package DataAccess;

import domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserDBAccessTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    User user;
    UserDBAccess userDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        user = new User("UserName_1", "Name_1", "Password_1", "TestMail@gmail.com");
        userDBAccess = UserDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        user = null;
        userDBAccess = null;
        connection.close();
        connection = null;
        preparedStatement.close();
        preparedStatement = null;
        resultSet.close();
        resultSet = null;
    }

    @Test
    public void save() throws SQLException {
        // user == null
        userDBAccess.save(null);
        assertEquals("Couldn't execute 'save(User user)' in UserDBAccess: the user is null\r\n", outContent.toString());
        outContent.reset();

//        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the user does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save user to DB
        userDBAccess.save(user);

        // check the user saved in the DB
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(user.getUserName(), resultSet.getString(1));


        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
        // user == null
        userDBAccess.update(null);
        assertEquals("Couldn't execute 'update(User user)' in UserDBAccess: the user is null\r\n", outContent.toString());
        outContent.reset();

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_2', 'password_2', 'testMail_2@gmail.com', 'false', 'false')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the user exists in DB
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("name_2", resultSet.getString(2));

        // update user in DB
        userDBAccess.update(user);

        // check the user updated in the DB
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(user.getUserName(), resultSet.getString(1));
        assertEquals(user.getName(), resultSet.getString(2));
        assertEquals(user.getPassword(), resultSet.getString(3));
        assertEquals(user.getMail(), resultSet.getString(4));


        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
        // user == null
        userDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(User user)' in UserDBAccess: the user is null\r\n", outContent.toString());
        outContent.reset();

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_2', 'password_2', 'testMail_2@gmail.com', 'false', 'false')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the user exists in DB
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete user from DB
        userDBAccess.delete(user);

        // check the user updated in the DB
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(userDBAccess.select(""));

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_2', 'password_2', 'testMail_2@gmail.com', 'false', 'false')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the user exists in DB
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        User selectedUser = userDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedUser.getUserName());
        assertEquals("name_2", selectedUser.getName());
        assertEquals("password_2", selectedUser.getPassword());
        assertEquals("testMail_2@gmail.com", selectedUser.getMail());
        assertFalse(selectedUser.isClosed());
    }

    @Test
    void conditionedSelect() {
        // TODO: write test
    }
}