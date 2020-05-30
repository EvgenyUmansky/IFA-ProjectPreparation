package DataAccess;

import domain.SystemAdministrator;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;


public class SysAdminDBAccess implements DBAccess<SystemAdministrator> {
    static Logger logger = Logger.getLogger(SysAdminDBAccess.class.getName());
    private static final SysAdminDBAccess instance = new SysAdminDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private SysAdminDBAccess() {

    }

    public static SysAdminDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(SystemAdministrator systemAdministrator) {
        if (systemAdministrator == null) {
            logger.error("Couldn't execute 'save(SystemAdministrator systemAdministrator)' in SysAdminDBAccess: the systemAdministrator is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [SystemAdministrator] values (?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1, systemAdministrator.getUserName());


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
    public void update(SystemAdministrator systemAdministrator) {
        // There is only Prime Key in the SystemAdministrator table, forbidden to change
    }

    @Override
    public void delete(SystemAdministrator systemAdministrator) {
        if (systemAdministrator == null) {
            logger.error("Couldn't execute 'save(SystemAdministrator systemAdministrator)' in SysAdminDBAccess: the systemAdministrator is null");
            return;
        }

        String query = "delete from [SystemAdministrator] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, systemAdministrator.getUserName());

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
    public SystemAdministrator select(String username) {
        String query = "select * from [SystemAdministrator] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        SystemAdministrator systemAdministrator = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            retrievedUser = statement.executeQuery();

            if (retrievedUser.next()) {
                systemAdministrator = new SystemAdministrator(username, "");
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
        return systemAdministrator;
    }

    @Override
    public HashMap<String, SystemAdministrator> conditionedSelect(String[] conditions) {
        return null;
    }
}