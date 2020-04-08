package service;

import domain.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Controller {

    LinkedList<SystemEvent> systemEvents;
    HashSet<League> leagues;
    HashMap<String,User> users;



    // Constructor
    public Controller() {
        users = new HashMap<String, User>();
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



    // the idea is that in the UI part where will be two text boxes - one for username and one for password.
    // when the user presses 'register', the functions isValidUserName and isValidPassword are activated and if they both returned true
    // then register function is activated
    public boolean register(String userName, String password, String name, String userType){
        if(users.containsKey(userName)){
            return false;
        }

        Subscriber newUser = null;
        //TODO: change the instances of each user according to its constructor
        switch (userType){
            case "Fan":
                newUser = new Fan();
                break;

            case "System Administrator":
                newUser = new SystemAdministrator(userName,password,name);
                break;

            case "Referee":
                newUser = new Referee(userName,password,name);
                break;

            case "Association Agent":
                newUser = new AssociationAgent(userName,password,name);
                break;

            case "Team Player":
                newUser = new TeamPlayer(userName,password,name);
                break;

            case "Team Coach":
                newUser = new TeamCoach(userName,password,name);
                break;

            case "Team Admin":
                newUser = new TeamAdmin(userName,password,name);
                break;

            case "Team Owner":
                newUser = new TeamOwner(userName,password,name);
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



}
