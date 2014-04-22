package main.java.player.panels;

import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

/**
 * Allows User to display Game info as Observing object
 * @author Michael
 *
 */
@SuppressWarnings("serial")
public class GameInfoPanel extends JPanel implements Observing {

	public static final String SCORE = "Score";
	public static final String LIFE = "Lives";
	public static final String MONEY = "Money";
	public static final String TIME = "Time";

	private TDPlayerEngine engine;
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

	public void update() {		
		Map<String, String> currentGameInfo = engine.getGameAttributes();
		scoreLabel.setText(currentGameInfo.get(SCORE));
		lifeLabel.setText(currentGameInfo.get(LIFE));
		moneyLabel.setText(currentGameInfo.get(MONEY));
		timeLabel.setText(currentGameInfo.get(TIME));
	}

	@Override
	public void setSubject(Subject s) {
		engine = (TDPlayerEngine) s;
	}

	@Override
	public void setSubject(List<Subject> s) {
		// TODO Auto-generated method stub
		
	}


}