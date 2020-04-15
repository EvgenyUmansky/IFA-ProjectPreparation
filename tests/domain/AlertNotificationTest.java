package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlertNotificationTest {

    AlertNotification alertNotification = new AlertNotification("Title", "Text");

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
        assertEquals("Text", alertNotification.getMessage());
    }

    @Test
    void setMessage() {
        alertNotification.setMessage("New Text");
        assertEquals("New Text", alertNotification.getMessage());
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