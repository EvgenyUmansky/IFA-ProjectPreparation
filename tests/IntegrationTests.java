import domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for mail
    private final PrintStream originalOut = System.out;
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


    // ========================= Referees receive Alerts from Game Tests ========================
    Game game;
    GameEvent gameEvent;
    Referee refereeMail;
    Referee refereeNotMail;


    // ========================= Sys Admins receive Alerts (complaints) from Fans Tests ===================
    SystemAdministrator sysAdminMail;
    SystemAdministrator sysAdminNotMail;
    ArrayList<SystemAdministrator> sysAdminsList;
    AlertNotification alertNotification;


    // ========================= TeamMembers receive Alerts from Team Tests =================================
    Team team;
    User teamOwnerUser;

    TeamPlayer teamPlayerMail;
    TeamPlayer teamPlayerNotMail;

    TeamManager teamManagerMail;
    TeamManager teamManagerNotMail;

    TeamCoach teamCoachMail;
    TeamCoach teamCoachNotMail;

    @BeforeEach
    public void insert() {
        System.setOut(new PrintStream(outContent));


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


        // ========================= Referees receive Alerts from Game Tests ========================
        game = new Game(new League(2020, new TwoGameSchedulingMethod(), new RankingMethod(), "Prime"), new Team("Real Madrid", new Field("Enspania", 100), new TeamOwner("Zidane", "euguman@gmail.com")), new Team("unReal Madrid", new Field("Magadan", 400), new TeamOwner("Ronaldo", "euguman@gmail.com")), new Field("Magadan", 400), LocalDateTime.now(), new ArrayList<>());
       // gameEvent = new GameEvent(LocalDateTime.now().plusMinutes(15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 30, GameAlert.GOAL, "desc");

        refereeMail = new Referee("refereeMail", "euguman@gmail.com");
        refereeMail.setMail(true);
        refereeNotMail = new Referee("refereeNotMail", "");
        refereeNotMail.setMail(false);


        // ========================= Sys Admins receive Alerts (complaints) from Fans Tests ===================
        sysAdminMail = new SystemAdministrator("sysAdminMail", "euguman@gmail.com");
        sysAdminMail.setMail(true);
        sysAdminNotMail = new SystemAdministrator("sysAdminNotMail", "");
        sysAdminNotMail.setMail(false);

        sysAdminsList = new ArrayList<>();
        sysAdminsList.add(sysAdminMail);
        sysAdminsList.add(sysAdminNotMail);

        alertNotification = new AlertNotification("Complaint to admins", "This project is very very hard, it eats all our time!");


        // ========================= TeamMembers receive Alerts from Team Tests =================================
        teamOwnerUser = new User("uname", "1234", "name", "");
        teamOwnerUser.addRoleToUser(Role.TEAM_OWNER);
        team = new Team("Test team", new Field("Test field", 100), (TeamOwner) teamOwnerUser.getRoles().get(Role.TEAM_OWNER));


        teamPlayerMail = new TeamPlayer("Test player mail", "euguman@gmail.com");
        teamPlayerMail.setMail(true);
        teamPlayerNotMail = new TeamPlayer("Test player not mail", "");
        teamPlayerNotMail.setMail(false);

        teamManagerMail = new TeamManager("Test manager mail", "euguman@gmail.com");
        teamManagerMail.setMail(true);
        teamManagerNotMail = new TeamManager("Test manager not mail", "");
        teamManagerNotMail.setMail(false);

        teamCoachMail = new TeamCoach("Test coach mail", "euguman@gmail.com");
        teamCoachMail.setMail(true);
        teamCoachNotMail = new TeamCoach("Test coach not mail", "");
        teamCoachNotMail.setMail(false);
        team.addPlayer(teamPlayerMail);
        team.addPlayer(teamPlayerNotMail);
        team.addCoach(teamCoachMail);
        team.addCoach(teamCoachNotMail);
        team.addSubscriber(teamManagerMail);
        team.addSubscriber(teamManagerNotMail);
    }

    @AfterEach
    public void delete(){
        System.setOut(originalOut);

        // ========================= Fans receive Alerts from Personal Page Tests ============================
        fanMail = null;
        playerPage = null;
        coachPage = null;
        teamPage = null;
        players = null;
        coaches = null;
        managers = null;
        fieldPlayerPage = null;


        // ========================= Referees receive Alerts from Game Tests ========================
        game = null;
        gameEvent = null;

        refereeMail = null;
        refereeNotMail = null;


        // ========================= Sys Admins receive Alerts (complaints) from Fans Tests ===================
        sysAdminMail = null;
        sysAdminNotMail = null;
        sysAdminsList = null;


        // ========================= TeamMembers receive Alerts from Team Tests ===============================
        team = null;
        teamOwnerUser = null;


        teamPlayerMail = null;;
        teamPlayerNotMail = null;;

        teamManagerMail = null;;
        teamManagerNotMail = null;;

        teamCoachMail = null;;
        teamCoachNotMail = null;;
    }

    // ========================= Fans receive Alerts from Personal Page Tests ============================
    @Test
    void fansReceiveAlertsFromPersonalPage() {

        assertEquals(0, fanNotMail.getNotifications().size());
        // Team Page

        teamPage.addSubscriber(fanMail);
        teamPage.addSubscriber(fanNotMail);


        teamPage.setName("Test Name team");
        assertEquals("The new name is Test Name team", fanNotMail.getNotifications().get(0).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString()); // check print
        outContent.reset(); // clean print log

        teamPage.setInfo("Test info team");
        assertEquals("The new info is Test info team", fanNotMail.getNotifications().get(1).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        teamPage.setMail("Test mail team");
        assertEquals("The new mail is Test mail team", fanNotMail.getNotifications().get(2).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        teamPage.setPlayers(players);
        assertEquals("The new players are Test player 2, Test player 1", fanNotMail.getNotifications().get(3).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        teamPage.setCoaches(coaches);
        assertEquals("The new coaches are Test coach 2, Test coach 1", fanNotMail.getNotifications().get(4).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        teamPage.setManagers(managers);
        assertEquals("The new managers are Test manager 1, Test manager 2", fanNotMail.getNotifications().get(5).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        teamPage.setStadium(fieldPlayerPage);
        assertEquals("The new stadium is Test player page field", fanNotMail.getNotifications().get(6).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        coachPage.addSubscriber(fanMail);
        coachPage.addSubscriber(fanNotMail);
        coachPage.setName("Test Name coach");
        assertEquals("The new name is Test Name coach", fanNotMail.getNotifications().get(7).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        coachPage.setInfo("Test info coach");
        assertEquals("The new info is Test info coach", fanNotMail.getNotifications().get(8).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        coachPage.setMail("Test mail coach");
        assertEquals("The new mail is Test mail coach", fanNotMail.getNotifications().get(9).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        coachPage.setCurrentTeam(teamCoach.getTeamName());
        assertEquals("The new team is Test Coach Team", fanNotMail.getNotifications().get(10).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        coachPage.setQualification("Test qualification");
        assertEquals("The new qualification is Test qualification", fanNotMail.getNotifications().get(11).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        coachPage.setRole("Test Role");
        assertEquals("The new role is Test Role", fanNotMail.getNotifications().get(12).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        playerPage.addSubscriber(fanMail);
        playerPage.addSubscriber(fanNotMail);
        playerPage.setName("Test Name player");
        assertEquals("The new name is Test Name player", fanNotMail.getNotifications().get(13).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        playerPage.setInfo("Test info player");
        assertEquals("The new info is Test info player", fanNotMail.getNotifications().get(14).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        playerPage.setMail("Test mail player");
        assertEquals("The new mail is Test mail player", fanNotMail.getNotifications().get(15).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        playerPage.setBirthDate(birthdayDate);
        assertEquals("The new birthday is Sat May 07 00:00:00 IDT 3892", fanNotMail.getNotifications().get(16).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        playerPage.setPosition("Test position");
        assertEquals("Position is Test position", fanNotMail.getNotifications().get(17).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        playerPage.setSquadNumber("Test squad");
        assertEquals("The new squad number is Test squad", fanNotMail.getNotifications().get(18).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

    }

    // ========================= Fans receive Alerts from Game Tests ============================
    @Test
    void fansReceiveAlertsFromGame() throws Exception {
        game.addFanToAlerts(fanMail);
        game.addFanToAlerts(fanNotMail);

        game.sendAlertScoreToFan();
        assertEquals("The score of the game between Real Madrid and unReal Madrid is 0:0", fanNotMail.getNotifications().get(0).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        game.sendAlertCloseGame();
        assertEquals("Before the game between Real Madrid and unReal Madrid remains one day!", fanNotMail.getNotifications().get(1).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        game.sendAlertChangeDateGame();
        assertEquals("The new date of the game between Real Madrid and unReal Madrid is " + game.getGameDate().withSecond(0).withNano(0).toString(), fanNotMail.getNotifications().get(2).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        game.addEvent(gameEvent);
        assertEquals(gameEvent.toString(), fanNotMail.getNotifications().get(3).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

       // game.changeEvent(gameEvent, null, -1, null, null);
        assertEquals(gameEvent.toString(), fanNotMail.getNotifications().get(4).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();
    }

    // ========================= Referees receive Alerts from Game Tests ========================
    @Test
    void refereesReceiveAlertsFromGame() throws Exception {
        game.addRefereeToGame(refereeMail);
        game.addRefereeToGame(refereeNotMail);

        game.sendAlertCloseGame();
        assertEquals("Before the game between Real Madrid and unReal Madrid remains one day!", refereeNotMail.getNotifications().get(0).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        game.sendAlertChangeDateGame();
        assertEquals("The new date of the game between Real Madrid and unReal Madrid is " + game.getGameDate().withSecond(0).withNano(0).toString(), refereeNotMail.getNotifications().get(1).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

        game.addEvent(gameEvent);
        assertEquals(gameEvent.toString(), refereeNotMail.getNotifications().get(2).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();

      //  game.changeEvent(gameEvent, null, -1, null, null);
        assertEquals(gameEvent.toString(), refereeNotMail.getNotifications().get(3).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();
    }

    // ========================= Sys Admins receive Alerts (complaints) from Fans Tests ======================
    @Test
    void sysAdminsReceiveAlertsFromFans() {
        fanNotMail.sendComplaintToSysAdmin(sysAdminsList, alertNotification);
        assertEquals(alertNotification.getMessage(), sysAdminsList.get(1).getNotifications().get(0).getMessage());
        assertEquals("The mail sent successfully\r\n", outContent.toString());
        outContent.reset();
    }

    // ========================= TeamMembers receive Alerts from Team Tests =================================
    @Test
    void TeamMembersReceiveAlertsFromTeam() {
        team.closeTeam(teamOwnerUser);
        String mailPrintedMassage = "The mail sent successfully\r\n";
        mailPrintedMassage = mailPrintedMassage + mailPrintedMassage + mailPrintedMassage;
        assertEquals("you team close temporary", teamPlayerNotMail.getNotifications().get(0).getMessage());
        assertEquals("you team close temporary", teamCoachNotMail.getNotifications().get(0).getMessage());
        assertEquals("you team close temporary", teamManagerNotMail.getNotifications().get(0).getMessage());
        assertEquals("you team close temporary", team.getOwners().get(teamOwnerUser.getUserName()).getNotifications().get(0).getMessage());
        assertEquals(mailPrintedMassage, outContent.toString());
        outContent.reset();
    }



}