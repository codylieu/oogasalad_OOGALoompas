package main.java.author.view.tabs;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.author.controller.MainController;

//SplitPaneDemo itself is not a visible component.
public class EnemyEditorTab extends EditorTab implements ListSelectionListener {
	private JLabel picture;
	private JList list;
	private JSplitPane splitPane;
	private String[] imageNames = { "spider", "wolf", "troll", "demon",
			"dragon" };

	private JLabel healthLabel;
	private JLabel speedLabel;
	private JLabel damageLabel;

	private JTextField healthField;
	private JTextField speedField;
	private JTextField damageField;

	private static String healthString = "Health: ";
	private static String speedString = "Speed: ";
	private static String damageString = "Damage: ";

	private NumberFormat numberFormat;

	public EnemyEditorTab(MainController c) {
		super(c);
		this.setLayout(new BorderLayout());
		initSplitPane();
	}

	public void initSplitPane() {
		list = new JList(imageNames);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);

		JScrollPane listScrollPane = new JScrollPane(list);
		picture = new JLabel();
		picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
		picture.setHorizontalAlignment(JLabel.CENTER);

		JComponent editorPane = makeEditorPane();

		// Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane,
				editorPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		// Provide minimum sizes for the two components in the split pane.
		Dimension minimumSize = new Dimension(100, 50);
		listScrollPane.setMinimumSize(minimumSize);
		editorPane.setMinimumSize(minimumSize);

		// Provide a preferred size for the split pane.
		splitPane.setPreferredSize(new Dimension(this.getWidth(), this
				.getHeight()));
		updateLabel(imageNames[list.getSelectedIndex()]);
		this.add(splitPane);
	}

	private JComponent makeEditorPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(makeAttributesPane(), BorderLayout.WEST);
		result.add(makeImagesPane(), BorderLayout.EAST);
		result.add(makeDeleteEnemyButton(), BorderLayout.SOUTH);

		return result;
	}

	private Component makeDeleteEnemyButton() {
		JButton result = new JButton("Delete Enemy");
		return result;
	}

	private JComponent makeImagesPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(makeEnemyGraphicPane(), BorderLayout.NORTH);
		result.add(makeCollisionGraphicPane(), BorderLayout.SOUTH);
		return result;
	}

	private JComponent makeEnemyGraphicPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		Canvas canvas = new Canvas();
		canvas.setSize(200, 200);
		canvas.setBackground(Color.BLACK);
		result.add(canvas, BorderLayout.NORTH);
		result.add(makeChooseGraphicsButton(), BorderLayout.SOUTH);
		return result;
	}

	private JComponent makeCollisionGraphicPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		Canvas canvas = new Canvas();
		canvas.setSize(200, 200);
		canvas.setBackground(Color.BLACK);
		result.add(canvas, BorderLayout.NORTH);
		result.add(makeChooseGraphicsButton(), BorderLayout.SOUTH);
		return result;
	}

	private JButton makeChooseGraphicsButton() {
		JButton result = new JButton("Choose graphics");
		return result;
	}

	// Listens to the list
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		updateLabel(imageNames[list.getSelectedIndex()]);
	}

	// Renders the selected image
	protected void updateLabel(String name) {
		ImageIcon icon = createImageIcon("images/" + name + ".gif");
		picture.setIcon(icon);
		if (icon != null) {
			picture.setText(null);
		} else {
			picture.setText("Image not found");
		}
	}

	// Used by SplitPaneDemo2
	public JList getImageList() {
		return list;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = EnemyEditorTab.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private JComponent makeLabelPane() {
		healthLabel = new JLabel(healthString);
		speedLabel = new JLabel(speedString);
		damageLabel = new JLabel(damageString);
		JPanel labels = new JPanel(new GridLayout(0, 1));
		labels.add(healthLabel);
		labels.add(speedLabel);
		labels.add(damageLabel);
		return labels;
	}

	private JComponent makeFieldPane() {
		healthField = new JFormattedTextField(numberFormat);
		healthField.setColumns(10);
		speedField = new JFormattedTextField(numberFormat);
		damageField = new JFormattedTextField(numberFormat);
		JPanel fields = new JPanel(new GridLayout(0, 1));
		fields.add(healthField);
		fields.add(speedField);
		fields.add(damageField);
		return fields;
	}

	private JComponent makeAttributesPane() {
		JPanel myAttributes = new JPanel();
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		myAttributes.add(makeLabelPane(), BorderLayout.CENTER);
		myAttributes.add(makeFieldPane(), BorderLayout.LINE_END);
		return myAttributes;
	}
}