package service;

import domain.TeamCoach;
import org.springframework.web.bind.annotation.*;

public class CoachController {
    private final domain.controllers.CoachController controller;

    public CoachController(){
        controller = new domain.controllers.CoachController();
    }

    // This will get coach details by user in the following way: //users/?userName=<userName>
    @GetMapping("/users")
    public TeamCoach getCoachDetails(@RequestParam("userName") String userName){
        return controller.getCoachDetails(userName);
    }

    @PutMapping("/users/{userName}")
    public void updateCoachDetails(@PathVariable String userName, @RequestParam String coachName, @RequestParam String qualification, @RequestParam String role){
        controller.updateCoachDetails(userName, coachName, qualification, role);
    }
}
