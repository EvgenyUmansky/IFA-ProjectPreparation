package DataAccess;

import domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserDBAccessTest {
    User user;
    UserDBAccess userDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        user = new User("UserName_1", "Name_1", "Password_1", "TestMail@gmail.com");
        userDBAccess = UserDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        user = null;
        userDBAccess = null;
        connection.close();
        connection = null;
        preparedStatement.close();
        preparedStatement = null;
//        resultSet.close();
//        resultSet = null;
    }

    @Test
    public void save() throws SQLException, InterruptedException {
//        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        //preparedStatement.setString(1, user.getUserName());
        preparedStatement.executeUpdate();
        connection.commit();
        connection.close();
        connection = null;
        preparedStatement.close();
        preparedStatement = null;
//
//        // check the user does not exist in DB
        connection = DBConnector.getConnection();
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        //preparedStatement.setString(1, user.getUserName());
        assertFalse(preparedStatement.executeQuery().next());
        connection.close();
        preparedStatement.close();
        preparedStatement = null;

        // save user to DB
        userDBAccess.save(user);

        // User user1 = userDBAccess.select(user.getUserName());

        // check the user saved in the DB
        connection = DBConnector.getConnection();
        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
        //preparedStatement.setString(1, user.getUserName());
        assertEquals(user.getUserName(), preparedStatement.executeQuery().getString(1));
        connection.close();
        preparedStatement.close();
        preparedStatement = null;


        // delete the user from DB
        connection = DBConnector.getConnection();
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        preparedStatement.setString(1,user.getUserName());
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void select() {
    }
}