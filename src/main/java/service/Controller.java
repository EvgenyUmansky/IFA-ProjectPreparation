package service;

import domain.*;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

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
    public boolean connectToExternalSystems(){

        return false;
    }


    public void writeSystemEventsInLogger(SystemEvent event){

    }

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean login(String userName, String password){
        if(!users.containsKey(userName)){
            return false;
        }

        Subscriber user = (Subscriber)(users.get(userName));
        if(!user.getPassword().equals(password)){
            return false;
        }

        user.connect();
        return true;
    }

    public boolean logout(String userName, boolean isOut){
        if(!isOut){
            return false;
        }

        Subscriber user = (Subscriber)(users.get(userName));
        user.disconnect();
        return true;
    }

    // the idea is that in the UI part where will be two text boxes - one for username and one for password.
    // when the user presses 'register', the functions isValidUserName and isValidPassword are activated and if they both returned true
    // then register function is activated
    public boolean register(String userName, String password, String name, String userType, String mail){
        if(users.containsKey(userName)){
            return false;
        }

        Subscriber newUser = null;
        //TODO: change the instances of each user according to its constructor
        switch (userType){
            case "Fan":
                newUser = new Fan(userName,password,name,mail);
                break;

            case "System Administrator":
                newUser = new SystemAdministrator(userName,password,name,mail);

                //TODO think about best solution
                sysAdminsAlert.addToSystemSet(newUser);
                if(!mail.isEmpty()){
                    sysAdminsAlert.addToMailSet(newUser);
                }

                break;

            case "Referee":
                newUser = new Referee(userName,password,name,mail);
                break;

            case "Association Agent":
                newUser = new AssociationAgent(userName,password,name,mail);
                break;

            case "Team Player":
                newUser = new TeamPlayer(userName,password,name,mail);
                break;

            case "Team Coach":
                newUser = new TeamCoach(userName,password,name,mail);
                break;

            case "Team Admin":
                newUser = new TeamAdmin(userName,password,name,mail);
                break;

            case "Team Owner":
                newUser = new TeamOwner(userName,password,name,mail);
                break;

            default:
                return false;
        }

        users.put(userName,newUser);
        //newUser.connect();
        return true;
    }


    public boolean isValidUserName(String userName){
        return Subscriber.isValidUserName(userName);
    }

    public boolean isValidPassword(String password){
        return Subscriber.isValidPassword(password);
    }



    public boolean createSeason(String rankingPolicy){


        return false;
    }


    public boolean enterAsGuest(){

        return false;
    }


/////////// Use Case 3 ///////////

    // UC 3.2
    public boolean addFanSubscriptionToPersonalPage(PersonalPage page, String userName, boolean isMail){
        Subscriber fan = (Subscriber)(users.get(userName));
        if(!(fan instanceof Fan)){
            return false;
        }

        page.addSubscriber(fan, isMail);
        return true;
    }

    // UC 3.3
    public boolean addFanSubscriptionToGame(Game game, String userName, boolean isMail){
        Subscriber fan = (Subscriber)(users.get(userName));
        if(!(fan instanceof Fan)){
            return false;
        }

        game.addSubscriber(fan, isMail);
        return true;
    }

    // UC 3.4
    public boolean sendAlertToSysAdmin(String userName, String message){
        Subscriber fan = (Subscriber)(users.get(userName));
        if(!(fan instanceof Fan)){
            return false;
        }

        sysAdminsAlert.sendAlert(message);

        return true;
    }

    // 3.5
    //TODO See all history. We need UI for this? Evgeny

    // UC 3.6
    public String getProfileDetails(String userName) {
        Subscriber fan = (Subscriber) (users.get(userName));
        if (!(fan instanceof Fan)) {
            return "";
        }

        return  "User Name: " + fan.getUserName() + "\n" +
                "Password: " + fan.getPassword() + "\n" +
                "Name: " + fan.getName() + "\n" +
                "Mail: " + fan.getMail();
    }

    public boolean setProfileDetails(String userName, String newUserName, String newPassword, String newName, String newMail){
        Subscriber fan = (Subscriber) (users.get(userName));
        if (!(fan instanceof Fan)) {
            return false;
        }

        if(!newUserName.isEmpty()){
            fan.setUserName(newUserName);
        }

        if(!newPassword.isEmpty()){
            fan.setPassword(newPassword);
        }

        if(!newName.isEmpty()){
            fan.setName(newName);
        }

        if(!newMail.isEmpty()){
            fan.setMail(newMail);
        }

        return true;
    }


    // ----------------------------------------------- Team Owner Use Cases (6) ----------------------------------------------- //

    public boolean addPropertyToTeam(TeamOwner owner, Object property){
        if(!(property instanceof TeamMember || property instanceof Field)){
            return false;
        }

        owner.addProperty(property);
        return true;
    }


    public boolean removePropertyFromTeam(TeamOwner owner, Object property){
        if(!(property instanceof TeamMember || property instanceof Field)){
            return false;
        }

        owner.removeProperty(property);
        return true;
    }


    public boolean updatePlayerDetails(TeamOwner owner, String userName, String name, Date birthDate, String role){
        owner.updatePlayerDetails(userName, name, birthDate, role);
        return true;
    }

    public boolean updateCoachDetails(TeamOwner owner, String userName, String name, String validation, String role){
        owner.updateCoachDetails(userName,name,validation,role);
        return true;
    }


}
