import domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTests {

    // ========================= Fan receive Alerts from Personal Page Tests ============================
    Fan fanMail;
    Fan fanNotMail;

    TeamPage teamPage;
    HashMap<String, TeamPlayer> players;
    HashMap<String, TeamCoach> coaches;
    HashMap<String, TeamManager> managers;
    Field fieldPlayerPage;

    CoachPage coachPage;
    Team teamCoach;

    PlayerPage playerPage;
    Date birthdayDate;

    @BeforeEach
    public void insert() {

        // ========================= Fans receive Alerts from Personal Page Tests ============================
        fanMail = new Fan("FanNameMail", "euguman@gmail.com");
        fanMail.setMail(true);
        fanNotMail = new Fan("FanNameNotMail", "");
        fanNotMail.setMail(false);

        // playerPage
        teamPage = new TeamPage(new Team("unReal Hadera", new Field("Dinamo", 500), new TeamOwner("Owner", "")));
        players = new HashMap<>();
        players.put("Test player 1", new TeamPlayer("Test player 1", ""));
        players.put("Test player 2", new TeamPlayer("Test player 2", ""));

        coaches = new HashMap<>();
        coaches.put("Test coach 1", new TeamCoach("Test coach 1", ""));
        coaches.put("Test coach 2", new TeamCoach("Test coach 2", ""));

        managers = new HashMap<>();
        managers.put("Test manager 1", new TeamManager("Test manager 1", ""));
        managers.put("Test manager 2", new TeamManager("Test manager 2", ""));

        fieldPlayerPage = new Field("Test player page field", 500);

        // coachPage
        coachPage = new CoachPage(new TeamCoach("CoachName", ""), "My best page coach");
        teamCoach = new Team("Test Coach Team", new Field("Test field", 200), new TeamOwner("test name", ""));

        // playersPage
        playerPage = new PlayerPage(new TeamPlayer("PlayerName", ""), "My best page player");
        birthdayDate = new Date(1992, Calendar.MAY, 7);

    }

    @AfterEach
    public void delete(){
        // ========================= Fans receive Alerts from Personal Page Tests ============================
        fanMail = null;
        playerPage = null;
        coachPage = null;
        teamPage = null;
        players = null;
        coaches = null;
        managers = null;
        fieldPlayerPage = null;


    }

    // ========================= Fans receive Alerts from Personal Page Tests ============================
    @Test
    void fansReceiveAlertsFromPersonalPage() {
        // TODO: check received mails in DB

        assertEquals(0, fanNotMail.getAlertsMessages().size());
        // Team Page

        //teamPage.addSubscriber(fanMail);
        teamPage.addSubscriber(fanNotMail);

        teamPage.setName("Test Name team");
        assertEquals("The new name is Test Name team", fanNotMail.getAlertsMessages().get(0).getMessage());
        teamPage.setInfo("Test info team");
        assertEquals("The new info is Test info team", fanNotMail.getAlertsMessages().get(1).getMessage());
        teamPage.setMail("Test mail team");
        assertEquals("The new mail is Test mail team", fanNotMail.getAlertsMessages().get(2).getMessage());
        teamPage.setPlayers(players);
        assertEquals("The new players are Test player 2, Test player 1", fanNotMail.getAlertsMessages().get(3).getMessage());
        teamPage.setCoaches(coaches);
        assertEquals("The new coaches are Test coach 2, Test coach 1", fanNotMail.getAlertsMessages().get(4).getMessage());
        teamPage.setManagers(managers);
        assertEquals("The new managers are Test manager 1, Test manager 2", fanNotMail.getAlertsMessages().get(5).getMessage());
        teamPage.setStadium(fieldPlayerPage);
        assertEquals("The new stadium is Test player page field", fanNotMail.getAlertsMessages().get(6).getMessage());

        //coachPage.addSubscriber(fanMail);
        coachPage.addSubscriber(fanNotMail);
        coachPage.setName("Test Name coach");
        assertEquals("The new name is Test Name coach", fanNotMail.getAlertsMessages().get(7).getMessage());
        coachPage.setInfo("Test info coach");
        assertEquals("The new info is Test info coach", fanNotMail.getAlertsMessages().get(8).getMessage());
        coachPage.setMail("Test mail coach");
        assertEquals("The new mail is Test mail coach", fanNotMail.getAlertsMessages().get(9).getMessage());
        coachPage.setCurrentTeam(teamCoach);
        assertEquals("The new team is Test Coach Team", fanNotMail.getAlertsMessages().get(10).getMessage());
        coachPage.setQualification("Test qualification");
        assertEquals("The new qualification is Test qualification", fanNotMail.getAlertsMessages().get(11).getMessage());
        coachPage.setRole("Test Role");
        assertEquals("The new role is Test Role", fanNotMail.getAlertsMessages().get(12).getMessage());

        //playerPage.addSubscriber(fanMail);
        playerPage.addSubscriber(fanNotMail);
        playerPage.setName("Test Name player");
        assertEquals("The new name is Test Name player", fanNotMail.getAlertsMessages().get(13).getMessage());
        playerPage.setInfo("Test info player");
        assertEquals("The new info is Test info player", fanNotMail.getAlertsMessages().get(14).getMessage());
        playerPage.setMail("Test mail player");
        assertEquals("The new mail is Test mail player", fanNotMail.getAlertsMessages().get(15).getMessage());
        playerPage.setBirthDate(birthdayDate);
        assertEquals("The new birthday is Sat May 07 00:00:00 IDT 3892", fanNotMail.getAlertsMessages().get(16).getMessage());
        playerPage.setPosition("Test position");
        assertEquals("Position is Test position", fanNotMail.getAlertsMessages().get(17).getMessage());
        playerPage.setSquadNumber("Test squad");
        assertEquals("The new squad number is Test squad", fanNotMail.getAlertsMessages().get(18).getMessage());

    }

    // ========================= Fans receive Alerts from Game Tests ============================
    @Test
    void fansReceiveAlertsFromGame() {

    }

    // ========================= Referees receive Alerts from Game Tests ========================
    @Test
    void refereesReceiveAlertsFromGame() {

    }

    // ========================= Sys Admins receive Alerts (complaints) from Fans Tests ======================
    @Test
    void sysAdminsReceiveAlertsFromFans() {

    }
}