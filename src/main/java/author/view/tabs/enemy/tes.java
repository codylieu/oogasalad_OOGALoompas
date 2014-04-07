package main.java.author.view.tabs.enemy;

import java.util.ArrayList;

import javax.swing.JComponent;

public class tes {
	private static ArrayList<String> dog;

	public tes() {
		dog = new ArrayList<String>();
		makeComponent(dog);
		System.out.println(dog.size());
	}
	public static void main(String[] args) {
		new tes();
	}

	public static void makeComponent(ArrayList<String> dog) {
		dog = new ArrayList<String>();
		dog.add("cat");
	}
}
