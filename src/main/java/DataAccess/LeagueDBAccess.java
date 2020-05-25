package DataAccess;

import domain.League;

import java.util.HashMap;

public class LeagueDBAccess implements DBAccess<League>{

    private static final LeagueDBAccess instance = new LeagueDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private LeagueDBAccess(){

    }

    public static LeagueDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(League league) {

    }

    @Override
    public void update(League league) {

    }

    @Override
    public void delete(League league) {

    }

    @Override
    public League select(String id) {
        return null;
    }

    @Override
    public HashMap<String, League> conditionedSelect(String[] conditions) {
        return null;
    }
}
