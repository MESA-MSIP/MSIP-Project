/**
 * 
 */
package com.msip.ui;

import javax.swing.JPanel;

import com.msip.manager.MISPCore;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Component;

/**
 * @author juanz
 *
 */
public class QuestionnairePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;

	public QuestionnairePanel(MISPCore msipCore) {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Edit Question");
		panel.add(btnNewButton);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		textField_1 = new JTextField();
		textField_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		textField_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
