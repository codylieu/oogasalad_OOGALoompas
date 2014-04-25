package test.java.author;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MousePressCommand extends UserInputCommand {

	public MousePressCommand() {
		super();
	}
	
	@Override
	public void execute(Robot r) {
		r.mousePress(InputEvent.BUTTON1_MASK);
	}

}
