package domain;

/**
 * Represents the types of events that can take place during a football match
 */
public enum GameAlert {
    GOAL {
        public String toString(){
            return "Goal";
        }
    },
    OFFSIDE {
        public String toString(){
            return "Offside";
        }
    },
    FOUL {
        public String toString(){
            return "Foul";
        }
    },
    RED_CARD{
        public String toString(){
            return "Red_card";
        }
    },
    YELLOW_CARD{
        public String toString(){
            return "Yellow_card";
        }
    },
    INJURY{
        public String toString(){
            return "Injury";
        }
    },
    PLAYER_IN{
        public String toString(){
            return "Player_in";
        }
    },
    PLAYER_OUT{
        public String toString(){
            return "Player_out";
        }
    }
}
