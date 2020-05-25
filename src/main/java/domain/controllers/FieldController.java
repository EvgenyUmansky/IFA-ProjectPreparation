package domain.controllers;

import DataAccess.DBAccess;
import DataAccess.FieldDBAccess;
import DataAccess.TeamFieldsDBAccess;
import domain.Field;
import domain.Team;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class FieldController {

    DBAccess<Field> fda = FieldDBAccess.getInstance();
    DBAccess<Pair<String,String>> tfda = TeamFieldsDBAccess.getInstance();


    public ArrayList<Field> getFields() {
        // TODO: DB arraylist of all Fields
        Field field1 = new Field("Santiago Bernabeu", 5);
        Field field2 = new Field("San Siro", 10);
        Field field3 = new Field("The Maracanã", 15);
        Field field4 = new Field("Azteca ", 20);
        return new ArrayList<Field>(Arrays.asList(field1,field2,field3,field4));
    }

    public ArrayList<Field> getAvailableFields() {
        // TODO: DB arraylist of all available Fields
        /*Field field1 = new Field("The Allianz Arena", 5);
        Field field2 = new Field("Old Trafford", 10);
        Field field3 = new Field("Camp Nou", 15);
        return new ArrayList<Field>(Arrays.asList(field1,field2,field3));*/

        HashMap<String,Field> availableFields = fda.conditionedSelect(new String[0]);
        return new ArrayList<>(availableFields.values());
    }
}
