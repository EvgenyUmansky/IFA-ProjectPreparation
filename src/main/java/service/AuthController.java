package service;

import domain.Role;
import domain.Subscriber;
import domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import service.pojos.LoginDTO;

import java.util.HashMap;

public class AuthController {
    private final domain.controllers.AuthController controller;

    public AuthController(){
        controller = new domain.controllers.AuthController();
    }

    @PostMapping("/externalSystems")
    public void connectToExternalSystems(){
        controller.connectToExternalSystems();
    }

    @GetMapping("/login")
    // This will log in a user
    public User login(@RequestBody LoginDTO login) throws Exception {
        return controller.login(login.getUsername(), login.getPassword());
    }

    @GetMapping("/users")
    // This will log out in the following way: /users?username=<username>
    public void logout(@RequestParam("username") String userName) throws Exception {
        controller.logout(userName);
    }

    @GetMapping("/registration")
    // This will register a user
    public User register(String userName, String password, String name, String mail) throws Exception {
        return controller.register(userName, password, name, mail);
    }

    @GetMapping("/users")
    // This will get roles of user in the following way: /users?username=<username>
    public HashMap<Role, Subscriber> getUserRoles(@RequestParam("username") String userName) throws Exception {
        return controller.getUserRoles(userName);
    }
}
