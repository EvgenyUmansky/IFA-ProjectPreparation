package service;

import domain.*;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Controller {

    //TODO think about best solution
    private Alert sysAdminsAlert;

    private LinkedList<SystemEvent> systemEvents;
    private HashSet<League> leagues;
    private HashMap<String, User> users;


    /////////// Constructor ///////////
    public Controller() {
        users = new HashMap<>();
        sysAdminsAlert = new Alert();
        systemEvents = new LinkedList<>();
    }


    //UC 1.1.1
    public boolean connectToExternalSystems() {

        return false;
    }


    public void writeSystemEventsInLogger(SystemEvent event) {

    }

    /**
     * @param userName
     * @param password
     * @return
     */
    public boolean login(String userName, String password) {
        if (!users.containsKey(userName)) {
            return false;
        }

        Subscriber user = (Subscriber) (users.get(userName));
        if (!user.getPassword().equals(password)) {
            return false;
        }

        user.connect();
        return true;
    }

    public boolean logout(String userName, boolean isOut) {
        if (!isOut) {
            return false;
        }

        Subscriber user = (Subscriber) (users.get(userName));
        user.disconnect();
        return true;
    }

    // the idea is that in the UI part where will be two text boxes - one for username and one for password.
    // when the user presses 'register', the functions isValidUserName and isValidPassword are activated and if they both returned true
    // then register function is activated
    public boolean register(String userName, String password, String name, String userType, String mail, int refereeQualification, RefereeType refereeType) {
        if (users.containsKey(userName)) {
            return false;
        }

        Subscriber newUser = null;
        //TODO: change the instances of each user according to its constructor
        switch (userType) {
            case "Fan":
                newUser = new Fan(userName, password, name, mail);
                break;

            case "System Administrator":
                newUser = new SystemAdministrator(userName, password, name, mail);

                //TODO think about best solution
                sysAdminsAlert.addToSystemSet(newUser);
                if (!mail.isEmpty()) {
                    sysAdminsAlert.addToMailSet(newUser);
                }

                break;

            case "Referee":
                //TODO think about refereeQualification and refereeType
                newUser = new Referee(userName, password, name, mail, refereeQualification, refereeType);
                break;

            case "Association Agent":
                newUser = new AssociationAgent(userName, password, name, mail);
                break;

            case "Team Player":
                newUser = new TeamPlayer(userName, password, name, mail);
                break;

            case "Team Coach":
                newUser = new TeamCoach(userName, password, name, mail);
                break;

            case "Team Admin":
                newUser = new TeamAdmin(userName, password, name, mail);
                break;

            case "Team Owner":
                newUser = new TeamOwner(userName, password, name, mail);
                break;

            default:
                return false;
        }

        users.put(userName, newUser);
        //newUser.connect();
        return true;
    }


    public boolean isValidUserName(String userName) {
        return Subscriber.isValidUserName(userName);
    }

    public boolean isValidPassword(String password) {
        return Subscriber.isValidPassword(password);
    }


    public boolean createSeason(String rankingPolicy) {


        return false;
    }


    public boolean enterAsGuest() {

        return false;
    }

    // UC 3.6
    private String getProfileDetails(Subscriber user) {

        return "User Name: " + user.getUserName() + "\n" +
                "Password: " + user.getPassword() + "\n" +
                "Name: " + user.getName() + "\n" +
                "Mail: " + user.getMail();
    }

    // UC 3.6, UC 10.1
    private boolean setProfileDetails(Subscriber user, String newUserName, String newPassword, String newName, String newMail, boolean isEmptyMail) {

        if (!newUserName.isEmpty()) {
            user.setUserName(newUserName);
        } else {
            System.out.println("The new user name is empty. The user name is not changed");
        }

        if (!newPassword.isEmpty()) {
            user.setPassword(newPassword);
        } else {
            System.out.println("The new password is empty. The password is not changed");
        }

        // set name
        if (!newName.isEmpty()) {
            user.setName(newName);
        } else {
            System.out.println("The new name is empty. The name is not changed");
        }
        // set mail
        if (isEmptyMail) {
            user.setMail("");
        } else if (!newMail.isEmpty()) {
            user.setMail(newMail);
        } else {
            System.out.println("The mail is not changed");
        }

        return true;
    }


/////////// Use Case 3 - Fan ///////////

    // UC 3.2 - add fan to subscription list of the personal page
    public boolean addFanSubscriptionToPersonalPage(PersonalPage page, String userName, boolean isMail) {
        Subscriber fan = (Subscriber) (users.get(userName));
        if (!(fan instanceof Fan)) {
            System.out.println("Not fan instance");
            return false;
        }

        page.addSubscriber(fan, isMail);
        return true;
    }

    // UC 3.3 - add fan to subscription list of the game
    public boolean addFanSubscriptionToGame(Game game, String userName, boolean isMail) {
        Subscriber fan = (Subscriber) (users.get(userName));
        if (!(fan instanceof Fan)) {
            System.out.println("Not fan instance");
            return false;
        }

        game.addSubscriber(fan, isMail);
        return true;
    }

    // UC 3.4 - send complaint (by fan) to System Administrator
    public boolean sendAlertToSysAdmin(String userName, String message) {
        Subscriber fan = (Subscriber) (users.get(userName));
        if (!(fan instanceof Fan)) {
            System.out.println("Not fan instance");
            return false;
        }

        sysAdminsAlert.sendAlert(message);

        return true;
    }

    // 3.5 -
    //TODO See all history. We need UI for this? Evgeny

    // UC 3.6 Envelope function for getProfileDetails - get and set fan info (fields)
    public String getFanProfileDetails(String userName) {
        Subscriber fan = (Subscriber) (users.get(userName));
        if (!(fan instanceof Fan)) {
            System.out.println("Not fan instance");
            return "";
        }

        return getProfileDetails(fan);
    }

    // Envelope function for setProfileDetails
    public boolean setFanProfileDetails(String userName, String newUserName, String newPassword, String newName, String newMail, boolean isEmptyMail) {
        Subscriber fan = (Subscriber) (users.get(userName));
        if (!(fan instanceof Fan)) {
            System.out.println("Not fan instance");
            return false;
        }

        return setProfileDetails(fan, newUserName, newPassword, newName, newMail, isEmptyMail);
    }

/////////// Use Case 10 - Referee ///////////

    // UC 10.1, envelope function for getProfileDetails - get and set referee info (fields)
    public String getRefereeProfileDetails(String userName) {
        Subscriber referee = (Subscriber) (users.get(userName));
        if (!(referee instanceof Referee)) {
            System.out.println("Not referee instance");
            return "";
        }

        return getProfileDetails(referee) + "\nQualification: " + ((Referee) referee).getQualification();
    }

    // Envelope function for setProfileDetails
    public boolean setRefereeProfileDetails(String userName, int qualification, RefereeType refereeType, String newUserName, String newPassword, String newName, String newMail, boolean isEmptyMail) {
        Subscriber referee = (Subscriber) (users.get(userName));
        if (!(referee instanceof Referee)) {
            System.out.println("Not referee instance");
            return false;
        }

        if (qualification < 1 || qualification > 5) {
            System.out.println("Qualification must be between 1 to 5, the qualification is not changed");
        } else {
            ((Referee) referee).setQualification(qualification);
        }

        if (refereeType != null) {
            ((Referee) referee).setRefereeType(refereeType);
        }

        return setProfileDetails(referee, newUserName, newPassword, newName, newMail, isEmptyMail);
    }

    // UC 10.2 - get all games the referee judge
    public Set<Game> getRefereeGames(String userName) {
        Subscriber referee = (Subscriber) (users.get(userName));
        if (!(referee instanceof Referee)) {
            System.out.println("Not referee instance");
            return null;
        }

        return ((Referee) referee).getGames();
    }

    // UC 10.3 - create new game event and add it to list of game events of the game
    public boolean updateGameEvent(String userName, Game game, String dateTime, int gameMinutes, GameAlert eventName, String subscription) {
        Subscriber user = (Subscriber) (users.get(userName));
        if (!(user instanceof Referee)) {
            System.out.println("Not referee instance");
            return false;
        }

        Referee referee = (Referee) user;
        if (!referee.getGames().contains(game)) {
            System.out.println("The referee does not judge this game");
            return false;
        }

        // new GameEvent(String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription)
        game.addGameEvent(new GameEvent(dateTime, gameMinutes, eventName, subscription));
        return true;
    }

    // UC 10.4 - update/change game events by main referee
    public boolean changeGameEvent(String userName, Game game, int gameEventId, String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription) {
        Subscriber user = (Subscriber) (users.get(userName));
        if (!(user instanceof Referee)) {
            System.out.println("Not referee instance");
            return false;
        }

        Referee referee = (Referee) user;
        if (!referee.getRefereeType().equals(RefereeType.MAIN)) {
            System.out.println("Not MAIN referee");
            return false;
        }

        long diffInHours = ChronoUnit.HOURS.between(game.getGameDate(), LocalDateTime.now());
        if (diffInHours > 5) {
            System.out.println("Not allowed to change the game events: out of time");
            return false;
        }

        GameEvent gameEvent = game.getGameEvents().get(gameEventId);

        if (!dateTimeStr.isEmpty()) {
            gameEvent.setGameDate(dateTimeStr);
        }

        if (gameMinutes > -1) {
            gameEvent.setGameMinutes(gameMinutes);
        }

        if (eventName != null) {
            gameEvent.setEventName(eventName);
        }

        if (!subscription.isEmpty()) {
            gameEvent.setSubscription(subscription);
        }

        return true;
    }

    // ----------------------------------------------- Team Owner Use Cases (6) ----------------------------------------------- //

    public boolean addPropertyToTeam(TeamOwner owner, Object property) {
        if (!(property instanceof TeamMember || property instanceof Field)) {
            return false;
        }

        owner.addProperty(property);
        return true;
    }


    public boolean removePropertyFromTeam(TeamOwner owner, Object property) {
        if (!(property instanceof TeamMember || property instanceof Field)) {
            return false;
        }

        owner.removeProperty(property);
        return true;
    }


    public boolean updatePlayerDetails(TeamOwner owner, String userName, String name, Date birthDate, String role) {
        owner.updatePlayerDetails(userName, name, birthDate, role);
        return true;
    }

    public boolean updateCoachDetails(TeamOwner owner, String userName, String name, String validation, String role) {
        owner.updateCoachDetails(userName, name, validation, role);
        return true;
    }

    // AssociationAgent UC 9.1

    public boolean setLeagueByAssociationAgent(Subscriber assAgent, String leaguename, int leaguequalification) {
        if (assAgent instanceof AssociationAgent == true) {
            //better to check in ui level
            if (leaguequalification >= 1 && leaguequalification <= 5) {
                leagues.add(new League(leaguename, leaguequalification));
                return true;
            } else {
                System.out.println("league qualification not in legal range");
            }
        }
        return false;
    }

    //UC9.2

    public boolean setSeasonToLeagueByAssociationAgent(Subscriber assAgent, int year, League league, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
        if (leagues.contains(league) && assAgent instanceof AssociationAgent) {
            league.addLeaguePerSeason(new LeaguePerSeason(league, new Season(year), schedulingMethod, rankingMethod));
            return true;
        }
        return false;
    }

    //UC 9.3A
    // TODO: 10/04/2020
    public boolean addReferee(Subscriber assAgent, String userName, String password, String name, String mail, int qualification, RefereeType refereeType) {
        if (assAgent instanceof AssociationAgent) {
            User referee = new Referee(userName, password, name, mail, qualification, refereeType);
            users.put(userName, referee);
            return true;
        }
        return false;
    }

    //UC 9.3B
    // TODO: 10/04/2020
    public boolean removeReferee(Subscriber assAgent, String userName) {
        if (assAgent instanceof AssociationAgent) {
            users.remove(userName);
            return true;
        }
        return false;
    }

    //UC 9.4
    // TODO: 10/04/2020
    public boolean addRefereeToLeagueBySeason(Subscriber assAgent, LeaguePerSeason leaguePerSeason, String userNameReferee) {
        if (assAgent instanceof AssociationAgent) {
            if(users.containsKey(userNameReferee) == true){
                User user = users.get(userNameReferee);
                if(user instanceof  Referee){
                    leaguePerSeason.addReferee((Referee) user);
                    return true;
                }
            }
        }

        return false;
    }

    //UC9.5
    public boolean setRankingMethod(Subscriber assAgent, int winP, int loseP, int drawP, LeaguePerSeason leaguePerSeason) {
        if (leaguePerSeason.isBegin() == false && assAgent instanceof AssociationAgent == true) {
            leaguePerSeason.getRankingMethod().setWinPoints(winP);
            leaguePerSeason.getRankingMethod().setLoosPoints(loseP);
            leaguePerSeason.getRankingMethod().setDrawPoints(drawP);
            return true;
        }
        return false;
    }

    //UC9.6
    public boolean setSchedulingMethod(Subscriber assAgent, LeaguePerSeason leaguePerSeason, SchedulingMethod schedulingMethod) {
        if (assAgent instanceof AssociationAgent) {
            leaguePerSeason.setSchedulingMethod(schedulingMethod);
            return true;
        }
        return false;
    }

    //UC9.7
    public boolean startScheduleMethod(SchedulingMethod schedulingMethod, Subscriber assAgent, LeaguePerSeason leaguePerSeason)
        {
            if(assAgent instanceof AssociationAgent){
                Team[] teams = leaguePerSeason.getTeamsInLeaguePerSeason().keySet().toArray(new Team[leaguePerSeason.getTeamsInLeaguePerSeason().size()]);
                schedulingMethod.scheduleGamePolicy(leaguePerSeason, teams);
            return true;
            }
            return false;
        }



}
