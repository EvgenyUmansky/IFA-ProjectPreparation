package domain;

import java.util.HashSet;

public class AssociationAgent extends Subscriber {

    // Constructor


    public AssociationAgent(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
    }


    //UC 9.1
    public boolean setLeague(String leaguename, int leaguequalification, HashSet<League> leagues) {
            //better to check in ui level
            if (leaguequalification >= 1 && leaguequalification <= 5) {
                leagues.add(new League(leaguename, leaguequalification));
                return true;
            } else {
                System.out.println("league qualification not in legal range");
            }
        return false;
    }


    //UC9.2
// TODO: 14/04/2020 need to update teams
    public boolean setSeasonToLeague(int year, League league, SchedulingMethod schedulingMethod, RankingMethod rankingMethod, HashSet<League> leagues) {
        if (leagues.contains(league)) {
            league.addLeaguePerSeason(new LeaguePerSeason(year, schedulingMethod, rankingMethod));
            return true;
        }
        return false;
    }

    //UC 9.3A
    // TODO: 10/04/2020
    public boolean addReferee(String userName, String password, String name, String mail, boolean isMail, int qualification, RefereeType refereeType, User user) {
            Subscriber referee = new Referee(userName, mail, isMail, qualification, refereeType);
            user.addRoleToUser("Referee", referee);
            return true;
    }


    //UC 9.3B
    // TODO: 10/04/2020
    public boolean removeReferee(String userName, User user) {
            user.removeRoleFromUser("Referee");
            return true;
    }


    //UC 9.4
    // TODO: 10/04/2020
    public boolean addRefereeToLeagueBySeason(Subscriber assAgent, LeaguePerSeason leaguePerSeason, User userRef) {
        if (assAgent instanceof AssociationAgent) {

            if (userRef.getRoles().containsKey("Referee")) {
                leaguePerSeason.addReferee((Referee) userRef.getRoles().get("Referee"));
                return true;
            }
        }
        return false;
    }

    //UC9.5
    public boolean setRankingMethod( int winP, int loseP, int drawP, LeaguePerSeason leaguePerSeason) {
        if (leaguePerSeason.isBegin() == false) {
            leaguePerSeason.getRankingMethod().setWinPoints(winP);
            leaguePerSeason.getRankingMethod().setLoosPoints(loseP);
            leaguePerSeason.getRankingMethod().setDrawPoints(drawP);
            return true;
        }
        return false;
    }


    //UC9.6
    public boolean setSchedulingMethod(LeaguePerSeason leaguePerSeason, SchedulingMethod schedulingMethod) {
            leaguePerSeason.setSchedulingMethod(schedulingMethod);
            return true;
    }

    //UC9.7
    public boolean startScheduleMethod(SchedulingMethod schedulingMethod, Subscriber assAgent, LeaguePerSeason leaguePerSeason) {
        if (assAgent instanceof AssociationAgent) {
            Team[] teams = leaguePerSeason.getTeamsInLeaguePerSeason().keySet().toArray(new Team[leaguePerSeason.getTeamsInLeaguePerSeason().size()]);
            schedulingMethod.scheduleGamePolicy(leaguePerSeason, teams);
            return true;
        }
        return false;
    }

}

