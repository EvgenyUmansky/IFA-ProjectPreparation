package service;

import domain.*;
import org.springframework.web.bind.annotation.*;
import service.pojos.GameDTO;

import java.util.ArrayList;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApiFieldController {

    private final domain.controllers.FieldController controller;

    public ApiFieldController(){
        controller = new domain.controllers.FieldController();
    }

    @GetMapping("/fields")
    public ArrayList<Field> getFields(@RequestParam(required = false) boolean available) {
        if (available) {
            return controller.getAvailableFields();
        }
        return controller.getFields();
    }

}
