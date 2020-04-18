//package domain;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FanTest {
//
//    Fan fan;
//    AlertNotification alertNotification;
//    ArrayList<SystemAdministrator> sysAdmins;
//
//    @BeforeEach
//    public void insert() {
//        fan = new Fan("Evgeny", "euguman@gmail.com", true);
//        alertNotification = new AlertNotification("Title sysAdmin", "Text");
//
//        sysAdmins = new ArrayList<>();
//        sysAdmins.add(new SystemAdministrator("SysAdmin_1", "euguman@gmail.com", true));
//        sysAdmins.add(new SystemAdministrator("SysAdmin_2", "euguman@gmail.com", false));
//    }
//
//    @AfterEach
//    public void delete(){
//        fan = null;
//        sysAdmins = null;
//        alertNotification = null;
//    }
//
//    @Test
//    void sendComplaintToSysAdmin() {
//        Map<String, Boolean> isSentMap = fan.sendComplaintToSysAdmin(sysAdmins, alertNotification);
//
//        for(String user : isSentMap.keySet()){
//            assertTrue(isSentMap.get(user));
//        }
//    }
//}