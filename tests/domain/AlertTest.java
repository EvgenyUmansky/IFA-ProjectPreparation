package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AlertTest {

    Alert alert = new Alert();
    ArrayList<Fan> fans;

    @BeforeEach
    public void insert() {
        fans = new ArrayList<>();
        fans.add(new Fan("Evgeny", "euguman@gmail.com"));
        fans.add(new Fan("Alina", ""));

        alert = new Alert();
    }

    @AfterEach
    public void delete(){
        alert = null;
    }

    @Test
    void addSubscriber(){
        assertEquals(0, alert.getMailAlertList().size());
        assertEquals(0, alert.getInSystemAlertList().size());
        alert.addSubscriber(fans.get(0));
        assertEquals(1, alert.getMailAlertList().size());
        alert.addSubscriber(fans.get(1));
        assertEquals(1, alert.getInSystemAlertList().size());
    }

    @Test
    void removeSubscriber(){
        assertEquals(0, alert.getMailAlertList().size());
        assertEquals(0, alert.getInSystemAlertList().size());
        alert.addSubscriber(fans.get(0));
        assertEquals(1, alert.getMailAlertList().size());
        alert.addSubscriber(fans.get(1));
        assertEquals(1, alert.getInSystemAlertList().size());

        alert.removeSubscriber(fans.get(0));
        assertEquals(0, alert.getMailAlertList().size());
        alert.removeSubscriber(fans.get(1));
        assertEquals(0, alert.getInSystemAlertList().size());
    }

    @Test
    void addToMailSet() {
        assertEquals(0, alert.getMailAlertList().size());
        alert.addToMailSet(fans.get(0));
        assertEquals(1, alert.getMailAlertList().size());
    }

    @Test
    void addToSystemSet() {
        assertEquals(0, alert.getInSystemAlertList().size());
        alert.addToSystemSet(fans.get(1));
        assertEquals(1, alert.getInSystemAlertList().size());
    }

    @Test
    void removeFromMailSet() {
        alert.addToMailSet(fans.get(0));
        assertEquals(1, alert.getMailAlertList().size());
        alert.removeFromMailSet((Fan) alert.getMailAlertList().toArray()[0]);
        assertEquals(0, alert.getMailAlertList().size());
    }

    @Test
    void removeFromSystemSet() {
        alert.addToSystemSet(fans.get(1));
        assertEquals(1, alert.getInSystemAlertList().size());
        alert.removeFromSystemSet((Fan) alert.getInSystemAlertList().toArray()[0]);
        assertEquals(0, alert.getInSystemAlertList().size());

        alert.addToSystemSet(fans.get(0));
        assertEquals(1, alert.getInSystemAlertList().size());
        alert.removeFromSystemSet((Fan) alert.getInSystemAlertList().toArray()[0]);
        assertEquals(0, alert.getInSystemAlertList().size());
    }

    @Test
    void sendAlert() {
        alert.addToMailSet(fans.get(0));
        alert.addToSystemSet(fans.get(1));
        Map<String, Boolean> isSentMap = alert.sendAlert(new AlertNotification("Title", "Text"));

        for(String user : isSentMap.keySet()){
            assertTrue(isSentMap.get(user));
        }
    }

    /*
    // private functions in sendAlert()
    @Test
    void sendMailAlert() {
        assertTrue(alert.sendMailAlert("euguman@gmail.com", new AlertNotification("Title", "Text")));
        assertFalse(alert.sendMailAlert("euguman@gmal.com", new AlertNotification("Title", "Text")));
    }

    @Test
    void sendInSystemAlert(){
        assertEquals(0, fans.get(1).getAlertsMessages().size());
        alert.sendInSystemAlert(fans.get(1), new AlertNotification("Title", "Text"));
        assertEquals(1, fans.get(1).getAlertsMessages().size());
        assertEquals("Title", fans.get(1).getAlertsMessages().get(0).getTitle());
        assertEquals("Text", fans.get(1).getAlertsMessages().get(0).getMessage());
    }
     */

    @Test
    void getMailAlertList() {
        assertEquals(0, alert.getMailAlertList().size());
        alert.addToMailSet(fans.get(0));
        assertEquals(1, alert.getMailAlertList().size());
    }


    @Test
    void getInSystemAlertList() {
        assertEquals(0, alert.getInSystemAlertList().size());
        alert.addToSystemSet(fans.get(1));
        assertEquals(1, alert.getInSystemAlertList().size());
    }
}