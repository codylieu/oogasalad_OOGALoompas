package test.java.author;

import java.awt.Robot;

public class KeyReleaseCommand extends UserInputCommand {

	private int myKeyCode;
	
	public KeyReleaseCommand(int keyCode) {
		super(keyCode);
		myKeyCode = keyCode;
	}
	
	@Override
	public void execute(Robot r) {
		r.keyRelease(myKeyCode);
	}

}
