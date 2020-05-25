package DataAccess;

import domain.Team;
import domain.TeamOwner;
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

class OwnerDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    TeamOwner teamOwner;
    Team team;
    OwnerDBAccess ownerDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        teamOwner = new TeamOwner("UserName_1", "TestMail@gmail.com");
        team = new Team("Team_1", null, null, "Open");
        ownerDBAccess = OwnerDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        teamOwner = null;
        team = null;
        ownerDBAccess = null;

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
        // teamOwner == null
        ownerDBAccess.save(null);
        assertEquals("Couldn't execute 'save(TeamOwner teamOwner)' in OwnerDBAccess: the teamOwner is null\r\n", outContent.toString());
        outContent.reset();

//        // delete the teamOwner from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Owners] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the teamOwner does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Owners] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save teamOwner to DB - null team
        ownerDBAccess.save(teamOwner);

        // check the teamOwner saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Owners] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamOwner.getUserName(), resultSet.getString(1));
        assertNull(resultSet.getString(2));


        // delete the teamOwner from DB
        preparedStatement = connection.prepareStatement("delete from [Owners] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert team mock to DB for FK "teamName" in Owners
        preparedStatement = connection.prepareStatement("insert into [Teams] values ('Team_1', null, null, 'open')");
        preparedStatement.executeUpdate();
        connection.commit();

        // save teamOwner to DB
        teamOwner.setTeam(team.getTeamName());
        ownerDBAccess.save(teamOwner);

        // check the teamOwner saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Owners] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamOwner.getUserName(), resultSet.getString(1));
        assertEquals(teamOwner.getTeam(), resultSet.getString(2));


        // delete the teamOwner from DB
        preparedStatement = connection.prepareStatement("delete from [Owners] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the team from DB
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
        // teamOwner == null
        ownerDBAccess.update(null);
        assertEquals("Couldn't execute 'update(TeamOwner teamOwner)' in OwnerDBAccess: the teamOwner is null\r\n", outContent.toString());
        outContent.reset();

        // delete the teamOwner from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Owners] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert team mock to DB for FK "teamName" in Owners
        preparedStatement = connection.prepareStatement("insert into [Teams] values ('Team_1', null, null, 'open')");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamOwner to DB
        preparedStatement = connection.prepareStatement("insert into [Owners] values ('UserName_1', null)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamOwner exists in DB
        preparedStatement = connection.prepareStatement("select * from [Owners] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // update teamOwner in DB
        teamOwner.setTeam(team.getTeamName());
        ownerDBAccess.update(teamOwner);

        // check the teamOwner updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Owners] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamOwner.getUserName(), resultSet.getString(1));
        assertEquals(teamOwner.getTeam(), resultSet.getString(2));


        // delete the teamOwner from DB
        preparedStatement = connection.prepareStatement("delete from [Owners] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the team from DB
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
        // teamOwner == null
        ownerDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(TeamOwner teamOwner)' in OwnerDBAccess: the teamOwner is null\r\n", outContent.toString());
        outContent.reset();

        // delete the teamOwner from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Owners] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamOwner to DB
        preparedStatement = connection.prepareStatement("insert into [Owners] values ('UserName_1', null)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamOwner exists in DB
        preparedStatement = connection.prepareStatement("select * from [Owners] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete teamOwner from DB
        ownerDBAccess.delete(teamOwner);

        // check the teamOwner updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Owners] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(ownerDBAccess.select(""));

        // delete the teamOwner from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Owners] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamOwner to DB
        preparedStatement = connection.prepareStatement("insert into [Owners] values ('UserName_1', null)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamOwner exists in DB
        preparedStatement = connection.prepareStatement("select * from [Owners] where username = 'UserName_1'");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        TeamOwner selectedOwner = ownerDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedOwner.getUserName());
        assertNull(selectedOwner.getTeam());

        // delete the teamOwner from DB
        preparedStatement = connection.prepareStatement("delete from [Owners] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void conditionedSelect() {
        ownerDBAccess.conditionedSelect(null);
    }
}