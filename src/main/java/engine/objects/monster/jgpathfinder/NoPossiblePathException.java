package main.java.engine.objects.monster.jgpathfinder;

public class NoPossiblePathException extends Exception {
    public NoPossiblePathException() {
        super("No path found between source and target tiles");
    }
}
