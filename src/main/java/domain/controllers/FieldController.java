package domain.controllers;

import domain.Field;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldController {
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
        Field field1 = new Field("The Allianz Arena", 5);
        Field field2 = new Field("Old Trafford", 10);
        Field field3 = new Field("Camp Nou", 15);
        return new ArrayList<Field>(Arrays.asList(field1,field2,field3));
    }
}
