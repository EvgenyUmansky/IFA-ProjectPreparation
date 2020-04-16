package domain;

public class AssociationAgent extends Subscriber {

    // Constructor


    public AssociationAgent(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
    }


//    // AssociationAgent UC 9.1
//
//    public boolean setLeagueByAssociationAgent(Subscriber assAgent, String leaguename, int leaguequalification) {
//        if (assAgent instanceof AssociationAgent) {
//            //better to check in ui level
//            if (leaguequalification >= 1 && leaguequalification <= 5) {
//                leagues.add(new League(leaguename, leaguequalification));
//                return true;
//            } else {
//                System.out.println("league qualification not in legal range");
//            }
//        }
//        return false;
//    }
//
//    //UC9.2
//// TODO: 14/04/2020 need to update teams
//    public boolean setSeasonToLeagueByAssociationAgent(Subscriber assAgent, int year, League league, SchedulingMethod schedulingMethod, RankingMethod rankingMethod) {
//        if (leagues.contains(league) && assAgent instanceof AssociationAgent) {
//            league.addLeaguePerSeason(new League( year, schedulingMethod, rankingMethod));
//            return true;
//        }
//        return false;
//    }
//
//    //UC 9.3A
//    // TODO: 10/04/2020
//    public boolean addReferee(Subscriber assAgent, String userName, String password, String name, String mail, boolean isMail, int qualification, RefereeType refereeType) {
//        if (assAgent instanceof AssociationAgent) {
//            Subscriber referee = new Referee(userName, mail, isMail, qualification, refereeType);
//            users.get(userName).addRoleToUser("Referee",referee);
//            return true;
//        }
//        return false;
//    }
//
//    //UC 9.3B
//    // TODO: 10/04/2020
//    public boolean removeReferee(Subscriber assAgent, String userName) {
//        if (assAgent instanceof AssociationAgent) {
//
//            users.get(userName).removeRoleFromUser("Referee");
//            return true;
//        }
//        return false;
//    }
//
//    //UC 9.4
//    // TODO: 10/04/2020
//    public boolean addRefereeToLeagueBySeason(Subscriber assAgent, League league, String userNameReferee) {
//        if (assAgent instanceof AssociationAgent) {
//            if(users.containsKey(userNameReferee) == true){
//                User user = users.get(userNameReferee);
//                if(user.getRoles().containsKey("Referee")){
//                    league.addReferee((Referee) user.getRoles().get("Referee"));
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    //UC9.5
//    public boolean setRankingMethod(Subscriber assAgent, int winP, int loseP, int drawP, League league) {
//        if (league.isBegin() == false && assAgent instanceof AssociationAgent == true) {
//            league.getRankingMethod().setWinPoints(winP);
//            league.getRankingMethod().setLoosPoints(loseP);
//            league.getRankingMethod().setDrawPoints(drawP);
//            return true;
//        }
//        return false;
//    }
//
//    //UC9.6
//    public boolean setSchedulingMethod(Subscriber assAgent, League league, SchedulingMethod schedulingMethod) {
//        if (assAgent instanceof AssociationAgent) {
//            league.setSchedulingMethod(schedulingMethod);
//            return true;
//        }
//        return false;
//    }
//
//    //UC9.7
//    public boolean startScheduleMethod(SchedulingMethod schedulingMethod, Subscriber assAgent, League league)
//    {
//        if(assAgent instanceof AssociationAgent){
//            Team[] teams = league.getTeamsInLeaguePerSeason().keySet().toArray(new Team[league.getTeamsInLeaguePerSeason().size()]);
//            schedulingMethod.scheduleGamePolicy(league, teams);
//            return true;
//        }
//        return false;
//    }
}
