package domain;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalPageTest {

    TeamPlayer player = new TeamPlayer("pageTestUser","pageTestMail");
    PersonalPage page = new PlayerPage(player,"pageTestName");


    @Test
    void setName() {
        assertEquals("pageTestName", page.getName());
        page.setName("newName");
        assertEquals("newName", page.getName());
    }

    @Test
    void setInfo() {
        assertNull( page.getInfo());
        page.setInfo("newInfo");
        assertEquals("newInfo", page.getInfo());
    }

    @Test
    void setMail() {
        assertEquals("pageTestMail", page.getMail());
        page.setMail("newMail");
        assertEquals("newMail", page.getMail());
    }

    @Test
    void addSubscriber() {
        Fan fan = new Fan("userFan", "fan@gmail.com");
        assertEquals(0,page.getAlert().getInSystemAlertList().size());
        assertEquals(0,page.getAlert().getMailAlertList().size());
        page.addSubscriber(fan);
        assertEquals(1,page.getAlert().getInSystemAlertList().size());
        assertEquals(0,page.getAlert().getMailAlertList().size());
    }

    @Test
    void addPermissions() {
        TeamPlayer secondPlayer = new TeamPlayer("secondPlayer","secondPlayerMail");
        assertEquals(1,page.getPageOwners().size());
        page.addPermissions(secondPlayer);
        assertEquals(2,page.getPageOwners().size());

    }
}