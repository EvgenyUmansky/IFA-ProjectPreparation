package service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.pojos.LoginDTO;
import service.pojos.UserDTO;

import static org.junit.jupiter.api.Assertions.*;

class ApiAuthControllerTest {

    ApiAuthController controller;
    UserDTO userDTO;

    @BeforeEach
    public void insertBeforeTest() {
        controller = new ApiAuthController();
        userDTO = new UserDTO("UserDTO_test", "UserDTO", new String[]{"FAN"}, "mail", new String[]{"Noti"},  new String[]{"game"});
    }

    @AfterEach
    public void deleteAfterTest() {
        controller = null;
        userDTO = null;
    }

    @Test
    void connectToExternalSystems() {
    }

    @Test
    void login() throws Exception {
        UserDTO login = controller.login(new LoginDTO("UserDTO_test", "password_1"));
        assertEquals(userDTO.getUsername(), login.getUsername());
        assertEquals(userDTO.getName(), login.getName());
        assertEquals(userDTO.getMail(), login.getMail());
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
}