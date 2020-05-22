package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SystemEventTest {
    SystemEvent systemEvent;

    @BeforeEach
    public void setUp() throws Exception {
        systemEvent = new SystemEvent("hi system");
    }

    @AfterEach
    public void tearDown() throws Exception {
        systemEvent = null;
    }

    @Test
    public void setSystemEvent(){
        assertNotNull(systemEvent);
    }


}