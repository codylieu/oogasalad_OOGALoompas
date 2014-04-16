package main.java.player;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {
	
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		new Player();
	}

}
