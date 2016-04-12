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
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;

/**
 * @author juanz
 *
 */
public class QuestionnairePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textQuestion;
	private Date startDate;
	private JTextField textValue5;
	private JTextField textValue1;
	private JTextField textValue4;
	private JTextField textValue2;
	private JTextField textValue3;
	private JPanel questionPanel;
	private JButton editQButton;
	private JPanel valuePanel;
	private JLabel labelValue1;
	private JLabel labelValue2;
	private JLabel labelValue3;
	private JComponent labelValue4;
	private JLabel labelValue5;
	private MISPCore manager;
	private SurveyTable surveyTable;
	private QuestionnairePanel panel;
	private JPanel graphPanel;
	private StudentSurveyPanel studentSurveyPanel;

	public QuestionnairePanel(MISPCore manager) {
		
		this.manager = manager;
		surveyTable = this.manager.getSurveyTable();
		panel = this;
		studentSurveyPanel = this.manager.getToastPanel().getStudentSurveyPanel();
		
		
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		questionPanel = new JPanel();
		questionPanel.setBackground(Color.WHITE);
		add(questionPanel, BorderLayout.NORTH);

		editQButton = new JButton("Submit Question");
		editQButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		editQButton.addActionListener(this);
		questionPanel.add(editQButton);

		textQuestion = new JTextField();
		textQuestion.setToolTipText("Example: Rate the MESA Tutors From 1 - 5, on Availability.");
		textQuestion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		textQuestion.addActionListener(this);
		questionPanel.add(textQuestion);
		textQuestion.setColumns(30);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		questionPanel.add(verticalStrut_4);
		

		 valuePanel = new JPanel();
		valuePanel.setBackground(Color.WHITE);
		add(valuePanel, BorderLayout.WEST);
		valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));

		labelValue1 = new JLabel("Response 1:");
		labelValue1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelValue1.setHorizontalAlignment(SwingConstants.CENTER);
		valuePanel.add(labelValue1);

		textValue1 = new JTextField();
		textValue1.setToolTipText("Example: Never Available");
		valuePanel.add(textValue1);
		textValue1.setColumns(15);
		textValue1.addActionListener(this);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut_1);

		labelValue2 = new JLabel("Response 2:");
		labelValue2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue2);

		textValue2 = new JTextField();
		textValue2.setToolTipText("Example: Rarely Available");
		valuePanel.add(textValue2);
		textValue2.setColumns(15);
		textValue2.addActionListener(this);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut);

		labelValue3 = new JLabel("Response 3:");
		labelValue3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue3);

		textValue3 = new JTextField();
		textValue3.setToolTipText("Example: Available");
		
		valuePanel.add(textValue3);
		textValue3.setColumns(15);
		textValue3.addActionListener(this);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut_2);

		 labelValue4 = new JLabel("Response 4:");
		labelValue4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue4);

		textValue4 = new JTextField();
		textValue4.setToolTipText("Example: Very Available");
		valuePanel.add(textValue4);
		textValue4.setColumns(15);
		textValue4.addActionListener(this);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut_3);

		 labelValue5 = new JLabel("Response 5:");
		labelValue5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue5);

		textValue5 = new JTextField();
		textValue5.setToolTipText("Example: Always Available");
		valuePanel.add(textValue5);
		textValue5.setColumns(15);
		textValue5.addActionListener(this);

		startDate = new Date();
		graphPanel = new JPanel();
		add(graphPanel, BorderLayout.CENTER);
		panel.add(graphPanel, BorderLayout.CENTER);
		
		//Update the Question when the QuestionnairePanel is shown
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				
				String question  = surveyTable.getQuestion();
				textQuestion.setText(question);
				
				if (surveyTable.getID() != -2){
					updateGraph();
				}
				
			}
			public void componentHidden(ComponentEvent e){
				
				String question  = surveyTable.getQuestion();
				textQuestion.setText(question);
				
				if (surveyTable.getID() != -2){
					updateGraph();
				}
				
			}

		});

	}
	public void updateGraph(){
		GraphReport newGraph = new GraphReport(textQuestion.getText());
		JPanel newGraphPanel = newGraph.createPiePanel();
		BorderLayout bl = (BorderLayout) panel.getLayout();
		panel.remove(bl.getLayoutComponent(BorderLayout.CENTER));
		panel.add(newGraphPanel);
		revalidate();
	}

	public ArrayList<Integer> getResult() {
		ArrayList<Integer> results = new ArrayList<Integer>();
		results = this.manager.getResults();
		return results;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(editQButton == e.getSource()){
			textQuestion.setText(GlobalUI.CLEAR);
			textQuestion.setEditable(true);
			clearValueChoices();
			setEditableValueChoices(true);
		}
		if(textQuestion == e.getSource()){
			
			ArrayList<String> labelValues = new ArrayList<String>();
			labelValues.add(textValue1.getText());
			labelValues.add(textValue2.getText());
			labelValues.add(textValue3.getText());
			
			ArrayList<String> Labels = setSurveyLabel();
			studentSurveyPanel.setButtonValue(GlobalUI.responseOne, Labels.get(0) );
			studentSurveyPanel.setButtonValue(GlobalUI.responseTwo, Labels.get(1));
			studentSurveyPanel.setButtonValue(GlobalUI.responseThree, Labels.get(2));
			studentSurveyPanel.setButtonValue(GlobalUI.responseFour, Labels.get(3));
			studentSurveyPanel.setButtonValue(GlobalUI.responseFive, Labels.get(4));
			
			addQuestion();
			setEditableValueChoices(false);
			
		}
		

	}
	public ArrayList<String> setSurveyLabel(){
		ArrayList<String> labels = new ArrayList<String>();
		
		labels.add(textValue1.getText());
		labels.add(textValue2.getText());
		labels.add(textValue3.getText());
		labels.add(textValue4.getText());
		labels.add(textValue5.getText());
		return labels;
		

	}
	public void clearValueChoices(){
		textValue1.setText(GlobalUI.CLEAR);
		textValue2.setText(GlobalUI.CLEAR);
		textValue3.setText(GlobalUI.CLEAR);
		textValue4.setText(GlobalUI.CLEAR);
		textValue5.setText(GlobalUI.CLEAR);
	}
	public void setEditableValueChoices(boolean flag){
		textValue1.setEditable(flag);
		textValue2.setEditable(flag);
		textValue3.setEditable(flag);
		textValue4.setEditable(flag);
		textValue5.setEditable(flag);
	}
	public void addQuestion(){
		
		manager.removeAll();
		String question  = textQuestion.getText();
		manager.addQuestion(question, startDate);
		textQuestion.setText(question);
		textQuestion.setEditable(false);
	}
}
