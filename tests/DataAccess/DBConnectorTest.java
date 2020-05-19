package DataAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectorTest {
    Connection connection;

    @BeforeEach
    public void insertBeforeTest() {
        connection = null;
    }


    @Test
    public void  getInstance(){
        assertNotNull(DBConnector.getInstance());
    }

    @Test
    public void getConnection() throws SQLException {
        connection = DBConnector.getConnection();
        assertFalse(connection.isClosed());
    }
}