package service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ApiGuestController {
    private final domain.controllers.GuestController controller;

    public ApiGuestController(){
        controller = new domain.controllers.GuestController();
    }

    @GetMapping("/search")
    // This will searches by term in the following way: /search?term=<term>
    public void searchByKeyTerm(@RequestParam("term") String term) {
        controller.searchByKeyTerm(term);
    }
}
