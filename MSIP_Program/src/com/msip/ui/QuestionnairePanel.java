/**
 * 
 */
package com.msip.ui;

import javax.swing.JPanel;

import com.msip.db.SurveyTable;
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
import java.util.ArrayList;
import java.util.Date;
import java.awt.FlowLayout;

/**
 * @author juanz
 *
 */
public class QuestionnairePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private SurveyTable surveyTable;
	private Date startDate;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private ArrayList<Integer> results;
	

	public QuestionnairePanel(MISPCore msipCore) 
	{
		
		
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
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Value 1:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel);
		
		textField_3 = new JTextField();
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Value 2");
		panel_1.add(lblNewLabel_1);
		
		textField_5 = new JTextField();
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Vaue 3:");
		panel_1.add(lblNewLabel_2);
		
		textField_6 = new JTextField();
		panel_1.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Value 4");
		panel_1.add(lblNewLabel_3);
		
		textField_4 = new JTextField();
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Value 5");
		panel_1.add(lblNewLabel_4);
		
		textField_2 = new JTextField();
		textField_2.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		textField_2.setBackground(new Color(255, 255, 255));
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		//
		surveyTable = new SurveyTable();
		surveyTable.addQuestion("Rate the MESA Center.", startDate);
		
	}
	public void getResults()
	{
		//ArrayList<Integer> results = surveyTable.getResults();
		//Harcoded results for now
		results = new ArrayList<Integer>();
		results.add(0, 12);
		results.add(1, 13);
		results.add(2, 7);
		results.add(3, 10);
		results.add(4, 8);
		
	}
	
	
	public void addQuestion()
	{
		surveyTable.addQuestion("Rate the Available Tutors", startDate);
	}

}
