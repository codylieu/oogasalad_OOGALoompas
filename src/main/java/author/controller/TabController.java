package main.java.author.controller;

public abstract class TabController {
	private MainController mySuperController;

	public TabController(MainController superController) {
		mySuperController = superController;
	}

	public abstract boolean isTabValid();

}
