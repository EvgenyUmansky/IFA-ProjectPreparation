package DataAccess;

import domain.Referee;
import domain.RefereeType;
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

import static org.junit.jupiter.api.Assertions.*;

class RefereeDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    Referee referee;
    RefereeDBAccess refereeDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        referee = new Referee("UserName_1", "TestMail@gmail.com");

        refereeDBAccess = RefereeDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        referee = null;
        refereeDBAccess = null;

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
        refereeDBAccess.save(null);
        assertEquals("Couldn't execute 'save(Referee referee)' in RefereeDBAccess: the referee is null\r\n", outContent.toString());
        outContent.reset();

//        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Referee] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the user does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Referee] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save user to DB: referee type == null
        refereeDBAccess.save(referee);

        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [Referee] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // save user to DB: referee type != null
        referee.setRefereeType(RefereeType.MAIN);
        refereeDBAccess.save(referee);

        // check the user saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Referee] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(referee.getUserName(), resultSet.getString(1));


        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [Referee] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
        // user == null
        refereeDBAccess.update(null);
        assertEquals("Couldn't execute 'update(Referee referee)' in RefereeDBAccess: the referee is null\r\n", outContent.toString());
        outContent.reset();

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Referee] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [Referee] values ('UserName_1', 'ASSISTANT', 2)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the user exists in DB
        preparedStatement = connection.prepareStatement("select * from [Referee] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("ASSISTANT", resultSet.getString(2));

        // update user in DB, Referee type == null
        refereeDBAccess.update(referee);

        // check the user updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Referee] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(referee.getUserName(), resultSet.getString(1));
        assertNull(resultSet.getString(2));
        assertEquals(String.valueOf(referee.getQualification()), resultSet.getString(3));

        // update user in DB, Referee type != null
        referee.setRefereeType(RefereeType.MAIN);
        referee.setQualification(5);
        refereeDBAccess.update(referee);

        // check the user updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Referee] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(referee.getUserName(), resultSet.getString(1));
        assertEquals(referee.getRefereeType().toString(), resultSet.getString(2));
        assertEquals(String.valueOf(referee.getQualification()), resultSet.getString(3));

        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [Referee] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
        // user == null
        refereeDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(Referee referee)' in RefereeDBAccess: the referee is null\r\n", outContent.toString());
        outContent.reset();

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Referee] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [Referee] values ('UserName_1', 'ASSISTANT', 0)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the user exists in DB
        preparedStatement = connection.prepareStatement("select * from [Referee] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete user from DB
        refereeDBAccess.delete(referee);

        // check the user updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Referee] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(refereeDBAccess.select(""));

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Referee] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [Referee] values ('UserName_1', 'ASSISTANT', 4)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the user exists in DB
        preparedStatement = connection.prepareStatement("select * from [Referee] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        Referee selectedReferee = refereeDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedReferee.getUserName());
        assertEquals("ASSISTANT", selectedReferee.getRefereeType().toString());
        assertEquals("4", String.valueOf(selectedReferee.getQualification()));
        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [Referee] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

    }
}