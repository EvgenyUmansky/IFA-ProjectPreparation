package DataAccess;

import domain.Fan;
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

class FanDBAccessTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    Fan fan;
    FanDBAccess fanDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        fan = new Fan("UserName_1", "TestMail@gmail.com");
        fanDBAccess = FanDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        fan = null;
        fanDBAccess = null;

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
        // fan == null
        fanDBAccess.save(null);
        assertEquals("Couldn't execute 'save(Fan fan)' in FanDBAccess: the fan is null\r\n", outContent.toString());
        outContent.reset();

//        // delete the fan from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Fan] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the fan does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Fan] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save fan to DB
        fanDBAccess.save(fan);

        // check the fan saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Fan] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(fan.getUserName(), resultSet.getString(1));


        // delete the fan from DB
        preparedStatement = connection.prepareStatement("delete from [Fan] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() {
        // There is only Prime Key in the Fan table, forbidden to change
        fanDBAccess.update(fan);
    }

    @Test
    public void delete() throws SQLException {
        // fan == null
        fanDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(Fan fan)' in FanDBAccess: the fan is null\r\n", outContent.toString());
        outContent.reset();

        // delete the fan from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Fan] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the fan to DB
        preparedStatement = connection.prepareStatement("insert into [Fan] values ('UserName_1')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the fan exists in DB
        preparedStatement = connection.prepareStatement("select * from [Fan] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete fan from DB
        fanDBAccess.delete(fan);

        // check the fan updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Fan] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(fanDBAccess.select(""));

        // delete the fan from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Fan] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the fan to DB
        preparedStatement = connection.prepareStatement("insert into [Fan] values ('UserName_1')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the fan exists in DB
        preparedStatement = connection.prepareStatement("select * from [Fan] where username = 'UserName_1'");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        Fan selectedFan = fanDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedFan.getUserName());

        // delete the fan from DB
        preparedStatement = connection.prepareStatement("delete from [Fan] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }
}