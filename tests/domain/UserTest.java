package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user;

    @BeforeEach
    public void setUp() {
        user = new User("username","1234","user","user@gmail.com");
    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

    @Test
    public void addRoleToUser(){
        assertEquals(1, user.getRoles().size());
        user.addRoleToUser(Role.REFEREE);
        user.addRoleToUser(Role.TEAM_MANAGER);
        assertEquals(3,user.getRoles().size());
    }

    @Test
    public void removeRoleFromUser(){
        user.addRoleToUser(Role.REFEREE);
        assertEquals(2, user.getRoles().size());

        user.removeRoleFromUser(Role.REFEREE);
        assertEquals(1, user.getRoles().size());
        assertFalse(user.getRoles().containsKey(Role.REFEREE));
    }

    @Test
    public void setProfileDetails(){
        user.setProfileDetails("12345","userNew","new@gmail.com");
        assertEquals("12345",user.getPassword());
        assertEquals("userNew",user.getName());
        assertEquals("new@gmail.com",user.getMail());
    }

    @Test
    public void isValidPassword(){
        assertTrue(User.isValidPassword("12345"));
        assertFalse(User.isValidPassword("!23456"));
        assertFalse(User.isValidPassword("123"));
    }

    @Test
    public void isValidUserName(){
        assertTrue(User.isValidUserName("a12345"));
        assertTrue(User.isValidUserName("username"));
        assertFalse(User.isValidUserName("!23456"));
        assertFalse(User.isValidUserName("123"));
        assertFalse(User.isValidUserName("1"));

    }

    @Test
    public void isConnected(){
        user.connect();
        assertTrue(user.isConnected());
        user.disconnect();
        assertFalse(user.isConnected());
    }

    @Test
    public void setRoles(){
        HashMap<Role,Subscriber> roles = new HashMap<>();
        roles.put(Role.FAN,new Fan("fantastic","a@gmail.com"));
        user.setRoles(roles);

        assertSame(roles, user.getRoles());
    }

    @Test
    public void setClosed(){
        assertFalse(user.isClosed());
        user.setClosed(true);
        assertTrue(user.isClosed());
        user.setClosed(false);
        assertFalse(user.isClosed());
    }

    @Test
    public void closeUser(){
        assertTrue(user.closeUser());
        user.setClosed(false);
        user.addRoleToUser(Role.SYSTEM_ADMIN,new SystemAdministrator(user.getUserName(),"admin@mail.com"));
        assertFalse(user.closeUser());
    }

    @Test
    public void closeUser2(){
        assertTrue(user.closeUser());
        user.setClosed(true);
        user.addRoleToUser(Role.TEAM_OWNER,new TeamOwner(user.getUserName(),"admin@mail.com"));
        assertFalse(user.closeUser());
        assertFalse(user.closeUser());
    }

    @Test
    public void getPages(){
        ArrayList<PersonalPage> personalPages = user.getPages();
        assertNotNull(personalPages);

    }


    @Test
    public void getProfileDetails(){
        String profD = user.getProfileDetails();
        assertNotNull(profD);
    }



}