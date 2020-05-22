package DataAccess;

import domain.TeamPlayer;

import java.sql.*;
import java.util.HashMap;


public class TeamPlayerDBAccess implements DBAccess<TeamPlayer> {

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
            System.out.println("Couldn't execute 'save(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [TeamPlayer] values (?, ?, ?, ?, ?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1, teamPlayer.getUserName());
            statement.setDate(2, (Date) teamPlayer.getBirthDate());
            statement.setString(3, teamPlayer.getCurrentTeam());
            statement.setString(4, teamPlayer.getPosition());
            statement.setString(5, teamPlayer.getPosition());

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException | NullPointerException e) {
            System.out.println("Couldn't execute 'save(TeamPlayer teamPlayer)' in TeamPlayerDBAccess for " + teamPlayer.getUserName());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                System.out.println("Couldn't close 'save(TeamPlayer teamPlayer)' in TeamPlayerDBAccess for " + teamPlayer.getUserName());
            }
        }
    }


    @Override
    public void update(TeamPlayer teamPlayer) {
        if (teamPlayer == null) {
            System.out.println("Couldn't execute 'update(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null");
            return;
        }

        String query = "update [Players] " +
                "set BirthDate = ?, TeamName = ?, Position = ?, SquadNumber = ?, " +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1,(Date) teamPlayer.getBirthDate());
            statement.setString(2, teamPlayer.getCurrentTeam());
            statement.setString(3, teamPlayer.getPosition());
            statement.setString(4, teamPlayer.getSquadNumber());


            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Couldn't execute 'update(TeamPlayer teamPlayer)' in TeamPlayerDBAccess for " + teamPlayer.getUserName());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                System.out.println("Couldn't close 'update(TeamPlayer teamPlayer)' in TeamPlayerDBAccess for " + teamPlayer.getUserName());
            }
        }
    }

    @Override
    public void delete(TeamPlayer teamPlayer) {
        if (teamPlayer == null) {
            System.out.println("Couldn't execute 'delete(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null");
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
            System.out.println("Couldn't execute 'delete(TeamPlayer teamPlayer)' in TeamPlayerDBAccess for " + teamPlayer.getUserName());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                System.out.println("Couldn't close 'delete(TeamPlayer teamPlayer)' in TeamPlayerDBAccess for " + teamPlayer.getUserName());
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
                teamPlayer = new TeamPlayer(username, "", birthDate, position, squadNumber);
            }
        } catch (SQLException e) {
            assert false;
            System.out.println("Couldn't execute 'select(TeamPlayer teamPlayer)' in TeamPlayerDBAccess for " + teamPlayer.getUserName());
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
                System.out.println("Couldn't close 'delete(TeamPlayer teamPlayer)' in TeamPlayerDBAccess for " + teamPlayer.getUserName());
            }
        }
        return teamPlayer;
    }

    @Override
    public HashMap<String, TeamPlayer> conditionedSelect(String[] conditions) {


        return null;
    }
}