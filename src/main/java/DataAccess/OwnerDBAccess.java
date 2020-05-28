package DataAccess;

import domain.TeamOwner;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class OwnerDBAccess implements DBAccess<TeamOwner> {
    static Logger logger = Logger.getLogger(NotificationDBAccess.class.getName());
    private static final OwnerDBAccess instance = new OwnerDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private OwnerDBAccess() {

    }

    public static OwnerDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(TeamOwner teamOwner) {
        if (teamOwner == null) {
            logger.error("teamOwner object is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Owners] values (?, ?)";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, teamOwner.getUserName());
            if (teamOwner.getTeam() != null) {
                statement.setString(2, teamOwner.getTeam());
            } else {
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
    public void update(TeamOwner teamOwner) {
        if (teamOwner == null) {
            logger.error("Couldn't execute 'update(TeamOwner teamOwner)' in OwnerDBAccess: the teamOwner is null");
            return;
        }

        String query = "update [Owners] " +
                "set TeamName = ? " +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, teamOwner.getTeam());
            statement.setString(2, teamOwner.getUserName());


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

    @Override
    public void delete(TeamOwner teamOwner) {
        if (teamOwner == null) {
            logger.error("Couldn't execute 'delete(TeamOwner teamOwner)' in OwnerDBAccess: the teamOwner is null");
            return;
        }

        String query = "delete from [Owners] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, teamOwner.getUserName());

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
    public TeamOwner select(String username) {
        String query = "select * from [Owners] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        TeamOwner teamOwner = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            retrievedUser = statement.executeQuery();

            if (retrievedUser.next()) {
                teamOwner = new TeamOwner(username, "");
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
        return teamOwner;
    }

    @Override
    public HashMap<String, TeamOwner> conditionedSelect(String[] conditions) {
        return null;
    }


}