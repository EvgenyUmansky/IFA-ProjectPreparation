package domain.controllers;

import javafx.util.Pair;
import DataAccess.UserDBAccess;
import DataAccess.UserRolesDBAccess;
import domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.pojos.UserDTO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {
    UserDBAccess userDBAccess;
    UserRolesDBAccess userRolesDBAccess;

    AuthController authController;
    UserDTO userDTO;

    @BeforeEach
    public void insertBeforeTest() {
        userDBAccess = UserDBAccess.getInstance();
        userRolesDBAccess = UserRolesDBAccess.getInstance();
        userDBAccess.save(new User("UserName_1", "Password_1", "Name_1", "Mail_1@gmail.com"));
        userRolesDBAccess.save(new Pair<>("UserName_1", new ArrayList<String>(){{add("TeamPlayer"); add("TeamCoach");}}));
        authController = new AuthController();
    }

    @AfterEach
    public void deleteAfterTest() {
        userRolesDBAccess.delete(new Pair<>("UserName_1", new ArrayList<String>(){{add("TeamPlayer"); add("TeamCoach");}}));
        userDBAccess.delete(new User("UserName_1", "Password_1", "Name_1", "Mail@gmail"));
        authController = null;
        userDTO = null;
        userDBAccess = null;
        userRolesDBAccess = null;
    }

    @Test
    void connectToExternalSystems() {
    }

    @Test
    void login() throws Exception {
        assertThrows(Exception.class, () -> authController.login(null, "Password_1"));
        assertThrows(Exception.class, () -> authController.login("UserName_1", "Error"));

        userDTO = authController.login("Referee_1", "password_1");
        assertEquals("Referee_1", userDTO.getUsername());
        assertEquals("RefereeName_1", userDTO.getName());
        assertEquals("testMail_1@gmail.com", userDTO.getMail());

        String[] roles = userDTO.getRoles();
        assertEquals("REFEREE", roles[0]);

        String[] gameIds = userDTO.getGames();
        assertEquals("1000", gameIds[0]);

        userDTO = authController.login("Fan_3", "password_1");
        assertEquals("Fan_3", userDTO.getUsername());
        assertEquals("FanName_3", userDTO.getName());
        assertEquals("testMail_3@gmail.com", userDTO.getMail());

        roles = userDTO.getRoles();
        assertEquals("FAN", roles[0]);

        gameIds = userDTO.getGames();
        assertEquals("1000", gameIds[0]);
    }

    @Test
    void logout() {
    }

    @Test
    void register() {
    }

    @Test
    void getUserRoles() {
    }

    @Test
    void main() {
    }

    @Test
    void runSystem() {
    }

    @Test
    void init() {
    }
}