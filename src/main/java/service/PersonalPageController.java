package service;

import domain.PersonalPage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class PersonalPageController {

    private final domain.controllers.PersonalPageController controller;

    public PersonalPageController(){
        controller = new domain.controllers.PersonalPageController();
    }

    @GetMapping("/pages")
    public ArrayList<PersonalPage> getPages(){
        return controller.getPages();
    }

    // This will get pages by user in the following way: /pages?username=<username>
    @GetMapping("/pages")
    public ArrayList<PersonalPage> getPagesByUsername(@RequestParam("username") String userName){
        return controller.getPagesByUsername(userName);
    }

    // this will update (PUT request) the page who named <pageName> with the following info in body
    @PutMapping("/pages/{pageName}")
    public PersonalPage updatePageInfo(@PathVariable String pageName, @RequestBody String info){
        return controller.updatePageInfo(pageName, info);
    }

}
