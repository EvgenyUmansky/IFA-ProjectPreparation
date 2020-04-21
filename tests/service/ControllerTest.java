package service;

import domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller = new Controller();
    // ========================= Fan Tests ============================
    Fan fanMail;
    Fan fanNotMail;

    // T3.2
    TeamPage teamPage;
    CoachPage coachPage;
    PlayerPage playerPage;

    // T3.3
    Game game;

    // T3.4
    ArrayList<SystemAdministrator> sysAdmins;
    AlertNotification complaintToSysAdmin;

    // =================== Team Owner functions ====================
    Team team;
    User teamOwnerUser;

    // T6.1A1, T6.1B1
    TeamPlayer player;
    // T6.1A2, T6.1B2
    TeamCoach coach;
    // T6.1A3, T6.1B3
    Field field;

    // T6.2, T6.3
    TeamOwner teamOwnerMain;
    TeamOwner teamOwnerSub;

    // T6.4, T6.5
    TeamManager teamManager;

    // ========================= Referee Tests ========================
    Referee refereeMail;
    Referee refereeNotMail;

    // T10.3
    GameEvent gameEvent;
    GameEvent gameEventBeforeGame;




    League leaguePerSeason;

    @BeforeEach
    public void insert() {
        controller = new Controller();
        leaguePerSeason = new League(1998, new OneGameSchedulingMethod(), new RankingMethod(), "bet");

        // ========================= Fan Tests ========================
        fanMail = new Fan("FanNameMail", "euguman@gmail.com");
        fanMail.setMail(true);
        fanNotMail = new Fan("FanNameNotMail", "");
        fanNotMail.setMail(false);

        // T3.2
        teamPage = new TeamPage(new Team("unReal Hadera", new Field("Dinamo", 500), new TeamOwner("Owner", "")));
        coachPage = new CoachPage(new TeamCoach("CoachName", ""), "My best page coach");
        playerPage = new PlayerPage(new TeamPlayer("PlayerName", ""), "My best page player");

        // T3.3, T10.3, T10.4
        game = new Game(new League("Test league"), new Team("Test guest team", new Field("Test field", 500), new TeamOwner("Test name", "")), new Team("Test team", new Field("Test field", 500), new TeamOwner("Test name", "")), new Field("Test field", 500), "2019-11-11 12:00", new ArrayList<>());

        // T3.4
        sysAdmins = new ArrayList<>();
        sysAdmins.add(new SystemAdministrator("Test sysAdmin mail", "euguman@gmail.com"));
        sysAdmins.get(0).setMail(true);
        sysAdmins.add(new SystemAdministrator("Test sysAdmin notMail", ""));
        sysAdmins.get(1).setMail(false);
        complaintToSysAdmin = new AlertNotification("Complaint title", "Complaint text");

        // =================== Team Owner functions ====================
        teamOwnerUser = new User("TeamOwnerUser", "123", "Evgeny", "");
        teamOwnerUser.addRoleToUser(Role.TEAM_OWNER);
        team = new Team("Test TO team", null, (TeamOwner)teamOwnerUser.getRoles().get(Role.TEAM_OWNER));

        // T6.1A1, T6.1B1
        player = new TeamPlayer("Test TO player", "");
        // T6.1A2, T6.1B2
        coach = new TeamCoach("Test TO coach", "");
        // T6.1A3, T6.1B3
        field = new Field("Test TO field", 500);

        // T6.2, T6.3
        teamOwnerMain = new TeamOwner("Main TO", "");
        teamOwnerSub = new TeamOwner("Sub TO", "");

        // T6.4, T6.5
        teamManager = new TeamManager("Test TO manager", "");

        // ========================= Referee Tests ========================
        refereeMail = new Referee("testRefereeMail", "euguman@gmail.com");
        refereeMail.setQualification(4);
        refereeMail.setRefereeType(RefereeType.MAIN);
        refereeNotMail = new Referee("testRefereeNotMail", "");
        refereeNotMail.setQualification(5);
        refereeNotMail.setRefereeType(RefereeType.ASSISTANT);

        // T10.3, T10.4
        gameEvent = new GameEvent("2019-11-11 12:30", 30, GameAlert.INJURY, "Test description 1");
        gameEventBeforeGame = new GameEvent("2019-11-11 11:30", 30, GameAlert.INJURY, "Test description 2");
    }

    @AfterEach
    public void delete(){
        controller = null;
        leaguePerSeason = null;
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

        // =================== Team Owner functions ====================
        teamOwnerUser = null;
        team = null;

        // T6.1A1, T6.1B1
        player = null;
        // T6.1A2, T6.1B2
        coach = null;
        // T6.1A3, T6.1B3
        field = null;

        // T6.2, T6.3
        teamOwnerMain = null;
        teamOwnerSub = null;

        // T6.4, T6.5
        teamManager = null;

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
        ArrayList<PersonalPage> personalPages = controller.getPagesByUsername("playerPage");
        for(int i = 0; i < personalPages.size(); i++){
            assertEquals("playerPage", personalPages.get(i).getName());
        }
    }


    // =================== Personal Pages Tests =================
    // ==============================================================

    //T4.2, T5.2
    @Test
    void updateInfo() {
        PersonalPage myPage = controller.updateInfo(playerPage, "newInfo");
        assertEquals("My best page player", myPage.getName());

    }

    // =================== Team Player functions ====================
    // ==============================================================

    //T4.1
    @Test
    void updatePlayerDetails() {
        Date date = new Date(2014, 02, 11);
        PlayerPage myPage = controller.updatePlayerDetails("userName", "playerName", date, "Attacker", "10");
        assertEquals("Attacker", myPage.getPosition());
        assertEquals("10", myPage.getSquadNumber());
        assertEquals(date, myPage.getBirthDate());

    }

    // ======================= Coach Tests ============================
    // ====================================================================

    //T5.1
    @Test
    void updateCoachDetails() {
        //TODO: open when working with DB
//        CoachPage myPage = controller.updateCoachDetails("userName", "CoachName", "5", "main");
//        assertEquals("5", myPage.getQualification());
//        assertEquals("main", myPage.getRole());
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

    // T10.4
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

    // T 9.1
    @Test
    void setLeague() {
        assertEquals("bet", controller.setLeague("bet").getLeagueName());

    }


    // T 9.2
    @Test
    void updateSeasonForLeague() {
        League upLeague = controller.updateSeasonForLeague("bet", 1998);
        assertEquals(1998, upLeague.getSeason());
        assertEquals("bet", upLeague.getLeagueName());
    }


    // T 9.3
    @Test
    void createReferee()
    {
        try {
            controller.createReferee("newRef", "abcd", "ref", "abc@gmail.com");
        } catch (Exception e) {

        }
        assertEquals(true, User.getUserByID("newRef").getRoles().containsKey(Role.REFEREE));
    }


    // T 9.3
    @Test
    void removeReferee() {
        //TODO:

        boolean thrown = false;

        try {
            controller.removeReferee("CoachName");
            thrown = true;
        } catch (IndexOutOfBoundsException e) {

        }

        assertTrue(thrown);
    }


    // T 9.4
    @Test
    void addRefereeToLeaguePerSeason() {

        controller.addRefereeToLeaguePerSeason(leaguePerSeason, "testRefereeMail");

        controller.addRefereeToLeaguePerSeason(leaguePerSeason, "testRefereeNotMail");

        assertEquals(2 , leaguePerSeason.getReferees().size());
    }


    // T 9.5
    @Test
    void setRankingMethod() {

        controller.setRankingMethod(5,4,3,leaguePerSeason);
        assertEquals(3, leaguePerSeason.getRankingMethod().getWinPoints());
        controller.setRankingMethod(5,3,4,leaguePerSeason);
        assertEquals(5, leaguePerSeason.getRankingMethod().getWinPoints());
        assertEquals(4, leaguePerSeason.getRankingMethod().getDrawPoints());
        assertEquals(3, leaguePerSeason.getRankingMethod().getLoosPoints());

    }


    // T 9.6
    @Test
    void setSchedulingMethod() {
        SchedulingMethod twoSchedule = new TwoGameSchedulingMethod();
        controller.setSchedulingMethod(leaguePerSeason,twoSchedule);
        boolean ans = false;
        if(leaguePerSeason.getSchedulingMethod() instanceof TwoGameSchedulingMethod){
            ans = true;
        }
        assertEquals(true, ans);

    }


    // T 9.7
    @Test
    void scheduleGamesInLeagues() {
//        SchedulingMethod twoSchedule = new TwoGameSchedulingMethod();
        Team[] leagueTeams = new Team[3];
        leagueTeams[0] = new Team("FCB", new Field("Barca-Field", 5000), new TeamOwner("abc", "aa"));
        leagueTeams[1] = new Team("Real", new Field("Real-Field", 5000), new TeamOwner("def", "bb"));
        leagueTeams[2] = new Team("Kissra", new Field("Kissra-Field", 5000), new TeamOwner("ghk", "cc"));
        leaguePerSeason.initTeams(new HashSet<>(Arrays.asList(leagueTeams)));
        controller.scheduleGamesInLeagues(leaguePerSeason);

        assertEquals(3, leaguePerSeason.getGames().size());
    }


    // T 9.8
    @Test
    void setRulesForBudgetControl() {
    }


    // T 9.9
    @Test
    void setTeamBudget() {
    }


    // =================== Team Owner Tests ========================
    // =============================================================

    // T6.1A

    // T6.1A1
    @Test
    void addPlayer() throws Exception {
        // TODO: check null player when DB
//        try {
//            controller.addPlayer(team, null);
//        } catch (Exception e) {
//            assertEquals("java.lang.Exception: This user is not a player", e.toString());
//        }

        assertEquals(0, team.getPlayers().size());
        controller.addPlayer(team, player.getUserName());
        assertEquals(player.getUserName(), team.getPlayers().get(player.getUserName()).getUserName());

        team.closeTeam(teamOwnerUser);
        try {
            controller.addPlayer(team, teamOwnerUser.getUserName());
        }
        catch (Exception e){
            assertEquals("java.lang.Exception: This team is currently closed", e.toString());
        }
    }

    @Test
    void addCoach() {
        team.closeTeam(teamOwnerUser);
        try {
            controller.addPlayer(team, teamOwnerUser.getUserName());
        }
        catch (Exception e){
            assertEquals("java.lang.Exception: This team is currently closed", e.toString());
        }
    }

    @Test
    void addField() {
        team.closeTeam(teamOwnerUser);
        try {
            controller.addPlayer(team, teamOwnerUser.getUserName());
        }
        catch (Exception e){
            assertEquals("java.lang.Exception: This team is currently closed", e.toString());
        }
    }

    @Test
    void addManager() {
        team.closeTeam(teamOwnerUser);
        try {
            controller.addPlayer(team, teamOwnerUser.getUserName());
        }
        catch (Exception e){
            assertEquals("java.lang.Exception: This team is currently closed", e.toString());
        }
    }

    @Test
    void removeField() {
        team.closeTeam(teamOwnerUser);
        try {
            controller.addPlayer(team, teamOwnerUser.getUserName());
        }
        catch (Exception e){
            assertEquals("java.lang.Exception: This team is currently closed", e.toString());
        }
    }

    @Test
    void removePlayer() {
        team.closeTeam(teamOwnerUser);
        try {
            controller.addPlayer(team, teamOwnerUser.getUserName());
        }
        catch (Exception e){
            assertEquals("java.lang.Exception: This team is currently closed", e.toString());
        }
    }

    @Test
    void removeCoach() {
        team.closeTeam(teamOwnerUser);
        try {
            controller.addPlayer(team, teamOwnerUser.getUserName());
        }
        catch (Exception e){
            assertEquals("java.lang.Exception: This team is currently closed", e.toString());
        }
    }

    @Test
    void removeManager() {
        team.closeTeam(teamOwnerUser);
        try {
            controller.addPlayer(team, teamOwnerUser.getUserName());
        }
        catch (Exception e){
            assertEquals("java.lang.Exception: This team is currently closed", e.toString());
        }
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