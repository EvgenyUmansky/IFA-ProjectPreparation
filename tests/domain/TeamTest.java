package domain;

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
    public void init() {
        field = new Field("test", 100);
        owner = new TeamOwner("ownerUser", "owner@gmail.com");
        team = new Team("test", field, owner);
        player = new TeamPlayer("playeruser", "player@gmail.com", new Date(1998, 02, 02), "player", "5");
        manager = new TeamManager("managerUser", "manager@gmail.com");
        coach = new TeamCoach("coachUser", "coach@gmail.com");
    }

    //TODO: in the next versions, we should delete the objects from the DB
    @AfterEach
    public void finish() {
        field = null;
        owner = null;
        team = null;
        player = null;
        manager = null;
        coach = null;
    }

    @Test
    void setTeamEmail() {
        team.setTeamEmail("new@gmail.com");
        assertEquals("new@gmail.com", team.getTeamEmail());
    }


    @Test
    void addOwner() {
        TeamOwner ownerTest2 = new TeamOwner("ownerUser2", "owner2@gmail.com");
        assertEquals(1, team.getOwners().size());
        team.addOwner(ownerTest2);
        assertEquals(2, team.getOwners().size());
    }

    @Test
    void removeOwnerPositiveCase() {
        User ownerUser = new User("ownerUser", "123", "shak", "s@mail.com");
        User ownerUser2 = new User("ownerUser2", "123", "shak", "s@mail.com");
        TeamOwner ownerTest2 = new TeamOwner("ownerUser2", "s@mail.com");
        owner.setTeam(team);
        ownerUser.addRoleToUser(Role.TEAM_OWNER, owner);
        ownerUser2.addRoleToUser(Role.TEAM_OWNER, ownerTest2);

        assertEquals(1, team.getOwners().size());
        team.addOwner(ownerUser, ownerUser2);
        assertEquals(2, team.getOwners().size());
        team.removeOwner(ownerUser2);
        assertEquals(1, team.getOwners().size());
    }

    @Test
    void removeOwnerNegativeCase() {
        assertEquals(1, team.getOwners().size());
        //assertFalse(team.removeOwner(owner));
        assertEquals(1, team.getOwners().size());
    }

    @Test
    void addManager() {
        User ownerUser = new User("ownerUser", "123", "shak", "s@mail.com");
        User managerUser = new User("managerUser", "144", "on", "o@gmail.com");
        ownerUser.addRoleToUser(Role.TEAM_OWNER, owner);
        managerUser.addRoleToUser(Role.TEAM_MANAGER, manager);
        assertEquals(0, team.getManagers().size());
        assertFalse(team.getManagers().containsKey(manager.getUserName()));

        team.addManager(ownerUser, managerUser);
        assertEquals(1, team.getManagers().size());
        assertTrue(team.getManagers().containsKey(manager.getUserName()));
    }


    @Test
    void removeManager() {
        User ownerUser = new User("ownerUser", "123", "shak", "s@mail.com");
        User managerUser = new User("managerUser", "144", "on", "o@gmail.com");
        ownerUser.addRoleToUser(Role.TEAM_OWNER, owner);
        managerUser.addRoleToUser(Role.TEAM_MANAGER, manager);
        team.addManager(ownerUser, managerUser);
        assertEquals(1, team.getManagers().size());
        assertTrue(team.getManagers().containsKey(manager.getUserName()));

        team.removeManager(managerUser);
        assertEquals(0, team.getManagers().size());
        assertFalse(team.getManagers().containsKey(manager.getUserName()));
    }


    @Test
    void addPlayer() {
        assertEquals(0, team.getPlayers().size());
        team.addPlayer(player);
        assertEquals(1, team.getPlayers().size());
        assertEquals(this.team, team.getPlayers().get(player.getUserName()).getCurrentTeam());
    }


    @Test
    void addCoach() {
        assertEquals(0, team.getCoaches().size());
        team.addCoach(coach);
        assertEquals(1, team.getCoaches().size());
        assertEquals(this.team, team.getCoaches().get(coach.getUserName()).getCurrentTeam());
    }


    @Test
    void addField() {
        assertEquals(1, team.getFields().size());
        Field field1 = new Field("test1", 100);
        team.addField(field1);
        assertEquals(2, team.getFields().size());
    }


    @Test
    void removePlayer() {
        team.addPlayer(player);
        assertEquals(1, team.getPlayers().size());
        assertTrue(team.getPlayers().containsKey(player.getUserName()));

        team.removePlayer(player);
        assertEquals(0, team.getPlayers().size());
        assertFalse(team.getPlayers().containsKey(player.getUserName()));
    }

    @Test
    void removeCoach() {
        team.addCoach(coach);
        assertEquals(1, team.getCoaches().size());
        assertTrue(team.getCoaches().containsKey(coach.getUserName()));

        team.removeCoach(coach);
        assertEquals(0, team.getCoaches().size());
        assertFalse(team.getCoaches().containsKey(coach.getUserName()));
    }


    @Test
    void removeField() {
        Field field1 = new Field("test 1", 1000);
        team.addField(field1);
        assertEquals(2, team.getFields().size());
        assertTrue(team.getFields().containsKey(field1.getFieldName()));

        team.removeField(field1);
        assertEquals(1, team.getFields().size());
        assertFalse(team.getFields().containsKey(field1.getFieldName()));
    }


    @Test
    public void getPlayers() {
        assertEquals(0, team.getPlayers().size());
        team.addPlayer(player);
        assertEquals(1, team.getPlayers().size());

    }

    @Test
    public void SetTeamEmail() {
        team.setTeamEmail("avbbb@jovani.cool");
        assertEquals("avbbb@jovani.cool", team.getTeamEmail());
    }


    @Test
    public void closeTeam() {
        User ownerUser = new User("ownerUser2", "123", "shak", "s@mail.com");
        TeamOwner ownerTest2 = new TeamOwner("ownerUser2", "owner2@gmail.com");
        ownerUser.addRoleToUser(Role.TEAM_OWNER, ownerTest2);
        assertEquals(1, team.getOwners().size());
        team.addOwner(ownerTest2);
        team.closeTeam(ownerUser);
        assertNotEquals(0, team.getTeamStatus().compareTo(TeamStatus.Open));
        team.openTeam();
        assertEquals(0, team.getTeamStatus().compareTo(TeamStatus.Open));
    }

    @Test
    public void closeTeam2() {
        User ownerUser = new User("ownerUser2", "123", "shak", "s@mail.com");
        TeamOwner ownerTest2 = new TeamOwner("ownerUser2", "owner2@gmail.com");
        ownerUser.addRoleToUser(Role.SYSTEM_ADMIN);
        ownerUser.addRoleToUser(Role.TEAM_OWNER, ownerTest2);
        assertEquals(1, team.getOwners().size());
        team.addOwner(ownerTest2);
        team.closeTeam(ownerUser);
        assertNotEquals(0, team.getTeamStatus().compareTo(TeamStatus.Open));
//        team.openTeam();
//        assertEquals(0, team.getTeamStatus().compareTo(TeamStatus.Open));
    }


    @Test
    public void openTeam() {
        User ownerUser = new User("ownerUser2", "123", "shak", "s@mail.com");
        TeamOwner ownerTest2 = new TeamOwner("ownerUser2", "owner2@gmail.com");
        ownerUser.addRoleToUser(Role.TEAM_OWNER, ownerTest2);
        assertEquals(1, team.getOwners().size());
        team.addOwner(ownerTest2);
        team.closeTeam(ownerUser);
        assertNotEquals(0, team.getTeamStatus().compareTo(TeamStatus.Open));
        team.openTeam();
        assertEquals(0, team.getTeamStatus().compareTo(TeamStatus.Open));
    }

    @Test
    public void getPlayer() {
        team.addPlayer(player);
        assertSame(player, team.getPlayer(player.getUserName()));
        assertTrue(team.getPlayers().containsKey(player.getUserName()));
    }

    @Test
    public void getCoach() {
        team.addCoach(coach);
        assertSame(coach, team.getCoach(coach.getUserName()));
        assertTrue(team.getCoaches().containsKey(coach.getUserName()));
    }

    @Test
    public void getFields() {
        assertEquals(1, team.getFields().size());
        Field field2 = new Field("field2", 100);
        team.addField(field2);
        assertEquals(2, team.getFields().size());
        assertTrue(team.getFields().containsKey(field.getFieldName()));
        assertTrue(team.getFields().containsKey(field2.getFieldName()));
    }

    @Test
    public void getOwners() {
        assertEquals(1, team.getOwners().size());
        assertTrue(team.getOwners().containsKey(owner.getUserName()));
    }

    @Test
    public void getBudget() {
        Budget budget = new Budget();
        team.setBudget(budget);
        assertSame(budget, team.getBudget());
    }

    @Test
    public void addSubscriber() {
        assertEquals(0, team.getAlert().getInSystemAlertList().size());
        team.addSubscriber(owner);
        assertEquals(1, team.getAlert().getInSystemAlertList().size());

    }

    @Test
    public void removeSubscriber() {
        assertEquals(0, team.getAlert().getInSystemAlertList().size());
        team.addSubscriber(owner);
        assertEquals(1, team.getAlert().getInSystemAlertList().size());
        team.removeSubscriber(owner);
        assertEquals(0, team.getAlert().getInSystemAlertList().size());

    }

    @Test
    public void getTeamStatus() {

    }

    @Test
    public void setBudget() {
        Budget budget = new Budget();
        budget.setBudget(500000);
        team.setBudget(budget);
        assertEquals(500000, team.getBudget().getBudget());

    }


    @Test
    public void getTeamPage() {
        assertNull(team.getTeamPage());
    }
}

