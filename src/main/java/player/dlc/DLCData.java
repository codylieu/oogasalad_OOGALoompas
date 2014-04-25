package main.java.player.dlc;

/**
 * A bundle for the different entries
 * that could be downloaded from the Web
 * @author Kevin
 *
 */

public class DLCData {
	private String description;
	private String fileName;
	
	public DLCData (String input) {
		String[] tokenizedString = input.split(RepositoryViewer.DELIMITER, 2);
		description = tokenizedString[0];
		fileName = tokenizedString[1];
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getFileName() {
		return fileName;
	}

}
