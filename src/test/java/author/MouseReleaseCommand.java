package test.java.author;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseReleaseCommand extends UserInputCommand {

	public MouseReleaseCommand() {
		super();
	}
	
	@Override
	public void execute(Robot r) {
		r.mouseRelease(InputEvent.BUTTON1_MASK);
	}

}
