package DataAccess;

import domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        preparedStatement = null;
        resultSet = null;
    }

    @Test
    public void save() throws SQLException {
        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = ?");
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.executeUpdate();
        connection.commit();

        // check the user does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [User] where username = ?" );
        preparedStatement.setString(1, user.getUserName());
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());

        // save user to DB
        userDBAccess.save(user);

        // check the user saved in the DB
        resultSet = preparedStatement.executeQuery();
        assertEquals(user.getUserName(), resultSet.getString(1));

        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [User] where username = " + user.getUserName());
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