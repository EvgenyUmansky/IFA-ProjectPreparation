package service;

import DataAccess.FieldDBAccess;
import DataAccess.TeamCoachDBAccess;
import domain.Field;
import domain.TeamCoach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.pojos.UserDTO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApiFieldControllerTest {
    ApiFieldController controller;
    FieldDBAccess fieldDBAccess;

    @BeforeEach
    public void insertBeforeTest() {
        controller = new ApiFieldController();
        fieldDBAccess = FieldDBAccess.getInstance();
    }

    @AfterEach
    public void deleteAfterTest() {
        controller = null;
        fieldDBAccess = null;
    }

    @Test
    void getFields() {
        String[] conditions = new String[2];
        conditions[0] = "TeamFields.TeamName";
        conditions[1] = "null";
        ArrayList<Field> fieldsTest = new ArrayList<>(fieldDBAccess.conditionedSelect(conditions).values());;
        ArrayList<Field> fieldsAPI = controller.getFields(true);

        for(int i = 0; i < fieldsTest.size(); i++){
            assertEquals(fieldsTest.get(i).getFieldName(), fieldsAPI.get(i).getFieldName());
        }

    }

}