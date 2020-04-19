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

    @BeforeEach
    public void init(){
        field = new Field("test",100);
        owner = new TeamOwner("ownerUser","owner@gmail.com");
        team = new Team("test",field,owner);
    }

    //TODO: in the next versions, we should delete the objects from the DB
    @AfterEach
    public void finish(){
        field = null;
        owner = null;
        team =  null;
    }

    @Test
    void setTeamEmail() {
        team.setTeamEmail("new@gmail.com");
        assertEquals("new@gmail.com",team.getTeamEmail());
    }

    @Test
    void addProperty() {
        Field field1 = new Field("test",1000);
        TeamPlayer player = new TeamPlayer("playeruser","player@gmail.com",new Date(1998,02,02),"player","5");
        TeamCoach coach = new TeamCoach("coachPlayer","coach@gmail.com");
        TeamManager manager = new TeamManager("manageUser","man@gmail.com");

        assertEquals(1,team.getFields().size());
        assertEquals(0,team.getPlayers().size());
        assertEquals(0,team.getCoaches().size());
        assertEquals(0,team.getManagers().size());

        team.addProperty(field1);
        team.addProperty(player);
        team.addProperty(coach);
        team.addProperty(manager);

        assertEquals(2,team.getFields().size());
        assertEquals(1,team.getPlayers().size());
        assertEquals(this.team,team.getPlayers().get(player.getUserName()).getCurrentTeam());
        assertEquals(1,team.getCoaches().size());
        assertEquals(this.team,team.getCoaches().get(coach.getUserName()).getCurrentTeam());
        assertEquals(1,team.getManagers().size());
        assertEquals(this.team,team.getManagers().get(manager.getUserName()).getCurrentTeam());

    }

    @Test
    void removeProperty() {
        Field field1 = new Field("test",1000);
        TeamPlayer player = new TeamPlayer("playeruser","player@gmail.com",new Date(1998,02,02),"player","5");
        TeamCoach coach = new TeamCoach("coachPlayer","coach@gmail.com");
        TeamManager manager = new TeamManager("manageUser","man@gmail.com");

        team.addProperty(field1);
        team.addProperty(player);
        team.addProperty(coach);
        team.addProperty(manager);

        assertEquals(2,team.getFields().size());
        assertEquals(1,team.getPlayers().size());
        assertEquals(1,team.getCoaches().size());
        assertEquals(1,team.getManagers().size());

        team.removeProperty(field1);
        team.removeProperty(player);
        team.removeProperty(coach);
        team.removeProperty(manager);

        assertEquals(1,team.getFields().size());
        assertEquals(0,team.getPlayers().size());
        assertEquals(0,team.getCoaches().size());
        assertEquals(0,team.getManagers().size());
    }


    @Test
    void addOwner() {
        TeamOwner ownerTest2 = new TeamOwner("ownerUse2r","owner2@gmail.com");
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

}