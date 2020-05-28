package DataAccess;

import domain.Referee;
import domain.RefereeType;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class RefereeDBAccess implements DBAccess<Referee> {
    static Logger logger = Logger.getLogger(NotificationDBAccess.class.getName());
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
            logger.error("Couldn't execute 'save(Referee referee)' in RefereeDBAccess: the referee is null");
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
    public void update(Referee referee) {
        if (referee == null) {
            logger.error("Couldn't execute 'save(Referee referee)' in RefereeDBAccess: the referee is null");
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
    public void delete(Referee referee) {
        if (referee == null) {
            logger.error("Couldn't execute 'save(Referee referee)' in RefereeDBAccess: the referee is null");
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
    public Referee select(String username) {
        String query = "select [Referee].*, [User].name, [User].Mail, [User].IsMail " +
                "from [Referee] " +
                "inner join [User] on [Referee].UserName = [User].Username " +
                "where [Referee].username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        Referee referee = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            retrievedUser = statement.executeQuery();

            if (retrievedUser.next()) {
                String refType = retrievedUser.getString(2);
                int qualification = retrievedUser.getInt(3);
                String name = retrievedUser.getString(4);
                String mail = retrievedUser.getString(5);
                String isMail = retrievedUser.getString(6);

                referee = new Referee(username, mail, name);
                referee.setMail(isMail);

                if (refType != null) {
                    referee.setRefereeType(RefereeType.valueOf(refType));
                }

                referee.setQualification(qualification);
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
        return referee;
    }

    @Override
    public HashMap<String, Referee> conditionedSelect(String[] conditions) {
        return null;
    }


}