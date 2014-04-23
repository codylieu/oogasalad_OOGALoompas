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
	private List<Method> myMethods;
	private List<Object[]> myArgsList;
	private List<Object> myTargetsList;

	public MultipleMethodAction(MethodAction ... methods){
		myMethodActions = methods;
		for(MethodAction m: myMethodActions){
			try
			{
				Method temp = m.getTarget().getClass().getDeclaredMethod(m.getMethodName(),
						Reflection.toClasses(m.getArguments()));
				myMethods.add(temp);
				myArgsList.add(m.getArguments());
				myTargetsList.add(m.getTarget());
			}
			catch (Exception e)
			{
				throw new ReflectionException(e.getMessage());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int count = 0;
		try
		{
			for(Method m: myMethods){
				m.invoke(myTargetsList.get(count), myArgsList.get(count));
				count ++;
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			throw new ReflectionException(e1.getMessage());
		}
	}
}
