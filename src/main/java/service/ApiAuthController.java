package service;

import domain.Role;
import domain.Subscriber;
import domain.User;
import org.springframework.web.bind.annotation.*;
import service.pojos.LoginDTO;
import service.pojos.UserDTO;

import java.util.HashMap;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApiAuthController {
    private final domain.controllers.AuthController controller;

    public ApiAuthController(){
        controller = new domain.controllers.AuthController();
    }

    @PostMapping("/externalSystems")
    public void connectToExternalSystems(){
        controller.connectToExternalSystems();
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginDTO login) throws Exception {
        return controller.login(login.getUsername(), login.getPassword());
    }

    @PostMapping("/logout")
    // This will log out in the following way: /users?username=<username>
    public void logout(@RequestParam("username") String userName) {
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
