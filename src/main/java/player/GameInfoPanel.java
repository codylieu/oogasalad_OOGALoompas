package main.java.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameInfoPanel extends JPanel implements Observing {

	public static final String SCORE = "Score";
	public static final String LIFE = "Lives";
	public static final String MONEY = "Money";
	public static final String TIME = "Time";
	
	private Subject TDPlayerEngine;//Strange variable name....
	private JLabel scoreLabel;
	private JLabel lifeLabel;
	private JLabel moneyLabel;
	private JLabel timeLabel;

	public GameInfoPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		scoreLabel = new JLabel("Score: ");
		lifeLabel = new JLabel("Lives: ");
		moneyLabel = new JLabel("Money: ");
		timeLabel = new JLabel("Time: ");
		add(scoreLabel);
		add(lifeLabel);
		add(moneyLabel);
		add(timeLabel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update() {		
		Map<String, String> currentGameInfo = (Map<String, String>) TDPlayerEngine.getUpdate(this);
		scoreLabel.setText(currentGameInfo.get(SCORE));
		lifeLabel.setText(currentGameInfo.get(LIFE));
		moneyLabel.setText(currentGameInfo.get(MONEY));
		timeLabel.setText(currentGameInfo.get(TIME));
	}

	@Override
	public void setSubjectState(Subject s) {
		TDPlayerEngine = s;
		
	}
		

}