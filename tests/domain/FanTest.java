package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FanTest {

    Fan fan;
    Notification notification;
    ArrayList<SystemAdministrator> sysAdmins;

    @BeforeEach
    public void insert() {
        fan = new Fan("Evgeny", "euguman@gmail.com");
        notification = new Notification("Title sysAdmin", "Text");

        sysAdmins = new ArrayList<>();
        sysAdmins.add(new SystemAdministrator("SysAdmin_1", "euguman@gmail.com"));
        sysAdmins.add(new SystemAdministrator("SysAdmin_2", "euguman@gmail.com"));
    }

    @AfterEach
    public void delete(){
        fan = null;
        sysAdmins = null;
        notification = null;
    }

    @Test
    void sendComplaintToSysAdmin() {
        Map<String, Boolean> isSentMap = fan.sendComplaintToSysAdmin(sysAdmins, notification);

        for(String user : isSentMap.keySet()){
            assertTrue(isSentMap.get(user));
        }
    }

    @Test
    void getSearchHistory(){
        assertArrayEquals(new String[]{"A", "B", "C", "D"}, fan.getSearchHistory());
    }
}