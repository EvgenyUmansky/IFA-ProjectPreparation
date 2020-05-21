package DataAccess;

import domain.Referee;
import domain.RefereeType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RefereeDBAccess implements DBAccess<Referee> {

    private static final RefereeDBAccess instance = new RefereeDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private RefereeDBAccess() {

    }

    public static RefereeDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(Referee referee) {
        if (referee == null) {
            System.out.println("Couldn't execute 'save(Referee referee)' in RefereeDBAccess: the referee is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Referee] values (?, ?, ?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1, referee.getUserName());
            if(referee.getRefereeType() != null) {
                statement.setString(2, referee.getRefereeType().toString());
            }
            else {
                statement.setString(2, null);
            }
            statement.setInt(3, referee.getQualification());


            statement.executeUpdate();
            connection.commit();
        } catch (SQLException | NullPointerException e) {
            System.out.println("Couldn't execute 'save(Referee referee)' in RefereeDBAccess for " + referee.getUserName());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                System.out.println("Couldn't close 'save(Referee referee)' in RefereeDBAccess for " + referee.getUserName());
            }
        }
    }


    @Override
    public void update(Referee referee) {
        if (referee == null) {
            System.out.println("Couldn't execute 'update(Referee referee)' in RefereeDBAccess: the referee is null");
            return;
        }

        String query = "update [referee] " +
                "set TYPE = ?, Qualification = ? " +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            if(referee.getRefereeType() != null) {
                statement.setString(1, referee.getRefereeType().toString());
            }
            else{
                statement.setString(1, null);
            }
            statement.setInt(2, referee.getQualification());
            statement.setString(3, referee.getUserName());


            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Couldn't execute 'update(Referee referee)' in RefereeDBAccess for " + referee.getUserName());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                System.out.println("Couldn't close 'update(Referee referee)' in RefereeDBAccess for " + referee.getUserName());
            }
        }
    }

    @Override
    public void delete(Referee referee) {
        if (referee == null) {
            System.out.println("Couldn't execute 'delete(Referee referee)' in RefereeDBAccess: the referee is null");
            return;
        }

        String query = "delete from [Referee] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, referee.getUserName());

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Couldn't execute 'delete(Referee referee)' in RefereeDBAccess for " + referee.getUserName());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e3) {
                System.out.println("Couldn't close 'delete(Referee referee)' in RefereeDBAccess for " + referee.getUserName());
            }
        }
    }

    //TODO: fix the roles assignment to the user - User's constructor automatically assigns a Fan to the roles, but we need to check which roles
    // the user really has according to the DB
    @Override
    public Referee select(String username) {
        String query = "select * from [Referee] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        Referee referee = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            retrievedUser = statement.executeQuery();

            if (retrievedUser.next()) {
                referee = new Referee(username, "");


                String refType = retrievedUser.getString("Type");

                if (refType != null) {
                    referee.setRefereeType(RefereeType.valueOf(refType));
                }

                referee.setQualification(Integer.parseInt(retrievedUser.getString("Qualification")));
            }

        } catch (SQLException e) {
            assert false;
            System.out.println("Couldn't execute 'select(Referee referee)' in RefereeDBAccess for " + referee.getUserName());
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
                System.out.println("Couldn't close 'delete(Referee referee)' in RefereeDBAccess for " + referee.getUserName());
            }
        }
        return referee;
    }


}