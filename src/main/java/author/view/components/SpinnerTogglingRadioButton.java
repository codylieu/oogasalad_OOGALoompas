package main.java.author.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JSpinner.DefaultEditor;

import main.java.author.view.global_constants.FontConstants;

public class SpinnerTogglingRadioButton extends JRadioButton {

	private JComponent[] fieldsToToggle;
	private Font regularFont;

	public SpinnerTogglingRadioButton() {

	}

	public SpinnerTogglingRadioButton(String towerBehaviorShoots, boolean b) {
		super(towerBehaviorShoots, b);
	}

	public void setFieldsToToggle(JComponent... fieldsToToggle) {
		this.fieldsToToggle = fieldsToToggle;
	}

	public void toggle() {

		if (!this.isSelected()) {
			for (JComponent component : fieldsToToggle) {
				disableField(component);
			}
			return;
		} else {
			for (JComponent component : fieldsToToggle) {
				enableField(component);
			}
		}
	}

	private void disableField(JComponent component) {
		if (component instanceof JSpinner) {
			JSpinner spinner = (JSpinner) component;
			JTextField textField = ((DefaultEditor) spinner.getEditor())
					.getTextField();
			if (!textField.isEditable()) {
				return;
			}

			textField.setEditable(false);
			textField.setBackground(Color.DARK_GRAY);
			spinner.setFocusable(false);
			spinner.setUI(new javax.swing.plaf.basic.BasicSpinnerUI() {
				protected Component createNextButton() {
					Component c = new JButton();
					c.setPreferredSize(new Dimension(0, 0));
					return c;
				}

				protected Component createPreviousButton() {
					Component c = new JButton();
					c.setPreferredSize(new Dimension(0, 0));
					return c;
				}
			});
		}
		if (component instanceof JRadioButton) {
			JRadioButton button = (JRadioButton) component;
			button.setEnabled(false);
			button.setSelected(false);
		}

	}

	private void enableField(JComponent component) {
		if (component instanceof JSpinner) {
			JSpinner spinner = (JSpinner) component;
			JTextField textField = ((DefaultEditor) spinner.getEditor())
					.getTextField();
			if (textField.isEditable()) {
				return;
			}
			textField.setEditable(true);
			textField.setBackground(Color.WHITE);
			spinner.setFocusable(true);
			spinner.updateUI();
			spinner.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,
					(int) FontConstants.LARGE_FONT_SIZE));
		}

		if (component instanceof JRadioButton) {
			JRadioButton button = (JRadioButton) component;

			button.setEnabled(true);
			button.setSelected(true);
		}
	}

}
