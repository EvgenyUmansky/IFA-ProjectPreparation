package DataAccess;

import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserRolesDBAccessTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    ArrayList<String> roles;
    UserRolesDBAccess userRolesDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() throws SQLException {
        System.setOut(new PrintStream(outContent));

        roles = new ArrayList<String>(){{add("TeamPlayer"); add("TeamCoach");}};
        userRolesDBAccess = UserRolesDBAccess.getInstance();
        connection = DBConnector.getConnection();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_1', 'password_1', 'testMail_1@gmail.com', 'false', 'false')");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        roles = null;
        System.setOut(originalOut);
        userRolesDBAccess = null;
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
        // roles == null
        userRolesDBAccess.save(null);
        assertEquals("Couldn't execute 'save(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess: the roles is null\r\n", outContent.toString());
        outContent.reset();

        // roles.getValue() == null
        userRolesDBAccess.save(new Pair<>("User", null));
        assertEquals("Couldn't execute 'save(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess: the roles.getValue() is null\r\n", outContent.toString());
        outContent.reset();

       // delete the roles from DB if exists
        preparedStatement = connection.prepareStatement("delete from [UserRoles] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the roles does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [UserRoles] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save roles to DB
        userRolesDBAccess.save(new Pair<>("UserName_1", roles));

        // check the roles saved in the DB
        preparedStatement = connection.prepareStatement("select * from [UserRoles] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));
        assertEquals("TeamCoach", resultSet.getString(2));
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));
        assertEquals("TeamPlayer", resultSet.getString(2));

        // delete the roles from DB
        preparedStatement = connection.prepareStatement("delete from [UserRoles] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() {
        userRolesDBAccess.update(null);
    }

    @Test
    public void delete() throws SQLException {
        // roles == null
        userRolesDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess: the roles is null\r\n", outContent.toString());
        outContent.reset();

        // roles.getValue() == null
        userRolesDBAccess.delete(new Pair<>("User", null));
        assertEquals("Couldn't execute 'delete(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess: the roles.getValue() is null\r\n", outContent.toString());
        outContent.reset();

        // delete the roles from DB if exists
        preparedStatement = connection.prepareStatement("delete from [UserRoles] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();


        ////////////////// delete 1 role //////////////////////////

        // insert the role to DB
        preparedStatement = connection.prepareStatement("insert into [UserRoles] values ('UserName_1', 'TeamPlayer')");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the role to DB
        preparedStatement = connection.prepareStatement("insert into [UserRoles] values ('UserName_1', 'TeamCoach')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the roles exists in DB
        preparedStatement = connection.prepareStatement("select * from [UserRoles] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete roles from DB
        roles.remove(0); // TeamPlayer
        userRolesDBAccess.delete(new Pair<>("UserName_1", roles));

        // check the roles updated in the DB
        preparedStatement = connection.prepareStatement("select * from [UserRoles] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));
        assertEquals("TeamPlayer", resultSet.getString(2));
        assertFalse(resultSet.next());

        ////////////////// delete all 2 roles //////////////////////////

        // insert the role to DB
        preparedStatement = connection.prepareStatement("insert into [UserRoles] values ('UserName_1', 'TeamCoach')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the roles exists in DB
        preparedStatement = connection.prepareStatement("select * from [UserRoles] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete roles from DB
        roles.add("TeamPlayer");
        userRolesDBAccess.delete(new Pair<>("UserName_1", roles));

        // check the roles updated in the DB
        preparedStatement = connection.prepareStatement("select * from [UserRoles] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    void select() throws SQLException {
        Pair<String,ArrayList<String>> rolesEmpty = userRolesDBAccess.select("");
        assertEquals(0, rolesEmpty.getValue().size());

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [UserRoles] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the role to DB
        preparedStatement = connection.prepareStatement("insert into [UserRoles] values ('UserName_1', 'TeamPlayer')");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the role to DB
        preparedStatement = connection.prepareStatement("insert into [UserRoles] values ('UserName_1', 'TeamCoach')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the role exists in DB
        preparedStatement = connection.prepareStatement("select * from [UserRoles] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        ArrayList<String> roles = userRolesDBAccess.select("UserName_1").getValue();
        assertEquals("TeamPlayer", roles.get(1));
        assertEquals("TeamCoach", roles.get(0));

        // delete the role from DB
        preparedStatement = connection.prepareStatement("delete from [UserRoles] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void conditionedSelect() {
        userRolesDBAccess.conditionedSelect(null);
    }
}