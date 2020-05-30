package DataAccess;

import domain.TeamPlayer;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;


public class TeamPlayerDBAccess implements DBAccess<TeamPlayer> {
    static Logger logger = Logger.getLogger(TeamPlayerDBAccess.class.getName());
    private static final TeamPlayerDBAccess instance = new TeamPlayerDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private TeamPlayerDBAccess() {

    }

    public static TeamPlayerDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(TeamPlayer teamPlayer) {
        if (teamPlayer == null) {
            logger.error("Couldn't execute 'save(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Players] values (?, ?, ?, ?, ?, ?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1, teamPlayer.getUserName());
            statement.setDate(2, (Date) teamPlayer.getBirthDate());
            statement.setString(3, teamPlayer.getCurrentTeam());
            statement.setString(4, teamPlayer.getPosition());
            statement.setString(5, teamPlayer.getSquadNumber());
            statement.setString(6, teamPlayer.getName());

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
    public void update(TeamPlayer teamPlayer) {
        if (teamPlayer == null) {
            logger.error("Couldn't execute 'update(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null");
            return;
        }

        String query = "update [Players] " +
                "set BirthDate = ?, TeamName = ?, Position = ?, SquadNumber = ?," + "name = ? " +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1,(Date) teamPlayer.getBirthDate());
            statement.setString(2, teamPlayer.getCurrentTeam());
            statement.setString(3, teamPlayer.getPosition());
            statement.setString(4, teamPlayer.getSquadNumber());
            statement.setString(5, teamPlayer.getName());
            statement.setString(6, teamPlayer.getUserName());

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
    public void delete(TeamPlayer teamPlayer) {
        if (teamPlayer == null) {
            logger.error("Couldn't execute 'delete(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null");
            return;
        }

        String query = "delete from [Players] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, teamPlayer.getUserName());

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
    public TeamPlayer select(String username) {
        String query = "select * from [Players] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        TeamPlayer teamPlayer = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            retrievedUser = statement.executeQuery();

            if (retrievedUser.next()) {
                Date birthDate = retrievedUser.getDate(2);
                String teamName = retrievedUser.getString(3);
                String position = retrievedUser.getString(4);
                String squadNumber = retrievedUser.getString(5);
                String name = retrievedUser.getString(6);
                teamPlayer = new TeamPlayer(username, "", birthDate, position, squadNumber,name);
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
        return teamPlayer;
    }


    @Override
    public HashMap<String, TeamPlayer> conditionedSelect(String[] conditions) {
        String query;
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement;
        ResultSet retrievedPlayers;
        HashMap<String, TeamPlayer> players = new HashMap<>();

        if(conditions.length == 0){
            query = "select * from [Players]";
        }


        else {
            query = "select * from [Players] where ";

            for (int i = 0; i < conditions.length; i++) {
                if (i % 2 == 0) {
                    query += " " + conditions[i];
                } else {
                    if (conditions[i].equals("null")) {
                        query += " is null";
                        continue;
                    }
                    query += " = ?";
                    if (i < conditions.length - 1)
                        query += " and";
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
                            if (!conditions[i + 1].equals("null")) {
                                statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                            }
                            break;
                        case "position":
                        case "squadnumber":
                        case "name":
                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                            break;

                        case "birthdate":
                            statement.setDate((int) (i / 2) + 1, Date.valueOf(conditions[i + 1]));
                            break;

                        default:
                            break;
                    }
                    i += 2;
                }
            }
            retrievedPlayers = statement.executeQuery();


            while(retrievedPlayers.next()){
                String username = retrievedPlayers.getString(1);
                Date birthdate = retrievedPlayers.getDate(2);
                String teamname =  retrievedPlayers.getString(3);
                String position =  retrievedPlayers.getString(4);
                String squadnumber =  retrievedPlayers.getString(5);
                String name =  retrievedPlayers.getString(6);


                TeamPlayer teamPlayer = new TeamPlayer(username, "",birthdate, position, squadnumber,name);
                teamPlayer.setCurrentTeam(teamname);
                players.put(username,teamPlayer);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return players;
    }
}
