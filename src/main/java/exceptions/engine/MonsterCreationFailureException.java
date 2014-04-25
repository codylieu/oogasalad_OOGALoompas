package main.java.exceptions.engine;

public class MonsterCreationFailureException extends Exception {
    public MonsterCreationFailureException (Exception e) {
        super(e);
    }
}
