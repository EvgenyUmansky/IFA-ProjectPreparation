package service;

import domain.TeamCoach;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ApiCoachController {
    private final domain.controllers.CoachController controller;

    public ApiCoachController() {
        controller = new domain.controllers.CoachController();
    }

//    // This will get coach details by user in the following way: //users/?userName=<userName>
//    @GetMapping("/coaches")
//    public TeamCoach getCoachDetails(@RequestParam("userName") String userName){
//        return controller.getCoachDetails(userName);
//    }

    @GetMapping("/coaches")
    public ArrayList<TeamCoach> getCoaches(@RequestParam(required = false) boolean available) {
        if (available) {
            return controller.getAvailableCoaches();
        }
        return controller.getCoaches();
    }
}
