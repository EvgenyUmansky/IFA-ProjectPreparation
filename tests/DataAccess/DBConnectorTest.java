package DataAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


public class DBConnectorTest {
    DBConnector dbConnector;

    @BeforeEach
    public void insertBeforeTest() {
        dbConnector = DBConnector.getInstance();
    }

    @AfterEach
    public void deleteAfterTest(){
        dbConnector = null;
    }

    @Test
    public void  getInstance(){
        assertNotNull(dbConnector);
    }

    @Test
    public void getConnection() {
    }
}