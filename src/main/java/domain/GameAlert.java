package domain;

/**
 * Represents the types of events that can take place during a football match
 */
public enum GameAlert {
    GOAL,
    OFFSIDE,
    FOUL,
    RED_CARD,
    YELLOW_CARD,
    INJURY,
    PLAYER_IN,
    PLAYER_OUT
}
