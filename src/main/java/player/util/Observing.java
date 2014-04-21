package main.java.player.util;

import java.util.List;

public interface Observing {
	public void update();
	public void setSubject(List<Subject> s);
}
