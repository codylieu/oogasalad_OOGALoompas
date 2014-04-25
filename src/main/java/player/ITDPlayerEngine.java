package main.java.player;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

import main.java.player.util.CursorState;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

public interface ITDPlayerEngine {
	
		public void initCanvas();

		public void initGame();

		public void speedUp();

		/**
		 * 
		 * @return whether the game was slowed down or not
		 */
		public boolean slowDown();
		
		public void paintFrame();
		
		public void setCursorState(CursorState newCursorState);

		public CursorState getCursorState();
		
		public String getCurrentDescription();

		public void doFrame();
		
		public void update();

		/**
		 * Toggle the cursor status from AddTower to None 
		 * or vice-versa
		 */
		public void toggleAddTower();

		public void toggleFullScreen();
		
		public void toggleRunning();


		public void register(Observing o);
		
		public void unregister(Observing o);

		public void notifyObservers();

		public List<String> getPossibleTowers();

		public List<String> getPossibleItems();

		public void loadBlueprintFile(String fileName) throws ClassNotFoundException, IOException, ZipException, net.lingala.zip4j.exception.ZipException;

		public void saveGameState(String gameName);
		
		public void loadGameState(String gameName);

		public Map<String, String> getGameAttributes();

		public void setSubject(List<Subject> s);

		public void setSubject(Subject s);

	

}
