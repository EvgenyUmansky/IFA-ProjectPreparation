package service;

import domain.User;
import domain.controllers.StartController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.pojos.LoginDTO;

@RestController
public class AuthController {
    private StartController controller;

    public AuthController(){
        controller = new StartController();
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginDTO creds) throws Exception {
        return controller.login(creds.getUsername(), creds.getPassword());
    }
}
