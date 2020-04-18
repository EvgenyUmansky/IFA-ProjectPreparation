package domain;

import java.util.Date;
import java.util.HashSet;

public class TeamOwner extends  Subscriber {

    private Team team;
    private HashSet<TeamManager> managerAppointments;
    private HashSet<TeamOwner> ownerAppointments;


    // =================== Constructors ====================




    public TeamOwner(String userName, String mail, Team team, HashSet<TeamManager> managerAppointments) {
        super(userName, mail);
        this.team = team;
        this.managerAppointments = managerAppointments;
    }

    public TeamOwner(String userName, String mail) {
        super(userName, mail);
        this.managerAppointments = new HashSet<>();
    }


    // =================== Getters and Setters ====================

    public void setTeam(Team team){
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public HashSet<TeamManager> getManagerAppointments() {
        return managerAppointments;
    }


    // =================== Functionality ====================

    // Add property use case

    public boolean addProperty(Object property){
        team.addProperty(property);
        return true;
    }

    //add Team Owner
    public void addToOwnerAppointments(TeamOwner Owner) {
        this.ownerAppointments.add(Owner);
    }

    //add Manager Owner
    public void addToManagerAppointments(TeamManager teamManager) {
        this.managerAppointments.add(teamManager);
    }
    //remove Team Owner
    public void removeFromOwnerAppointments(TeamOwner OwnerToRemove) {
        this.ownerAppointments.remove(OwnerToRemove);
    }

    //remove Manager Owner
    public void removeFromManagerAppointments(TeamManager teamManager) {
        this.managerAppointments.remove(teamManager);
    }



    // Remove property use case

    public boolean removeProperty(Object property){
        if(property instanceof TeamManager){
            if(!(managerAppointments.contains((TeamManager)property))){
                return false;
            }
        }

        team.removeProperty(property);
        return true;
    }


    // Update details use case

    public boolean updatePlayerDetails(String userName, String squadNumber, Date birthDate, String position){
        TeamPlayer player = this.team.getPlayer(userName);
        player.updateDetails(birthDate,position,squadNumber);
        return true;
    }

    public boolean updateCoachDetails(String userName,  String validation, String role){
        TeamCoach coach = this.team.getCoach(userName);
        coach.updateDetails(validation,role);
        return true;
    }

    //UC 6.2
    // TODO: 15/04/2020 manage list of added owners for removing
    public void addTeamOwner(Subscriber secondOwner) {
      /*  if (owner instanceof TeamOwner == true) {
            TeamOwner teamOwner = (TeamOwner)owner;
            if(users.containsKey(secondOwner.getUserName())) {
                User userNewOwner = users.get(secondOwner.getUserName());
                Subscriber newSubsOwner = new TeamOwner(secondOwner.getUserName(), secondOwner.getMail(), false, teamOwner.getTeam(), teamOwner.getManagerAppointments());
                userNewOwner.getRoles().put("Team Owner", newSubsOwner);
            }*/
    }
    //UC 6.3
    // TODO: 15/04/2020 recursive removing?
    public void removeTeamOwner(Subscriber owner,Subscriber ownerToRemove) {
       /* if (owner instanceof TeamOwner == true) {
            TeamOwner teamOwner = (TeamOwner)owner;
            if(users.containsKey(ownerToRemove.getUserName())) {
                User userRemoveOwner = users.get(ownerToRemove.getUserName());
                if (userRemoveOwner.getRoles().containsKey("Team Owner")) {
                    userRemoveOwner.getRoles().remove("Team Owner");
                }
            }
        }*/
    }




    //UC 6.4

    // TODO: 15/04/2020 manage list of added managers for removing
    public void addTeamManager(Subscriber owner,Subscriber newManager) {
        /*if (owner instanceof TeamOwner == true) {
            TeamOwner teamOwner = (TeamOwner) owner;
            if (users.containsKey(newManager.getUserName())) {
                User userNewManager = users.get(newManager.getUserName());
                Subscriber newSubManager = new TeamManager(newManager.getUserName(), newManager.getMail(), false);
                userNewManager.getRoles().put("Team Manager", newSubManager);
            }
        }*/
    }

    //UC 6.5
    // TODO: 15/04/2020 recursive removing?
    public void removeTeamManager(Subscriber owner,Subscriber managerToRemove) {
        /*if (owner instanceof TeamOwner == true) {
            TeamOwner teamOwner = (TeamOwner)owner;
            if(users.containsKey(managerToRemove.getUserName())) {
                User userRemoveManager = users.get(managerToRemove.getUserName());
                if (userRemoveManager.getRoles().containsKey("Team Manager")) {
                    userRemoveManager.getRoles().remove("Team Manager");
                }
            }
        }*/
    }


}