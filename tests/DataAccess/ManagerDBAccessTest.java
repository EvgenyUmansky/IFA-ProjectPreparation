package DataAccess;

import domain.Team;
import domain.TeamManager;
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

class ManagerDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    TeamManager teamManager;
    Team team;
    ManagerDBAccess managerDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        teamManager = new TeamManager("UserName_1", "TestMail@gmail.com");
        team = new Team("Team_1", null, null, "Open");
        managerDBAccess = ManagerDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        teamManager = null;
        team = null;
        managerDBAccess = null;

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
        // teamManager == null
        managerDBAccess.save(null);
        assertEquals("Couldn't execute 'save(TeamManager teamManager)' in ManagerDBAccess: the teamManager is null\r\n", outContent.toString());
        outContent.reset();

//        // delete the teamManager from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Managers] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the teamManager does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Managers] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save teamManager to DB - null team
        managerDBAccess.save(teamManager);

        // check the teamManager saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Managers] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamManager.getUserName(), resultSet.getString(1));
        assertNull(resultSet.getString(2));


        // delete the teamManager from DB
        preparedStatement = connection.prepareStatement("delete from [Managers] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert team mock to DB for FK "teamName" in Managers
        preparedStatement = connection.prepareStatement("insert into [Teams] values ('Team_1', null, null, 'open')");
        preparedStatement.executeUpdate();
        connection.commit();

        // save teamManager to DB
        teamManager.setCurrentTeam(team);
        managerDBAccess.save(teamManager);

        // check the teamManager saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Managers] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamManager.getUserName(), resultSet.getString(1));
        assertEquals(teamManager.getCurrentTeam().getTeamName(), resultSet.getString(2));


        // delete the teamManager from DB
        preparedStatement = connection.prepareStatement("delete from [Managers] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the team from DB
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
        // teamManager == null
        managerDBAccess.update(null);
        assertEquals("Couldn't execute 'update(TeamManager teamManager)' in ManagerDBAccess: the teamManager is null\r\n", outContent.toString());
        outContent.reset();

        // delete the teamManager from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Managers] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert team mock to DB for FK "teamName" in Managers
        preparedStatement = connection.prepareStatement("insert into [Teams] values ('Team_1', null, null, 'open')");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamManager to DB
        preparedStatement = connection.prepareStatement("insert into [Managers] values ('UserName_1', null)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamManager exists in DB
        preparedStatement = connection.prepareStatement("select * from [Managers] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // update user in DB
        teamManager.setCurrentTeam(team);
        managerDBAccess.update(teamManager);

        // check the teamManager updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Managers] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamManager.getUserName(), resultSet.getString(1));
        assertEquals(teamManager.getCurrentTeam().getTeamName(), resultSet.getString(2));


        // delete the teamManager from DB
        preparedStatement = connection.prepareStatement("delete from [Managers] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the team from DB
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
        // teamManager == null
        managerDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(TeamManager teamManager)' in ManagerDBAccess: the teamManager is null\r\n", outContent.toString());
        outContent.reset();

        // delete the teamManager from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Managers] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamManager to DB
        preparedStatement = connection.prepareStatement("insert into [Managers] values ('UserName_1', null)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamManager exists in DB
        preparedStatement = connection.prepareStatement("select * from [Managers] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete teamManager from DB
        managerDBAccess.delete(teamManager);

        // check the teamManager updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Managers] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(managerDBAccess.select(""));

        // delete the teamManager from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Managers] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamManager to DB
        preparedStatement = connection.prepareStatement("insert into [Managers] values ('UserName_1', null)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamManager exists in DB
        preparedStatement = connection.prepareStatement("select * from [Managers] where username = 'UserName_1'");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        TeamManager selectedManager = managerDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedManager.getUserName());
        assertNull(selectedManager.getCurrentTeam());

        // delete the teamManager from DB
        preparedStatement = connection.prepareStatement("delete from [Managers] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }
}