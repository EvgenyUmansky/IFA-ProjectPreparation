package service;

import org.springframework.web.bind.annotation.*;

public class ApiSystemAdministratorController {
    private final domain.controllers.SystemAdministratorController controller;

    public ApiSystemAdministratorController(){
        controller = new domain.controllers.SystemAdministratorController();
    }

    @PutMapping("/users")
    public void removeUserFromSystem(String username) {
        controller.removeUserFromSystem(username);
    }

    @GetMapping("/users")
    public void showComplain() {
        controller.showComplain();
    }

    @PutMapping("/users")
    public void commentToComplaint() {
        controller.commentToComplaint();
    }

    @GetMapping("/users")
    public void showLogDocument() {
        controller.showLogDocument();
    }

    @PostMapping("/users")
    public void startModelRecommendationSystem() {
        controller.startModelRecommendationSystem();
    }
}
