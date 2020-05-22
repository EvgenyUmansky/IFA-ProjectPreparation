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
            return "Temporarily Closed";
        }
    },
    PermanentlyClose {
        @Override
        public String toString(){
            return "Permanently Closed";
        }
    }
}
