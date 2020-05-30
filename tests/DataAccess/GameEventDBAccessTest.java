package DataAccess;

import domain.GameAlert;
import domain.GameEvent;
import domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GameEventDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    GameEvent gameEvent;
    GameEventDBAccess gameEventDBAccessTest;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        gameEvent = new GameEvent(1111, 1000, LocalDateTime.now(), 25, GameAlert.GOAL.toString(), "TestDesc");
        gameEventDBAccessTest = GameEventDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        gameEvent = null;
        gameEventDBAccessTest = null;
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
        // user == null
        gameEventDBAccessTest.save(null);
        assertEquals("gameEvent == null in GameEventDBAccess save(GameEvent gameEvent)\r\n", outContent.toString());
        outContent.reset();

//        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [GameEvent] where EventId = '1111'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the user does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [GameEvent] where EventId = '1111'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save user to DB
        gameEventDBAccessTest.save(gameEvent);

        // check the user saved in the DB
        preparedStatement = connection.prepareStatement("select * from [GameEvent] where EventId = '1111'");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(gameEvent.getId(), resultSet.getInt(1));


        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [GameEvent] where EventId = '1111'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
//        // user == null
//        gameEventDBAccessTest.update(null);
//        assertEquals("Couldn't execute 'update(User user)' in UserDBAccess: the user is null\r\n", outContent.toString());
//        outContent.reset();
//
//        // delete the user from DB if exists
//        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // insert the user to DB
//        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_2', 'password_2', 'testMail_2@gmail.com', 'false', 'false')");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // check the user exists in DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        assertEquals("name_2", resultSet.getString(2));
//
//        // update user in DB
//        gameEventDBAccessTest.update(gameEvent);
//
//        // check the user updated in the DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        assertEquals(gameEvent.getUserName(), resultSet.getString(1));
//        assertEquals(gameEvent.getName(), resultSet.getString(2));
//        assertEquals(gameEvent.getPassword(), resultSet.getString(3));
//        assertEquals(gameEvent.getMail(), resultSet.getString(4));
//
//
//        // delete the user from DB
//        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
//        preparedStatement.executeUpdate();
//        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
//        // user == null
//        gameEventDBAccessTest.delete(null);
//        assertEquals("Couldn't execute 'delete(User user)' in UserDBAccess: the user is null\r\n", outContent.toString());
//        outContent.reset();
//
//        // delete the user from DB if exists
//        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // insert the user to DB
//        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_2', 'password_2', 'testMail_2@gmail.com', 'false', 'false')");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // check the user exists in DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        assertEquals("UserName_1", resultSet.getString(1));
//
//        // delete user from DB
//        gameEventDBAccessTest.delete(gameEvent);
//
//        // check the user updated in the DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
//        assertNull(gameEventDBAccessTest.select(""));
//
//        // delete the user from DB if exists
//        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // insert the user to DB
//        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_2', 'password_2', 'testMail_2@gmail.com', 'false', 'false')");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // check the user exists in DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        assertEquals("UserName_1", resultSet.getString(1));
//
//        // check select
//        User selectedUser = gameEventDBAccessTest.select("UserName_1");
//        assertEquals("UserName_1", selectedUser.getUserName());
//        assertEquals("name_2", selectedUser.getName());
//        assertEquals("password_2", selectedUser.getPassword());
//        assertEquals("testMail_2@gmail.com", selectedUser.getMail());
//        assertFalse(selectedUser.isClosed());
    }

    @Test
    void conditionedSelect() {
        // TODO: write test
    }
}