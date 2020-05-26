package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlertNotificationTest {

    AlertNotification alertNotification;

    @BeforeEach
    public void insert() {
        alertNotification = new AlertNotification("Title", "Text");
    }

    @AfterEach
    public void delete(){
        alertNotification = null;
    }

    @Test
    void getTitle() {
        assertEquals("Title", alertNotification.getTitle());
    }

    @Test
    void setTitle() {
        alertNotification.setTitle("New Title");
        assertEquals("New Title", alertNotification.getTitle());
    }

    @Test
    void getMessage() {
        assertEquals("Text", alertNotification.getSubject());
    }

    @Test
    void setMessage() {
        alertNotification.setSubject("New Text");
        assertEquals("New Text", alertNotification.getSubject());
    }

    @Test
    void isSeen() {
        assertFalse(alertNotification.isSeen());
    }

    @Test
    void setSeen() {
        alertNotification.setSeen(true);
        assertTrue(alertNotification.isSeen());
    }
}