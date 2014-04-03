package main.java.author.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * IMPORTANT: This class is entirely taken from 
 * 
 * http://stackoverflow.com/questions/10271924/how-to-implement-and-mantain-multiple-actionlistener
 *
 * and is soon to be cleared for use with Professor Duvall. 
 */

public class ActionListenerUtil {    
	  private static final Class<?>[] INTERFACES = { ActionListener.class };

	  public static ActionListener actionListener(final Object target,
	                                                    String method) {
	    final Method proxied = method(target, method);
	    InvocationHandler handler = new InvocationHandler() {
	      @Override
	      public Object invoke(Object proxy, Method method, Object[] args)
	          throws Throwable {
	        ActionEvent event = (ActionEvent) args[0];
	        return proxied.invoke(target, event);
	      }
	    };
	    return (ActionListener) Proxy.newProxyInstance(target.getClass()
	        .getClassLoader(), INTERFACES, handler);
	  }

	  private static Method method(Object target, String method) {
	    try {
	      return target.getClass().getMethod(method, ActionEvent.class);
	    } catch (NoSuchMethodException e) {
	      throw new IllegalStateException(e);
	    } catch (SecurityException e) {
	      throw new IllegalStateException(e);
	    }
	  }
	}