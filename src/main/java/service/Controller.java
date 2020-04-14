package service;

import domain.*;

import java.util.Date;
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

        User user =  users.get(userName);
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

        User user = users.get(userName);
        user.disconnect();
        return true;
    }

    // the idea is that in the UI part where will be two text boxes - one for username and one for password.
    // when the user presses 'register', the functions isValidUserName and isValidPassword are activated and if they both returned true
    // then register function is activated
    public boolean register(String userName, String password, String name, String userType, String mail, boolean isMail, int refereeQualification, RefereeType refereeType) {
//        if (users.containsKey(userName)) {
//            return false;
//        }

        User newUser = new User(false,userName,password,name,mail);



        Subscriber newSubscriber = null;
        //TODO: change the instances of each user according to its constructor
        switch (userType) {
            case "Fan":
                newSubscriber = new Fan(userName,mail,isMail);
                newUser.addRoleToUser("Fan",newSubscriber);
                break;

            case "System Administrator":
                newSubscriber = new SystemAdministrator(userName,mail,isMail);
                newUser.addRoleToUser("System Administrator",newSubscriber);

                //TODO think about best solution
                sysAdminsAlert.addToSystemSet(newSubscriber);
                if (!mail.isEmpty()) {
                    sysAdminsAlert.addToMailSet(newSubscriber);
                }

                break;

            case "Referee":
                //TODO think about refereeQualification and refereeType
                newSubscriber = new Referee(userName, mail, isMail, refereeQualification, refereeType);
                newUser.addRoleToUser("Referee",newSubscriber);
                break;

            case "Association Agent":
                newSubscriber = new AssociationAgent(userName,mail,isMail);
                newUser.addRoleToUser("Association Agent",newSubscriber);
                break;

            case "Team Player":
                //need to add position and birthdate
                newSubscriber = new TeamPlayer(userName,mail,isMail);
                newUser.addRoleToUser("Team Player",newSubscriber);
                break;

            case "Team Coach":
                //need to add role and validation
                newSubscriber = new TeamCoach(userName,mail, isMail);
                newUser.addRoleToUser("Team Coach",newSubscriber);
                break;

            case "Team Manager":
                newSubscriber = new TeamManager(userName, mail,isMail);
                newUser.addRoleToUser("Team Manager",newSubscriber);
                break;

            case "Team Owner":
                //need to update team
                newSubscriber = new TeamOwner(userName, mail,isMail);
                newUser.addRoleToUser("Team Owner",newSubscriber);
                break;

            default:
                return false;

        }

        users.put(userName, newUser);
        //newUser.connect();
        return true;
    }


    public boolean isValidUserName(String userName) {
        return User.isValidUserName(userName);
    }

    public boolean isValidPassword(String password) {
        return User.isValidPassword(password);
    }


    public boolean createSeason(String rankingPolicy) {


        return false;
    }


    public boolean enterAsGuest() {

        return false;
    }

    // UC 3.6
    private String getProfileDetails(User user, Subscriber subscriber) {

        return "User Name: " + user.getUserName() + "\n" +
                "Password: " + user.getPassword() + "\n" +
                "Name: " + user.getName() + "\n" +
                "Mail: " + subscriber.getMail();
    }

    // UC 3.6, UC 10.1
    private boolean setProfileDetails(User user,Subscriber subscriber, String newUserName, String newPassword, String newName, String newMail, boolean isEmptyMail) {

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
            subscriber.setMail("");
        } else if (!newMail.isEmpty()) {
            subscriber.setMail(newMail);
        } else {
            System.out.println("The mail is not changed");
        }

        return true;
    }


/////////// Use Case 3 - Fan ///////////

    // UC 3.2 - add fan to subscription list of the personal page
    public boolean addFanSubscriptionToPersonalPage(PersonalPage page, String userName, boolean isMail) {
        User fanUser = users.get(userName);
//        if (!(fan instanceof Fan)) {
        if(!fanUser.getRoles().containsKey("Fan")){
            System.out.println("Not fan instance");
            return false;
        }

        page.addSubscriber(fanUser.getRoles().get("Fan"), isMail);
        return true;
    }

    // UC 3.3 - add fan to subscription list of the game
    public boolean addFanSubscriptionToGame(Game game, String userName, boolean isMail) {
        User fanUser = users.get(userName);
//        if (!(fan instanceof Fan)) {
        if(!fanUser.getRoles().containsKey("Fan")){
            System.out.println("Not fan instance");
            return false;
        }

        game.addFanToAlerts(fanUser.getRoles().get("Fan"));
        return true;
    }

    // UC 3.4 - send complaint (by fan) to System Administrator
    public boolean sendAlertToSysAdmin(String userName, AlertNotification message) {
//        Subscriber fan = (Subscriber) (users.get(userName));
//        if (!(fan instanceof Fan)) {
        User fanUser = users.get(userName);
        if(!fanUser.getRoles().containsKey("Fan")){
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
//        Subscriber fan = (Subscriber) (users.get(userName));
//        if (!(fan instanceof Fan)) {
        User fanUser = users.get(userName);
        if(!fanUser.getRoles().containsKey("Fan")){
        System.out.println("Not fan instance");
            return "";
        }

        return getProfileDetails(fanUser, fanUser.getRoles().get("Fan"));
    }

    // Envelope function for setProfileDetails
    public boolean setFanProfileDetails(String userName, String newUserName, String newPassword, String newName, String newMail, boolean isEmptyMail) {
        User fanUser = users.get(userName);
        if(!fanUser.getRoles().containsKey("Fan")){
            System.out.println("Not fan instance");
            return false;
        }

        return setProfileDetails(fanUser, fanUser.getRoles().get("Fan"), newUserName, newPassword, newName, newMail, isEmptyMail);
    }

/////////// Use Case 10 - Referee ///////////

    // UC 10.1, envelope function for getProfileDetails - get and set referee info (fields)
    public String getRefereeProfileDetails(String userName) {
//        Subscriber referee = (Subscriber) (users.get(userName));
        User refereeUser = users.get(userName);
        if(!refereeUser.getRoles().containsKey("Referee")){
            System.out.println("Not referee instance");
            return "";
        }

        return getProfileDetails(refereeUser, refereeUser.getRoles().get("Referee")) + "\nQualification: " + ((Referee)refereeUser.getRoles().get("Referee")).getQualification();
    }

    // Envelope function for setProfileDetails
    public boolean setRefereeProfileDetails(String userName, int qualification, RefereeType refereeType, String newUserName, String newPassword, String newName, String newMail, boolean isEmptyMail) {
        User refereeUser = users.get(userName);
        if(!refereeUser.getRoles().containsKey("Referee")){
            System.out.println("Not referee instance");
            return false;
        }

        if (qualification < 1 || qualification > 5) {
            System.out.println("Qualification must be between 1 to 5, the qualification is not changed");
        } else {
            ((Referee)refereeUser.getRoles().get("Referee")).setQualification(qualification);
        }

        if (refereeType != null) {
            ((Referee)refereeUser.getRoles().get("Referee")).setRefereeType(refereeType);
        }

        return setProfileDetails(refereeUser,refereeUser.getRoles().get("Referee"), newUserName, newPassword, newName, newMail, isEmptyMail);
    }

    // UC 10.2 - get all games the referee judge
    public Set<Game> getRefereeGames(String userName) {
        User refereeUser = users.get(userName);
        if(!refereeUser.getRoles().containsKey("Referee")){
            System.out.println("Not referee instance");
            return null;
        }

        return ((Referee)refereeUser.getRoles().get("Referee")).getGames();
    }

    // UC 10.3 - create new game event and add it to list of game events of the game
    public boolean updateGameEvent(String userName, Game game, String dateTime, int gameMinutes, GameAlert eventName, String subscription) {
        User refereeUser = users.get(userName);
        if(!refereeUser.getRoles().containsKey("Referee")){
            System.out.println("Not referee instance");
            return false;
        }

        Referee referee = ((Referee)refereeUser.getRoles().get("Referee"));
        if (!referee.getGames().contains(game)) {
            System.out.println("The referee does not judge this game");
            return false;
        }

        // new GameEvent(String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription)
        game.addGameEvent(new GameEvent(dateTime, gameMinutes, eventName, subscription));
        return true;
    }

    // UC 10.4 - update/change game events by main referee
    public boolean changeGameEvent(String userName, Game game, int gameEventId, String dateTimeStr, int gameMinutes, GameAlert eventName, String subscription){
        User refereeUser = users.get(userName);
        if(!refereeUser.getRoles().containsKey("Referee")){
            System.out.println("Not referee instance");
            return false;
        }

        ((Referee)refereeUser.getRoles().get("Referee")).changeGameEvent(game, gameEventId, dateTimeStr, gameMinutes, eventName, subscription);

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


    public boolean updatePlayerDetails(TeamOwner owner,String squadNumber ,String userName, Date birthDate, String position) {
        owner.updatePlayerDetails(userName,squadNumber ,birthDate, position);
        return true;
    }

    public boolean updateCoachDetails(TeamOwner owner, String userName, String validation, String role) {
        owner.updateCoachDetails(userName, validation, role);
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
// TODO: 14/04/2020 need to update teams
    public boolean setSeasonToLeagueByAssociationAgent(Subscriber assAgent, int year, League league, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
        if (leagues.contains(league) && assAgent instanceof AssociationAgent) {
            league.addLeaguePerSeason(new LeaguePerSeason( year, schedulingMethod, rankingMethod));
            return true;
        }
        return false;
    }

    //UC 9.3A
    // TODO: 10/04/2020
    public boolean addReferee(Subscriber assAgent, String userName, String password, String name, String mail, boolean isMail, int qualification, RefereeType refereeType) {
        if (assAgent instanceof AssociationAgent) {
            Subscriber referee = new Referee(userName, mail, isMail, qualification, refereeType);
           users.get(userName).addRoleToUser("Referee",referee);
            return true;
        }
        return false;
    }

    //UC 9.3B
    // TODO: 10/04/2020
    public boolean removeReferee(Subscriber assAgent, String userName) {
        if (assAgent instanceof AssociationAgent) {

            users.get(userName).removeRoleFromUser("Referee");
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
                if(user.getRoles().containsKey("Referee")){
                    leaguePerSeason.addReferee((Referee) user.getRoles().get("Referee"));
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
