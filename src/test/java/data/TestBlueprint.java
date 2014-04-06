package test.java.data;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import main.java.data.datahandler.AuthorDataHandler;
import main.java.schema.GameBlueprint;
import main.java.schema.GameSchema;

import org.junit.Test;

public class TestBlueprint {

	public final static String FILE_PATH = "testBlueprint.ser";

	@Test
	public void TestBlueprintIsSaved() {
		//Set up blueprint
		GameSchema scenario = new GameSchema();
		scenario.setMyStartingLives(50);
		GameBlueprint savedBlueprint = new GameBlueprint();
		savedBlueprint.setMyGameScenario(scenario);
		
		//Try to save blueprint
		AuthorDataHandler dataHandler = new AuthorDataHandler();
		try {
			dataHandler.saveBlueprint(savedBlueprint, FILE_PATH);
		} catch (IOException e) {}
		
		//Load blueprint
		GameBlueprint loadedBlueprint = null;
		try {
			loadedBlueprint = dataHandler.loadBlueprint(FILE_PATH);
		} catch (ClassNotFoundException | IOException e) {}
		
		//Check if the variable values are the same
		assertEquals(scenario.getMyStartingLives(), loadedBlueprint.getMyGameScenario().getMyStartingLives());
	}

}
