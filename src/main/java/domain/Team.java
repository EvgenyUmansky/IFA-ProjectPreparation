package domain;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.HashSet;

public class Team {

    private String teamName;
    private Field stadium; //The team's main stadium, where they play official matches
    private HashSet<Field> fields; //All the team's fields, including the stadium and training fields
    private HashMap<String, TeamPlayer> players;
    private HashMap<String, TeamCoach> coaches;
    private HashMap<String, TeamManager> managers;
    private HashMap<String, TeamOwner> owners;
    private String teamEmail;
    private Budget budget;
    private PersonalPage teamPage;
    private TeamStatus teamStatus;

    // Constructor
    public Team(String name, Field stadium, TeamOwner owner) {
        this.teamName = name;
        this.stadium = stadium;
        this.fields = new HashSet<>();
        this.owners = new HashMap<>();
        this.managers = new HashMap<>();
        this.coaches = new HashMap<>();
        this.players = new HashMap<>();
        this.fields.add(stadium);
        this.teamStatus =TeamStatus.Open;
        this.owners.put(owner.getUserName(), owner);
    }



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


    public void addProperty(Object property) {
        if(teamStatus != TeamStatus.Open){
            return;
        }
        if (property instanceof Field) {
            this.fields.add((Field) property);
        }

        if (property instanceof TeamPlayer) {
            ((TeamPlayer) property).setCurrentTeam(this);
            this.players.put(((TeamPlayer) property).getUserName(), (TeamPlayer) property);
        }

        if (property instanceof TeamCoach) {
            ((TeamCoach) property).setCurrentTeam(this);
            this.coaches.put(((TeamCoach) property).getUserName(), (TeamCoach) property);
        }

        if (property instanceof TeamManager) {
            ((TeamManager) property).setCurrentTeam(this);
            this.managers.put(((TeamManager) property).getUserName(), (TeamManager) property);
        }
    }

    public void removeProperty(Object property) {
        if(teamStatus != TeamStatus.Open){
            return;
        }
        if (property instanceof Field) {
            this.fields.remove(property);
        }

        if (property instanceof TeamPlayer) {
            this.players.remove(((TeamPlayer) property).getUserName());
        }

        if (property instanceof TeamCoach) {
            this.coaches.remove(((TeamCoach) property).getUserName());
        }

        if (property instanceof TeamManager) {
            this.managers.remove(((TeamManager) property).getUserName());
        }
    }


    public void closeTeam(User user) {
        // TODO: 18/04/2020 add relevant subscribers to mailSet
        Alert alert = new Alert();
        //alert.addToMailSet();
        if (teamStatus == TeamStatus.Open) {
            if(user.getRoles().containsKey(Role.SYSTEM_ADMIN)){
                teamStatus = TeamStatus.PermanentlyClose;
                alert.sendAlert(new AlertNotification("close team permanently","you team close permanently"));

            }
            else if(user.getRoles().containsKey(Role.TEAM_OWNER)){
                teamStatus = TeamStatus.TempClose;
                alert.sendAlert(new AlertNotification("close team temporary","you team close temporary"));
            }
        }
    }

    public void openTeam() {
        // TODO: 18/04/2020 add relevant subscribers to mailSet
        Alert alert = new Alert();
        //alert.addToMailSet();
        if(teamStatus == TeamStatus.TempClose){
            teamStatus = TeamStatus.Open;
            alert.sendAlert(new AlertNotification("open your team","your team open again"));
        }
    }


    //UC 6.2
    public void addOwner(TeamOwner owner) {
        if(teamStatus != TeamStatus.Open){
            return;
        }
        if (owner.getTeam() == null) {
            owner.setTeam(this);
        }

        this.owners.put(owner.getUserName(), owner);
    }

    //UC 6.2
    public void addOwner(User currentOwner, User newOwner) {
        if(teamStatus != TeamStatus.Open){
            return;
        }
        if (this.owners.containsKey(currentOwner.getUserName())) {
            TeamOwner newTeamOwner = new TeamOwner(newOwner.getUserName(), newOwner.getMail());
            newOwner.addRoleToUser(Role.TEAM_OWNER, newTeamOwner);
            this.addOwner(newTeamOwner);
        }
    }


    //UC 6.3
    public void removeOwner(User user) {
        if(teamStatus != TeamStatus.Open){
            return;
        }
        //Impossible to leave the Team without an Owner
        if (this.owners.size() <= 1) {
           throw new Error("Team cannot be without owner");
        } else {
            this.owners.remove(user.getUserName());
            user.removeRoleFromUser(Role.TEAM_OWNER);
        }
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

    public HashSet<Field> getFields() {
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
        return new Team(teamName, null, null);
    }
}
