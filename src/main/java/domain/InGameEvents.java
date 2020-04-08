package domain;

import java.util.ArrayList;

public class InGameEvents {
    private ArrayList<GameEvent> events;

    /////////// Constructors ///////////
    public InGameEvents() {
        this.events = new ArrayList<>();
    }


    /////////// Functionality ///////////
    public void addEvent(GameEvent event){
        this.events.add(event);
    }

    public void removeEvent(GameEvent event){
        this.events.remove(event);
    }


    /////////// Getters and Setters ///////////
    public ArrayList<GameEvent> getEvents() {
        return events;
    }
}