package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemEventTest {
    SystemEvent systemEvent;

    @Before
    public void setUp() throws Exception {
        systemEvent = new SystemEvent("hi system");
    }

    @After
    public void tearDown() throws Exception {
        systemEvent = null;
    }

    @Test
    public void setSystemEvent(){
        assertNotNull(systemEvent);
    }


}