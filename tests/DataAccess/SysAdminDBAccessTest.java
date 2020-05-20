package DataAccess;

import domain.SystemAdministrator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SysAdminDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    SystemAdministrator systemAdministrator;
    SysAdminDBAccess sysAdminDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        systemAdministrator = new SystemAdministrator("UserName_1", "TestMail@gmail.com");
        sysAdminDBAccess = SysAdminDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        systemAdministrator = null;
        sysAdminDBAccess = null;

        if(connection != null) {
            connection.close();
            connection = null;
        }

        if(preparedStatement != null) {
            preparedStatement.close();
            preparedStatement = null;
        }

        if(resultSet != null) {
            resultSet.close();
            resultSet = null;
        }
    }


    @Test
    public void save() throws SQLException {
        // systemAdministrator == null
        sysAdminDBAccess.save(null);
        assertEquals("Couldn't execute 'save(SystemAdministrator systemAdministrator)' in SysAdminDBAccess: the systemAdministrator is null\r\n", outContent.toString());
        outContent.reset();

//        // delete the systemAdministrator from DB if exists
        preparedStatement = connection.prepareStatement("delete from [SystemAdministrator] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the systemAdministrator does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [SystemAdministrator] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save systemAdministrator to DB
        sysAdminDBAccess.save(systemAdministrator);

        // check the systemAdministrator saved in the DB
        preparedStatement = connection.prepareStatement("select * from [SystemAdministrator] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(systemAdministrator.getUserName(), resultSet.getString(1));


        // delete the systemAdministrator from DB
        preparedStatement = connection.prepareStatement("delete from [SystemAdministrator] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() {
        // There is only Prime Key in the systemAdministrator table, forbidden to change
        sysAdminDBAccess.update(systemAdministrator);
    }

    @Test
    public void delete() throws SQLException {
        // systemAdministrator == null
        sysAdminDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(SystemAdministrator systemAdministrator)' in SysAdminDBAccess: the systemAdministrator is null\r\n", outContent.toString());
        outContent.reset();

        // delete the systemAdministrator from DB if exists
        preparedStatement = connection.prepareStatement("delete from [SystemAdministrator] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the systemAdministrator to DB
        preparedStatement = connection.prepareStatement("insert into [SystemAdministrator] values ('UserName_1')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the systemAdministrator exists in DB
        preparedStatement = connection.prepareStatement("select * from [SystemAdministrator] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete systemAdministrator from DB
        sysAdminDBAccess.delete(systemAdministrator);

        // check the systemAdministrator updated in the DB
        preparedStatement = connection.prepareStatement("select * from [SystemAdministrator] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(sysAdminDBAccess.select(""));

        // delete the systemAdministrator from DB if exists
        preparedStatement = connection.prepareStatement("delete from [SystemAdministrator] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the systemAdministrator to DB
        preparedStatement = connection.prepareStatement("insert into [SystemAdministrator] values ('UserName_1')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the systemAdministrator exists in DB
        preparedStatement = connection.prepareStatement("select * from [SystemAdministrator] where username = 'UserName_1'");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        SystemAdministrator selectedSystemAdministrator = sysAdminDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedSystemAdministrator.getUserName());

        // delete the systemAdministrator from DB
        preparedStatement = connection.prepareStatement("delete from [SystemAdministrator] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }
}