package domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    Field field;
    TeamOwner owner;
    Team team;
    TeamPlayer player;
    TeamManager manager;
    TeamCoach coach;

    @BeforeEach
    public void init(){
        field = new Field("test",100);
        owner = new TeamOwner("ownerUser","owner@gmail.com");
        team = new Team("test",field,owner);
        player = new TeamPlayer("playeruser","player@gmail.com",new Date(1998,02,02),"player","5");
        manager = new TeamManager("managerUser","manager@gmail.com");
        coach = new TeamCoach("coachUser","coach@gmail.com");
    }

    //TODO: in the next versions, we should delete the objects from the DB
    @AfterEach
    public void finish(){
        field = null;
        owner = null;
        team =  null;
        player = null;
        manager = null;
        coach = null;
    }

    @Test
    void setTeamEmail() {
        team.setTeamEmail("new@gmail.com");
        assertEquals("new@gmail.com",team.getTeamEmail());
    }


    @Test
    void addOwner() {
        TeamOwner ownerTest2 = new TeamOwner("ownerUser2","owner2@gmail.com");
        assertEquals(1,team.getOwners().size());
        team.addOwner(ownerTest2);
        assertEquals(2,team.getOwners().size());
    }

    @Test
    void removeOwnerPositiveCase() {
        TeamOwner ownerTest2 = new TeamOwner("ownerUse2r","owner2@gmail.com");
        assertEquals(1,team.getOwners().size());
        team.addOwner(ownerTest2);
        assertEquals(2,team.getOwners().size());
        //assertTrue(team.removeOwner(ownerTest2));
        assertEquals(1,team.getOwners().size());
    }

    @Test
    void removeOwnerNegativeCase() {
        assertEquals(1,team.getOwners().size());
        //assertFalse(team.removeOwner(owner));
        assertEquals(1,team.getOwners().size());
    }

    @Test
    void addManager(){
        assertEquals(0,team.getManagers().size());
        assertFalse(team.getManagers().containsKey(manager.getUserName()));

        team.addManager(manager);
        assertEquals(1,team.getManagers().size());
        assertTrue(team.getManagers().containsKey(manager.getUserName()));
    }


    @Test
    void removeManager(){
        team.addManager(manager);
        assertEquals(1,team.getManagers().size());
        assertTrue(team.getManagers().containsKey(manager.getUserName()));

        team.removeManager(manager);
        assertEquals(0,team.getManagers().size());
        assertFalse(team.getManagers().containsKey(manager.getUserName()));
    }


    @Test
    void addPlayer(){
        assertEquals(0,team.getPlayers().size());
        team.addPlayer(player);
        assertEquals(1,team.getPlayers().size());
        assertEquals(this.team,team.getPlayers().get(player.getUserName()).getCurrentTeam());
    }


    @Test
    void addCoach(){
        assertEquals(0,team.getCoaches().size());
        team.addCoach(coach);
        assertEquals(1,team.getCoaches().size());
        assertEquals(this.team,team.getCoaches().get(coach.getUserName()).getCurrentTeam());
    }


    @Test
    void addField(){
        assertEquals(1,team.getFields().size());
        Field field1 = new Field("test1",100);
        team.addField(field1);
        assertEquals(2,team.getFields().size());
    }


    @Test
    void removePlayer(){
        team.addPlayer(player);
        assertEquals(1,team.getPlayers().size());
        assertTrue(team.getPlayers().containsKey(player.getUserName()));

        team.removePlayer(player);
        assertEquals(0,team.getPlayers().size());
        assertFalse(team.getPlayers().containsKey(player.getUserName()));
    }

    @Test
    void removeCoach(){
        team.addCoach(coach);
        assertEquals(1,team.getCoaches().size());
        assertTrue(team.getCoaches().containsKey(coach.getUserName()));

        team.removeCoach(coach);
        assertEquals(0,team.getCoaches().size());
        assertFalse(team.getCoaches().containsKey(coach.getUserName()));
    }


    @Test
    void removeField(){
        Field field1 = new Field("test",1000);
        team.addField(field1);
        assertEquals(2,team.getFields().size());
        assertTrue(team.getFields().contains(field1));

        team.removeField(field1);
        assertEquals(1,team.getFields().size());
        assertFalse(team.getFields().contains(field1));
    }




}