package service;

import org.springframework.web.bind.annotation.*;

public class ApiFanController {
    private final domain.controllers.FanController controller;

    public ApiFanController(){
        controller = new domain.controllers.FanController();
    }

    @PostMapping("/users/{username}")
    public void addFanSubscriptionToGame(@PathVariable String username, @RequestParam String sysAdmin, @RequestParam String title, @RequestParam String message){
        controller.sendComplaintToSysAdmin(username, sysAdmin, title, message);
    }

    @GetMapping("/users")
    // This will get history by fan user in the following way: /users?username=<username>
    public String[] getFanHistory(@RequestParam("username") String userName) {
        return controller.getFanHistory(userName);
    }

    @GetMapping("/users")
    // This will get details of fan user in the following way: /users?username=<username>
    public String getFanProfileDetails(@RequestParam("username") String userName) {
        return controller.getFanProfileDetails(userName);
    }

    // This will update details of fan user in the following way: /users/{userName}
    @PutMapping("/users/{userName}")
    public void setFanProfileDetails(@PathVariable String username, @RequestParam String newPassword, @RequestParam String newName, @RequestParam String newMail){
        controller.setFanProfileDetails(username, newPassword, newName, newMail);
    }
}
