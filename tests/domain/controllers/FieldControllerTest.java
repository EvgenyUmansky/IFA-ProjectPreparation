package domain.controllers;

import DataAccess.DBAccess;
import DataAccess.FieldDBAccess;
import domain.Field;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FieldControllerTest {

    FieldController fieldController = new FieldController();
    DBAccess<Field> fda = FieldDBAccess.getInstance();


    @BeforeEach
    void setUp() {
        fda.save(new Field("field1",100000));
        fda.save(new Field("field2",100000));
        fda.save(new Field("field3",100000));
        fda.save(new Field("field4",100000));
        fda.save(new Field("field5",100000));
        fda.save(new Field("field6",100000));
        fda.save(new Field("field7",100000));
    }

    @AfterEach
    void tearDown() {
        fda.delete(new Field("field1",100000));
        fda.delete(new Field("field2",100000));
        fda.delete(new Field("field3",100000));
        fda.delete(new Field("field4",100000));
        fda.delete(new Field("field5",100000));
        fda.delete(new Field("field6",100000));
        fda.delete(new Field("field7",100000));
    }


    @Test
    void getAvailableFields() {
        assertTrue(fieldController.getAvailableFields().size() > 0);
    }
}