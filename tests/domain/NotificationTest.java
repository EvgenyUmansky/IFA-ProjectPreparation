package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    Notification notification;

    @BeforeEach
    public void insert() {
        notification = new Notification("Title", "Text");
    }

    @AfterEach
    public void delete(){
        notification = null;
    }

    @Test
    void getTitle() {
        assertEquals("Title", notification.getTitle());
    }

    @Test
    void setTitle() {
        notification.setTitle("New Title");
        assertEquals("New Title", notification.getTitle());
    }

    @Test
    void getMessage() {
        assertEquals("Text", notification.getSubject());
    }

    @Test
    void setMessage() {
        notification.setSubject("New Text");
        assertEquals("New Text", notification.getSubject());
    }

    @Test
    void isSeen() {
        assertFalse(notification.isSeen());
    }

    @Test
    void setSeen() {
        notification.setSeen(true);
        assertTrue(notification.isSeen());
    }
}