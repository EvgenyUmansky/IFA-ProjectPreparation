package service;

import domain.PersonalPage;
import domain.controllers.PersonalPageController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class PagesController {

    private PersonalPageController controller;

    public PagesController(){
        controller = new PersonalPageController();
    }


    // This will get pages by user in the following way: /pages?username=<username>
    @GetMapping("/pages")
    public ArrayList<PersonalPage> getPages(@RequestParam("username") String userName){
        return controller.getPagesByUsername(userName);
    }

    // this will update (PUT request) the page who named <pageName> with the following info in body
    @PutMapping("/pages/{pageName}")
    public PersonalPage updatePageInfo(@PathVariable String pageName, @RequestBody String info){
        return controller.updateInfo(pageName, info);
    }

}
