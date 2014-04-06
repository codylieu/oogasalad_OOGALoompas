package main.java.player;

import java.util.ArrayList;
import java.util.List;



import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameInfoPanel extends JPanel implements Observing {

	private List<String> gameInfo;
	private Subject TDPlayerEngine;
	private JLabel scoreLabel;
	private JLabel lifeLabel;
	private JLabel moneyLabel;
	private JLabel timeLabel;

	public GameInfoPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		gameInfo = new ArrayList<String>();
		scoreLabel = new JLabel("dasf");
		lifeLabel = new JLabel("asdfsd");
		moneyLabel = new JLabel("asdfsf");
		timeLabel = new JLabel("asdfsdf");
		add(scoreLabel);
		add(lifeLabel);
		add(moneyLabel);
		add(timeLabel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update() {		
		List<String> currentGameInfo = (List<String>) TDPlayerEngine.getUpdate(this);
		scoreLabel.setText(currentGameInfo.get(0));
		lifeLabel.setText(currentGameInfo.get(1));
		moneyLabel.setText(currentGameInfo.get(2));
		timeLabel.setText(currentGameInfo.get(3));
	}

	@Override
	public void setSubjectState(Subject s) {
		TDPlayerEngine = s;
		
	}
		

}