package domain;
import java.util.HashSet;
import java.util.Set;

public class Field {

    private String fieldName;
    private double propertyCost;

/////////// Constructors ///////////
    public Field(String fieldName, double propertyCost) {
        this.fieldName = fieldName;
        this.propertyCost = propertyCost;
    }


/////////// Functionality ///////////


/////////// Getters and Setters ///////////

    public String getFieldName() {
        return this.fieldName;
    }

    public boolean setFieldName(String fieldName) {
        if(fieldName.isEmpty()){
            System.out.println("The name of a field cannot be empty");
            return false;
        }

        this.fieldName = fieldName;

        return true;
    }

    public double getPropertyCost() {
        return this.propertyCost;
    }

    public boolean setPropertyCost(double propertyCost) {
        if (propertyCost < 0){
            System.out.println("The cost of a field cannot be negative");
            return false;
        }

        this.propertyCost = propertyCost;

        return true;
    }

    public static Field getFieldByName(String fieldName){
        //TODO - change this to a query for DB
        return new Field(fieldName,0);
    }

}
