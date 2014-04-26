package main.java.player.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.List;

import main.java.reflection.MethodAction;
import main.java.reflection.Reflection;
import main.java.reflection.ReflectionException;

/**
 * adapted from Duvall's MethodAction class
 * @author Michael Han
 *
 */
public class MultipleMethodAction implements ActionListener{
	private MethodAction[] myMethodActions;

	public MultipleMethodAction(MethodAction ... methods){
		myMethodActions = methods;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(MethodAction m: myMethodActions){
			m.actionPerformed(e);
		}
	}
}
