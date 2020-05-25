package DataAccess;

import domain.AssociationAgent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class AssAgentDBAccessTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    AssociationAgent associationAgent;
    AssAgentDBAccess assAgentDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        associationAgent = new AssociationAgent("UserName_1", "TestMail@gmail.com");
        assAgentDBAccess = AssAgentDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        associationAgent = null;
        assAgentDBAccess = null;

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
        // AssociationAgent == null
        assAgentDBAccess.save(null);
        assertEquals("Couldn't execute 'save(AssociationAgent associationAgent)' in AssAgentDBAccess: the associationAgent is null\r\n", outContent.toString());
        outContent.reset();

//        // delete the AssociationAgent from DB if exists
        preparedStatement = connection.prepareStatement("delete from [AssociationAgent] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the AssociationAgent does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [AssociationAgent] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save AssociationAgent to DB
        assAgentDBAccess.save(associationAgent);

        // check the AssociationAgent saved in the DB
        preparedStatement = connection.prepareStatement("select * from [AssociationAgent] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(associationAgent.getUserName(), resultSet.getString(1));


        // delete the AssociationAgent from DB
        preparedStatement = connection.prepareStatement("delete from [AssociationAgent] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() {
        // There is only Prime Key in the AssociationAgent table, forbidden to change
        assAgentDBAccess.update(associationAgent);
    }

    @Test
    public void delete() throws SQLException {
        // AssociationAgent == null
        assAgentDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(AssociationAgent associationAgent)' in AssAgentDBAccess: the associationAgent is null\r\n", outContent.toString());
        outContent.reset();

        // delete the AssociationAgent from DB if exists
        preparedStatement = connection.prepareStatement("delete from [AssociationAgent] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the AssociationAgent to DB
        preparedStatement = connection.prepareStatement("insert into [AssociationAgent] values ('UserName_1')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the AssociationAgent exists in DB
        preparedStatement = connection.prepareStatement("select * from [AssociationAgent] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete AssociationAgent from DB
        assAgentDBAccess.delete(associationAgent);

        // check the AssociationAgent updated in the DB
        preparedStatement = connection.prepareStatement("select * from [AssociationAgent] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(assAgentDBAccess.select(""));

        // delete the AssociationAgent from DB if exists
        preparedStatement = connection.prepareStatement("delete from [AssociationAgent] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the AssociationAgent to DB
        preparedStatement = connection.prepareStatement("insert into [AssociationAgent] values ('UserName_1')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the AssociationAgent exists in DB
        preparedStatement = connection.prepareStatement("select * from [AssociationAgent] where username = 'UserName_1'");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        AssociationAgent selectedAssociationAgent = assAgentDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedAssociationAgent.getUserName());

        // delete the AssociationAgent from DB
        preparedStatement = connection.prepareStatement("delete from [AssociationAgent] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void conditionedSelect() {
        assAgentDBAccess.conditionedSelect(null);
    }
}