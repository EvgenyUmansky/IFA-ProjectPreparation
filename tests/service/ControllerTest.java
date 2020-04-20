package service;

import domain.*;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller = new Controller();
    // ========================= Fan Tests ============================
    Fan fanMail;
    Fan fanNotMail;

    // T 3.2
    TeamPage teamPage;
    CoachPage coachPage;
    PlayerPage playerPage;

    // T 3.3
    Game game;

    // T 3.4
    ArrayList<SystemAdministrator> sysAdmins;
    AlertNotification complaintToSysAdmin;

    // ========================= Referee Tests ========================
    Referee refereeMail;
    Referee refereeNotMail;

    // T10.3
    GameEvent gameEvent;
    GameEvent gameEventBeforeGame;


    @BeforeEach
    public void insert() {
        controller = new Controller();

        // ========================= Fan Tests ========================
        fanMail = new Fan("FanNameMail", "euguman@gmail.com");
        fanMail.setMail(true);
        fanNotMail = new Fan("FanNameNotMail", "");
        fanNotMail.setMail(false);

        // T3.2
        teamPage = new TeamPage(new Team("unReal Hadera", new Field("Dinamo", 500), new TeamOwner("Owner", "")));
        coachPage = new CoachPage(new TeamCoach("CoachName", ""), "My best page coach");
        playerPage = new PlayerPage(new TeamPlayer("PlayerName", ""), "My best page player");

        // T3.3, 10.3, 10.4
        game = new Game(new League("Test league"), new Team("Test guest team", new Field("Test field", 500), new TeamOwner("Test name", "")), new Team("Test team", new Field("Test field", 500), new TeamOwner("Test name", "")), new Field("Test field", 500), "2019-11-11 12:00", new ArrayList<>());

        // T3.4
        sysAdmins = new ArrayList<>();
        sysAdmins.add(new SystemAdministrator("Test sysAdmin mail", "euguman@gmail.com"));
        sysAdmins.get(0).setMail(true);
        sysAdmins.add(new SystemAdministrator("Test sysAdmin notMail", ""));
        sysAdmins.get(1).setMail(false);
        complaintToSysAdmin = new AlertNotification("Complaint title", "Complaint text");

        // ========================= Referee Tests ========================
        refereeMail = new Referee("testRefereeMail", "euguman@gmail.com");
        refereeMail.setQualification(4);
        refereeMail.setRefereeType(RefereeType.MAIN);
        refereeNotMail = new Referee("testRefereeNotMail", "");
        refereeNotMail.setQualification(5);
        refereeNotMail.setRefereeType(RefereeType.ASSISTANT);

        // T10.3, 10.4
        gameEvent = new GameEvent("2019-11-11 12:30", 30, GameAlert.INJURY, "Test description 1");
        gameEventBeforeGame = new GameEvent("2019-11-11 11:30", 30, GameAlert.INJURY, "Test description 2");
    }

    @AfterEach
    public void delete(){
        controller = null;
        // ========================= Fan Tests ============================
        fanMail = null;
        fanNotMail = null;
        // T 3.2
        playerPage = null;
        coachPage = null;
        teamPage = null;
        // T 3.3
        game = null;
        // T 3.4
        sysAdmins = null;
        complaintToSysAdmin = null;

        // ========================= Referee Tests ========================
        refereeMail = null;
        refereeNotMail = null;

        // T10.3, 10.4
        gameEvent = null;

    }

    @Test
    void connectToExternalSystems() {
    }

    @Test
    void login() {
    }

    // T3.1
    @Test
    void logout() {
    }

    @Test
    void register() {
    }

    
    // =================== User functions ===========================
    // ==============================================================

    @Test
    void getPagesByUsername() {
    }


    // =================== Personal Pages Tests =================
    // ==============================================================

    //T4.2, T5.2
    @Test
    void updateInfo() {
    }

    // =================== Team Player functions ====================
    // ==============================================================

    //T4.1
    @Test
    void updatePlayerDetails() {
    }

    // ======================= Coach Tests ============================
    // ====================================================================

    //T5.1
    @Test
    void updateCoachDetails() {
    }

    // ========================= Guest Tests ============================
    // ====================================================================
    //T2.4A
    @Test
    void getTeamDetails() {
        assertEquals("team", controller.getTeamDetails("team").getTeamName());
    }

    //T2.4B
    @Test
    void getPlayersDetails() {
        assertEquals("userName", controller.getPlayersDetails("userName").getUserName());

    }

    //T2.4C
    @Test
    void getCoachDetails() {
        assertEquals("userName", controller.getCoachDetails("userName").getUserName());

    }

    //T2.4D
    @Test
    void getLeagueDetails() {
        assertEquals("leagueName", controller.getLeagueDetails("leagueName").getLeagueName());
    }

    //T2.4E
    @Test
    void getSeasonDetails() {
        //TODO: change test accordingly
        assertEquals(0 , controller.getSeasonDetails(1998).size());
    }


    //T2.5
    @Test
    void searchByKeyWord() {
        //TODO: next iteration
    }


    // ========================= Fan Tests ============================
    // ====================================================================

    // T3.2
    @Test
    void addFanSubscriptionToPersonalPage() {
        // TODO: complete mail fan test with DB
        //controller.addFanSubscriptionToPersonalPage(teamPage, fanMail.getUserName());
        controller.addFanSubscriptionToPersonalPage(teamPage, fanNotMail.getUserName());
        //assertEquals(fanMail.getUserName(), ((Fan) teamPage.getAlert().getMailAlertList().toArray()[0]).getUserName());
        assertEquals(fanNotMail.getUserName(), ((Fan) teamPage.getAlert().getInSystemAlertList().toArray()[0]).getUserName());

        //controller.addFanSubscriptionToPersonalPage(coachPage, fanMail.getUserName());
        controller.addFanSubscriptionToPersonalPage(coachPage, fanNotMail.getUserName());
        //assertEquals(fanMail.getUserName(), ((Fan) coachPage.getAlert().getMailAlertList().toArray()[0]).getUserName());
        assertEquals(fanNotMail.getUserName(), ((Fan) coachPage.getAlert().getInSystemAlertList().toArray()[0]).getUserName());

        //controller.addFanSubscriptionToPersonalPage(playerPage, fanMail.getUserName());
        controller.addFanSubscriptionToPersonalPage(playerPage, fanNotMail.getUserName());
        //assertEquals(fanMail.getUserName(), ((Fan) playerPage.getAlert().getMailAlertList().toArray()[0]).getUserName());
        assertEquals(fanNotMail.getUserName(), ((Fan) playerPage.getAlert().getInSystemAlertList().toArray()[0]).getUserName());
    }

    // T3.3
    @Test
    void addFanSubscriptionToGame() {
        // TODO: complete mail fan test with DB
        //controller.addFanSubscriptionToGame(game, fanMail.getUserName());
        controller.addFanSubscriptionToGame(game, fanNotMail.getUserName());
        //assertEquals(fanMail.getUserName(), ((Fan) game.getAlertFans().getMailAlertList().toArray()[0]).getUserName());
        assertEquals(fanNotMail.getUserName(), ((Fan) game.getAlertFans().getInSystemAlertList().toArray()[0]).getUserName());
    }

    // T3.4
    @Test
    void sendComplaintToSysAdmin() {
        // TODO: complete mail sysAdmin test with DB
        controller.sendComplaintToSysAdmin(fanMail.getUserName(), sysAdmins, complaintToSysAdmin);
        assertEquals("Complaint text", sysAdmins.get(1).getAlertsMessages().get(0).getMessage());
    }

    // T3.5
    @Test
    void getFanHistory() {
        // TODO: complete complex tests with DB
        assertArrayEquals(new String[]{"A", "B", "C", "D"}, controller.getFanHistory(fanMail.getUserName()));
    }

    //T3.6A
    @Test
    void getFanProfileDetails() {
        // TODO: complete complex tests with DB
        assertEquals("User Name: FanNameMail\n" + "Password: 1234\n" + "Name: null\n" + "Mail: null", controller.getFanProfileDetails(fanMail.getUserName()));
    }

    //T3.6B
    @Test
    void setFanProfileDetails() {
        // TODO: complete complex tests with DB
        controller.setFanProfileDetails("User name test profile details", "TestPassword 321", "Test name", "Test mail");
        assertEquals("User Name: FanNameMail\n" + "Password: 1234\n" + "Name: null\n" + "Mail: null", controller.getFanProfileDetails(fanMail.getUserName()));
    }


    // ========================= Referee Tests ========================
    // ====================================================================

    // T10.1A
    @Test
    void getRefereeDetails() {
        // TODO: complete complex tests with DB
        assertEquals("User Name: testRefereeMail\n" + "Password: 1234\n" + "Name: null\n" + "Mail: null\n" + "Qualification: 0\n" + "Type: null", controller.getRefereeDetails(refereeMail.getUserName()));
    }

    // T10.1B
    @Test
    void setRefereeProfileDetails() {
        // TODO: complete complex tests with DB
        controller.setRefereeProfileDetails(refereeMail.getUserName(), "TestPassword", "TestName", "TestMail", 1, RefereeType.MAIN);
        assertEquals("User Name: testRefereeMail\n" + "Password: 1234\n" + "Name: null\n" + "Mail: null\n" + "Qualification: 0\n" + "Type: null", controller.getRefereeDetails(refereeMail.getUserName()));
    }

    // T10.2
    @Test
    void getRefereeGames() {
        // TODO: complete complex tests with DB
        ArrayList<Game> games = controller.getRefereeGames(refereeMail.getUserName());
        for(Game game : games){
            ArrayList<Referee> referees = game.getReferees();
            ArrayList<String> userNamesReferee = new ArrayList<>();
            for(Referee referee : referees){
                userNamesReferee.add(referee.getUserName());
            }
            assertTrue(userNamesReferee.contains(refereeMail.getUserName()));
            assertFalse(userNamesReferee.contains(refereeNotMail.getUserName()));
        }
    }

    // T10.3
    @Test
    void addGameEventToGame() throws Exception {
        // TODO: with DB get true games of referee and start the tests
//        game.addRefereeToGame(refereeMail);
//
//        try {
//            controller.addGameEventToGame(refereeNotMail.getUserName(), game, gameEvent);
//        }
//        catch (Exception e){
//            assertEquals("java.lang.Exception: This referee doesn't judge in this game", e.toString());
//        }
//
//        assertEquals(0, game.getGameEvents().size());
        controller.addGameEventToGame(refereeMail.getUserName(), game, gameEvent);
        assertEquals("Test description 1", game.getGameEvents().get(0).getDescription());

        controller.addGameEventToGame(refereeMail.getUserName(), game, gameEventBeforeGame);
    }

    @Test
    void changeGameEvent() throws Exception {
        // TODO: with DB get true games of referee and start the tests
//        game.addRefereeToGame(refereeMail);
//
//        try {
//            controller.addGameEventToGame(refereeNotMail.getUserName(), game, gameEvent);
//        }
//        catch (Exception e){
//            assertEquals("java.lang.Exception: This referee doesn't judge in this game", e.toString());
//        }
//
//        assertEquals(0, game.getGameEvents().size());

        game.setGameDate(LocalDateTime.now().withNano(0).withSecond(0));
        gameEvent.setGameDate(LocalDateTime.now().withNano(0).withSecond(0).plusMinutes(30));
        game.addEvent(gameEvent);
        game.addRefereeToGame(refereeMail);
        // new GameEvent("2019-11-11 12:30", 30, GameAlert.INJURY, "Test description 1");
        controller.changeGameEvent(refereeMail.getUserName(), game, gameEvent, LocalDateTime.now().plusMinutes(45).withNano(0).withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), -1, null, null);
        assertEquals(LocalDateTime.now().plusMinutes(45).withNano(0).withSecond(0).toString(), game.getGameEvents().get(0).getDateTime().toString());
        assertEquals(30, game.getGameEvents().get(0).getGameMinutes());
        assertEquals(GameAlert.INJURY.toString(), game.getGameEvents().get(0).getEventName().toString());
        assertEquals("Test description 1", game.getGameEvents().get(0).getDescription());

        controller.changeGameEvent(refereeMail.getUserName(), game, gameEvent, null, 45, null, null);
        assertEquals(LocalDateTime.now().plusMinutes(45).withNano(0).withSecond(0).toString(), game.getGameEvents().get(0).getDateTime().toString());
        assertEquals(45, game.getGameEvents().get(0).getGameMinutes());

        controller.changeGameEvent(refereeMail.getUserName(), game, gameEvent, null, -1, GameAlert.GOAL, null);
        assertEquals(GameAlert.GOAL.toString(), game.getGameEvents().get(0).getEventName().toString());

        controller.changeGameEvent(refereeMail.getUserName(), game, gameEvent, null, -1, null, "Changed");
        assertEquals("Changed", game.getGameEvents().get(0).getDescription());
    }

    // =================== Association Agent Tests ====================
    // ====================================================================

    @Test
    void setLeague() {
    }

    @Test
    void updateSeasonForLeague() {
    }

    @Test
    void addNewReferee() {
    }

    @Test
    void removeReferee() {
    }

    @Test
    void addRefereeToLeaguePerSeason() {
    }

    @Test
    void setRankingMethod() {
    }

    @Test
    void setSchedulingMethod() {
    }

    @Test
    void scheduleGamesInLeagues() {
    }

    @Test
    void setRulesForBudgetControl() {
    }

    @Test
    void setTeamBudget() {
    }

    @Test
    void addField() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void addCoach() {
    }

    @Test
    void addManager() {
    }

    @Test
    void removeField() {
    }

    @Test
    void removePlayer() {
    }

    @Test
    void removeCoach() {
    }

    @Test
    void removeManager() {
    }

    @Test
    void testUpdatePlayerDetails() {
    }

    @Test
    void testUpdateCoachDetails() {
    }

    @Test
    void updateManagerDetails() {
    }

    @Test
    void setPermissionsToManager() {
    }

    @Test
    void closeTeam() {
    }

    @Test
    void removeUserFromSystem() {
    }

    @Test
    void showComplain() {
    }

    @Test
    void commentToComplaint() {
    }

    @Test
    void showLogDocument() {
    }

    @Test
    void startModelRecommendationSystem() {
    }

    @Test
    void getTeamByName() {
    }

    @Test
    void getUserRoles() {
    }

    @Test
    void main() {
    }

    @Test
    void showLoginPanel() {
    }

    @Test
    void runSystem() {
    }

    @Test
    void init() {
    }
}