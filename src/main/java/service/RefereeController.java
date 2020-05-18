package service;

import org.springframework.web.bind.annotation.*;

public class RefereeController {
    private final domain.controllers.RefereeController controller;

    public RefereeController(){
        controller = new domain.controllers.RefereeController();
    }

    @PostMapping("/users")
    public void createReferee(String username, String password, String name, String mail) throws Exception {
        controller.createReferee(username, password, name, mail);
    }

    @PutMapping("/users")
    public void removeReferee(String username){
        controller.removeReferee(username);
    }

    @GetMapping("/users")
    public String getRefereeDetails(String userName){
        return controller.getRefereeDetails(userName);
    }

    @PutMapping("/users")
    public void setRefereeProfileDetails(String username, String newPassword, String newName, String newMail, String qualification, String refereeType){
        controller.setRefereeProfileDetails(username, newPassword, newName, newMail, qualification, refereeType);
    }
}
