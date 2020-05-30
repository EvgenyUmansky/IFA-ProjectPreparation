package domain;

/**
 * Represents the states that a team can be in
 */
public enum TeamStatus {
    Open {
        @Override
        public String toString(){
            return "open";
        }
    },
    TempClose {
        @Override
        public String toString(){
            return "TempClose";
        }
    },
    PermanentlyClose {
        @Override
        public String toString(){
            return "PermanentlyClose";
        }
    },

    Pending {
        @Override
        public String toString(){
            return "Pending";
        }
    }
}
