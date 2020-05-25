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

        userDTO = authController.login("UserName_1", "Password_1");
        assertEquals("UserName_1", userDTO.getUsername());
        assertEquals("Name_1", userDTO.getName());
        assertEquals("Mail_1@gmail.com", userDTO.getMail());

        String[] roles = userDTO.getRoles();
        assertEquals("TeamCoach", roles[0]);
        assertEquals("TeamPlayer", roles[1]);
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