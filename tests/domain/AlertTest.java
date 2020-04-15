package domain;

import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlertTest {

    Alert alert = new Alert();

    @Test
    void addToMailSet() {
        assertEquals(0, alert.getMailAlertList().size());
        alert.addToMailSet(new Fan("Evgeny", "euguman@gmail.com", true));
        assertEquals(1, alert.getMailAlertList().size());
    }

    @Test
    void addToSystemSet() {
        assertEquals(0, alert.getInSystemAlertList().size());
        alert.addToSystemSet(new Fan("Alina", "", false));
        assertEquals(1, alert.getInSystemAlertList().size());
    }

    @Test
    void removeFromMailSet() {
        alert.addToMailSet(new Fan("Evgeny", "euguman@gmail.com", true));
        assertEquals(1, alert.getMailAlertList().size());
        alert.removeFromMailSet((Fan) alert.getMailAlertList().toArray()[0]);
        assertEquals(0, alert.getMailAlertList().size());
    }

    @Test
    void removeFromSystemSet() {
        alert.addToSystemSet(new Fan("Alina", "", false));
        assertEquals(1, alert.getInSystemAlertList().size());
        alert.removeFromSystemSet((Fan) alert.getInSystemAlertList().toArray()[0]);
        assertEquals(0, alert.getInSystemAlertList().size());
    }

    @Test
    void sendAlert() {

    }

    @Test
    void sendMailAlert() throws MessagingException {
        assertTrue(alert.sendMailAlert("euguman@gmail.com", new AlertNotification("Title", "Text")));
    }

    @Test
    void getMailAlertList() {
        assertEquals(0, alert.getMailAlertList().size());
        alert.addToMailSet(new Fan("Evgeny", "euguman@gmail.com", true));
        assertEquals(1, alert.getMailAlertList().size());
    }


    @Test
    void getInSystemAlertList() {
        assertEquals(0, alert.getInSystemAlertList().size());
        alert.addToSystemSet(new Fan("Alina", "", false));
        assertEquals(1, alert.getInSystemAlertList().size());
    }
}