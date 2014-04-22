package test.java.engine;

import static org.junit.Assert.assertEquals;

import main.java.data.DataHandler;
import main.java.engine.Model;
import main.java.engine.TestEngine;
import main.java.schema.GameBlueprint;


import org.junit.Before;

public class TestBlueprintLoading {

	DataHandler dataHandler;
	Model model;
	GameBlueprint blueprint;
	@Before
	public void setUp() throws Exception {
		dataHandler = new DataHandler();
		Model model = new Model(new TestEngine());
		blueprint = dataHandler.loadBlueprint("src/test/java/engine/TestBlueprint.zipZippedResources.zip",true);
		// TODO: Eventually call the loadBlueprint method with the file path (actually load the blueprint rather than using test in Model)
		//model.initializeBlueprintContents(blueprint);
	}

	@org.junit.Test
	public void testATan() throws Exception {

	}
}