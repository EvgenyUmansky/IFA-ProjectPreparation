package DataAccess;

import domain.TeamManager;
import domain.TeamOwner;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class ManagerDBAccess implements DBAccess<TeamManager> {
    static Logger logger = Logger.getLogger(ManagerDBAccess.class.getName());
    private static final ManagerDBAccess instance = new ManagerDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private ManagerDBAccess() {

    }

    public static ManagerDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(TeamManager teamManager) {
        if (teamManager == null) {
            logger.error("Couldn't execute 'save(TeamManager teamManager)' in ManagerDBAccess: the teamManager is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Managers] values (?, ?)";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, teamManager.getUserName());
            if(teamManager.getCurrentTeam() != null) {
                statement.setString(2, teamManager.getCurrentTeam());
            }
            else {
                statement.setString(2, null);
            }


            statement.executeUpdate();
            connection.commit();
        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                logger.error(e3.getMessage());
            }
        }
    }


    @Override
    public void update(TeamManager teamManager) {
        if (teamManager == null) {
            logger.error("Couldn't execute 'save(TeamManager teamManager)' in ManagerDBAccess: the teamManager is null");
            return;
        }

        String query = "update [Managers] " +
                "set TeamName = ? " +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, teamManager.getCurrentTeam());
            statement.setString(2, teamManager.getUserName());



            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                System.out.println("Couldn't close 'update(TeamManager teamManager)' in ManagerDBAccess for " + teamManager.getUserName());
            }
        }
    }

    @Override
    public void delete(TeamManager teamManager) {
        if (teamManager == null) {
            logger.error("Couldn't execute 'save(TeamManager teamManager)' in ManagerDBAccess: the teamManager is null");
            return;
        }

        String query = "delete from [Managers] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, teamManager.getUserName());

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                logger.error(e3.getMessage());
            }
        }
    }

    //TODO: fix the roles assignment to the user - User's constructor automatically assigns a Fan to the roles, but we need to check which roles
    // the user really has according to the DB
    @Override
    public TeamManager select(String username) {
        String query = "select * from [Managers] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        TeamManager teamManager = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            retrievedUser = statement.executeQuery();

            if (retrievedUser.next()) {
                teamManager = new TeamManager(username, "");
            }
        } catch (SQLException e) {
            assert false;
            logger.error(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (retrievedUser != null) {
                    retrievedUser.close();
                }
                connection.close();
            } catch (SQLException e3) {
                logger.error(e3.getMessage());
            }
        }
        return teamManager;
    }

    @Override
    public HashMap<String, TeamManager> conditionedSelect(String[] conditions) {

        String query;
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedManagers = null;
        HashMap<String, TeamManager> teamManagers = new HashMap<>();

        if(conditions.length == 0){
            query = "select * from [Managers]";
        }
        else {
            query = "select * from [Managers] where";

            for (int i = 0; i < conditions.length; i++) {
                if (i % 2 == 0) {
                    query += " " + conditions[i];
                } else {
                    query += " = ?";
                    if (i < conditions.length - 1)
                        query += ",";
                }
            }
        }
        try {
            statement = connection.prepareStatement(query);

            if(conditions.length > 0) {
                int i = 0;
                while (i < conditions.length) {
                    switch (conditions[i].toLowerCase()) {
                        case "username":
                        case "teamname":
                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                            break;

                        default:
                            break;
                    }
                    i += 2;
                }
            }

            retrievedManagers = statement.executeQuery();


            while(retrievedManagers.next()){
                String userName = retrievedManagers.getString(1);
                String teamName = retrievedManagers.getString(2);


                teamManagers.put(userName,new TeamManager(userName,"",teamName));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return teamManagers;
    }


}