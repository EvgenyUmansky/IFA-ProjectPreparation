package domain;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.HashSet;

public class Team {

    private String teamName;
    private Field stadium; //The team's main stadium, where they play official matches
    private HashMap<String,Field> fields; //All the team's fields, including the stadium and training fields
    private HashMap<String, TeamPlayer> players;
    private HashMap<String, TeamCoach> coaches;
    private HashMap<String, TeamManager> managers;
    private HashMap<String, TeamOwner> owners;
    private String teamEmail;
    private Budget budget;
    private PersonalPage teamPage;
    private TeamStatus teamStatus;
    private Alert alert;

    // Constructor
    public Team(String name, Field stadium, TeamOwner owner) {
        this.teamName = name;
        this.stadium = stadium;
        this.fields = new HashMap<>();
        this.owners = new HashMap<>();
        this.managers = new HashMap<>();
        this.coaches = new HashMap<>();
        this.players = new HashMap<>();
        this.alert = new Alert();
        this.alert.addSubscriber(owner);
        this.fields.put(stadium.getFieldName(), stadium);
        this.teamStatus = TeamStatus.Open;
        this.owners.put(owner.getUserName(), owner);
    }


    // Constructor
   /* public Team(String name, Field stadium) {
        this.teamName = name;
        this.stadium = stadium;
        this.fields = new HashSet<>();
        this.owners = new HashMap<>();
        this.managers = new HashMap<>();
        this.coaches = new HashMap<>();
        this.players = new HashMap<>();
        alert = new Alert();
        this.fields.add(stadium);
        this.teamStatus = TeamStatus.Open;
    }*/



    public HashMap<String, TeamPlayer> getPlayers() {
        return players;
    }

    public HashMap<String, TeamCoach> getCoaches() {
        return coaches;
    }

    public HashMap<String, TeamManager> getManagers() {
        return managers;
    }

    public Field getStadium() {
        return stadium;
    }

    public String getTeamEmail() {
        return teamEmail;
    }

    public void setTeamEmail(String teamEmail) {
        this.teamEmail = teamEmail;
    }

    public Field getMyField() {
        return stadium;
    }

    public String getTeamName() {
        return teamName;
    }

    public void addPlayer(TeamPlayer player){
        player.setCurrentTeam(this);
        this.players.put(player.getUserName(), player);
        addSubscriber(player);
    }

    public void addCoach(TeamCoach coach){
        coach.setCurrentTeam(this);
        this.coaches.put(coach.getUserName(), coach);
        addSubscriber(coach);
    }

    public void addField(Field field){
        this.fields.put(field.getFieldName(), field);
    }


    public void removePlayer(TeamPlayer player){
        player.setCurrentTeam(null);
        this.players.remove(player.getUserName());
        removeSubscriber(player);
    }

    public void removeCoach(TeamCoach coach){
        coach.setCurrentTeam(null);
        this.coaches.remove(coach.getUserName());
        removeSubscriber(coach);
    }

    public void removeField(Field field){
        this.fields.remove(field.getFieldName());
    }


    public void closeTeam(User user) {
        // TODO: 18/04/2020 add relevant subscribers to mailSet
        if (teamStatus == TeamStatus.Open || teamStatus == TeamStatus.TempClose) {
            if (user.getRoles().containsKey(Role.SYSTEM_ADMIN)) {
                teamStatus = TeamStatus.PermanentlyClose;
                alert.sendAlert(new AlertNotification("close team permanently", "you team close permanently"));
                return;
            }
        }
        if(teamStatus == TeamStatus.Open) {
            if (user.getRoles().containsKey(Role.TEAM_OWNER)) {
                teamStatus = TeamStatus.TempClose;
                alert.sendAlert(new AlertNotification("close team temporary", "you team close temporary"));
            }
        }
        else{
            throw new Error("This team is already closed");
        }
    }

    public void openTeam() {
        // TODO: 18/04/2020 add relevant subscribers to mailSet
        if(teamStatus == TeamStatus.TempClose){
            teamStatus = TeamStatus.Open;
            alert.sendAlert(new AlertNotification("open your team","your team open again"));
        }
        else{
            throw new Error("This team can't be reopened");
        }
    }


    //UC 6.2
    public void addOwner(TeamOwner owner) {
        if (owner.getTeam() == null) {
            owner.setTeam(this);
        }
        this.owners.put(owner.getUserName(), owner);
        addSubscriber(owner);
    }

    //UC 6.2
    public void addOwner(User currentOwner, User newOwner) {
        if(teamStatus != TeamStatus.Open){
            return;
        }
        if (this.owners.containsKey(currentOwner.getUserName())) {
            TeamOwner newTeamOwner = (TeamOwner)User.getUserByID(newOwner.getUserName()).getRoles().get(Role.TEAM_OWNER);
            this.addOwner(newTeamOwner);
        }
    }


    //UC 6.3
    public void removeOwner(User user) {
        //Impossible to leave the Team without an Owner
        if (this.owners.size() <= 1) {
           throw new Error("The team cannot be left without an owner");
        } else {
            TeamOwner owner = (TeamOwner)user.getRoles().get(Role.TEAM_OWNER);
            this.owners.remove(user.getUserName());
            removeSubscriber(owner);
            HashSet<TeamOwner> ownerAppointments = owner.getOwnerAppointments();
            HashSet<TeamManager> managerAppointments = owner.getManagerAppointments();
            for(TeamOwner appointment: ownerAppointments){
                removeOwner(User.getUserByID(appointment.getUserName()));
            }
            for(TeamManager appointment : managerAppointments){
                removeManager(User.getUserByID(appointment.getUserName()));
            }
        }
    }


   //UC 6.4
   public void addManager(User currentOwner, User newManager) {
       if (this.owners.containsKey(currentOwner.getUserName())) {
           TeamManager newTeamManager = (TeamManager)newManager.getRoles().get(Role.TEAM_MANAGER);
           newTeamManager.setCurrentTeam(this);
           this.managers.put(newTeamManager.getUserName(), newTeamManager);
           addSubscriber(newTeamManager);
       }
   }


   //UC 6.5
    public void removeManager(User managerUser){
        TeamManager manager = (TeamManager)managerUser.getRoles().get(Role.TEAM_MANAGER);
        this.managers.remove(managerUser.getUserName());
        removeSubscriber(manager);
    }

//    //UC 6.4
//    public Team addTeamManager(User user) {
//        user.addRoleToUser(Role.TEAM_MANAGER);
//        this.managers.put(user.getUserName(), (TeamManager) user.getRoles().get(Role.TEAM_MANAGER));
//        // TODO: Implement permissions
//        return this;
//    }


//    //UC 6.5
//    // TODO: 15/04/2020 recursive removing?
//    public boolean removeTeamManager(Subscriber owner, Subscriber managerToRemove) {
//        if (owner instanceof TeamOwner) {
//            TeamOwner teamOwner = (TeamOwner) owner;
//            if (managers.containsKey(managerToRemove.getUserName())) {
//                TeamManager userRemoveManager = managers.get(managerToRemove.getUserName());
//                //TODO: We need to get the manager's User: by a static method from User or a User field in TeamMember (Naor)
///*                if (userRemoveManager.getRoles().containsKey(Role.TEAM_MANAGER)) {
//                    userRemoveManager.getRoles().remove(Role.TEAM_MANAGER);
//                    return true;
//                }*/
//            }
//        }
//        return false;
//    }

    public TeamPlayer getPlayer(String userName) {
        return players.get(userName);
    }

    public TeamCoach getCoach(String userName) {
        return coaches.get(userName);
    }

    public HashMap<String,Field> getFields() {
        return fields;
    }

    public HashMap<String, TeamOwner> getOwners() {
        return owners;
    }

    public Budget getBudget() {
        return budget;
    }

    public PersonalPage getTeamPage() {
        return teamPage;
    }

    public static Team getTeamByName(String teamName) {
        //TODO: change the mock to DB
        return new Team(teamName, new Field("fieldName", 100), new TeamOwner("userName", "mail"));
    }


    public void addSubscriber(Subscriber user){
        alert.addSubscriber(user);
    }

    public void removeSubscriber(Subscriber user){
        alert.removeSubscriber(user);
    }

    public TeamStatus getTeamStatus(){
        return this.teamStatus;
    }

    public void setBudget(Budget budget){
        this.budget = budget;
    }

    public Alert getAlert() {
        return alert;
    }
}
