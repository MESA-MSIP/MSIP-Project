/**
 * 
 */
package com.msip.ui;

import javax.swing.JPanel;

import com.msip.db.SurveyTable;
import com.msip.db.SurveyTableLables;
import com.msip.manager.MISPCore;

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;

/**
 * @author juanz
 *
 */
public class QuestionnairePanel extends JPanel implements ActionListener, KeyListener {

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
	private JLabel questionLabel;
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
	private SurveyTableLables surveyTableLables;
	private popUpResponse popUp;

	public QuestionnairePanel(MISPCore manager) {

		/*
		Instantiating and receiving the MSIPCore.
		Set the panel to our variable panel for later.
		Store our student survey panel and labels into this class.
		 */
		this.manager = manager;
		surveyTable = this.manager.getSurveyTable();
		panel = this;
		studentSurveyPanel = this.manager.getToastPanel().getStudentSurveyPanel();
		surveyTableLables = this.manager.getSurveyTableLables();

		//Set the Background to Global Background Color
		setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		setLayout(new BorderLayout(0, 0));

		//Create a question panel, and set it to the "North" Region of BorderLayout
		questionPanel = new JPanel();
		questionPanel.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		add(questionPanel, BorderLayout.NORTH);

		//Creates Label for Survey Question
		questionLabel = new JLabel("Question:");
		questionLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		questionPanel.add(questionLabel);

		/*
		Create a Textfield that will prompt users for the Question, with the following attributes:
		SetHorizontalAlignment: Center the Text in TextField.
		AddActionListener: Add an event listener that when something happens to this textfield, some function is called.
		AddKeyListener: When a key is pressed, edited, or manipulated, then a function is performed.
		We will then add this textfield to the Question Panel up above.
		Set Columns: Question can only store up to 30 Characters.
		 */
		textQuestion = new JTextField();
		textQuestion.setToolTipText("Example: Rate the MESA Tutors From 1 - 5, on Availability.");
		textQuestion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		textQuestion.addActionListener(this);
		textQuestion.addKeyListener(this);
		questionPanel.add(textQuestion);
		textQuestion.setColumns(30);

		// Constrain our QuestionPanel with a vertical strut of 20 pixels.
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		questionPanel.add(verticalStrut_4);

		//Create a Button that will either allow editing of the text.
		editQButton = new JButton();
		toggleButton();
		GlobalUI.formatButtonAdmin(editQButton, 60, new Font("Tahoma", Font.PLAIN, 18));
		editQButton.addActionListener(this);
		questionPanel.add(editQButton);

		//Create a Panel that will store all the textfields of our responses.
		//We will store these in the west region of borderlayout, as vertical layout, e.g. "Y_AXIS"
		valuePanel = new JPanel();
		valuePanel.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		add(valuePanel, BorderLayout.WEST);
		valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));

		/*
		Create all the Labels and the TextFields of Each Response.
		 */

		labelValue1 = new JLabel("Response 1:");
		labelValue1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelValue1.setHorizontalAlignment(SwingConstants.CENTER);
		valuePanel.add(labelValue1);

		textValue1 = new JTextField();
		textValue1.setToolTipText("Example: Never Available");
		valuePanel.add(textValue1);
		textValue1.setColumns(15);
		textValue1.addActionListener(this);
		textValue1.addKeyListener(this);

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
		textValue2.addKeyListener(this);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut);

		labelValue3 = new JLabel("Response 3:");
		labelValue3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue3);

		textValue3 = new JTextField();
		textValue3.setToolTipText("Example: Available");
		textValue3.addActionListener(this);
		valuePanel.add(textValue3);
		textValue3.setColumns(15);
		textValue3.addKeyListener(this);

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
		textValue4.addKeyListener(this);

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
		textValue5.addKeyListener(this);




		/*
		Create a Graph, and Store into Center Region of our Borderlayout.
		 */
		startDate = new Date();
		graphPanel = new JPanel();
		graphPanel.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		add(graphPanel, BorderLayout.CENTER);
		panel.add(graphPanel, BorderLayout.CENTER);

		// Update the Question when the QuestionnairePanel is shown
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				String question = surveyTable.getQuestion();
				textQuestion.setText(question);
				if (surveyTable.getID() != -2) {
					updateGraph();
				}

				// setText of the Labels
				setResponseLabels();
				checkForText();
			}

			public void componentHidden(ComponentEvent e) {
				// setText of the labels

				String question = surveyTable.getQuestion();
				textQuestion.setText(question);

				if (surveyTable.getID() != -2) {
					updateGraph();
				}
				setResponseLabels();
				checkForText();

			}

		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Edit Button is the source of the actionListener
		if (editQButton == e.getSource()) {
			if (editQButton.getText() == GlobalUI.clearButtonText) {
				setEditableValueChoices(true);
				clearValueChoices();
				clearQAndLabels();
				clearGraph();
				toggleButton();
			} else {
				if (editQButton.getText() == GlobalUI.submitButtonText) {
					if (checkForCompleteFields() == false) {
						popUp = new popUpResponse();
						int textQuestionLength = textQuestion.getText().length();
						if (textQuestionLength < GlobalUI.minQuestionLength) {
							popUp.questionnairePopUp(GlobalUI.questionLength);
						} else {
							popUp.questionnairePopUp(GlobalUI.completeFields);
						}

					} else {
						addQuestion();
						addLabelsToTable();
						setLabelsToSurvey();
						setEditableValueChoices(false);
						toggleButton();
						updateGraph();
					}

				}
			}
		}

	}

	/*
	Clears the graph of the Panel.
	 */
	public void clearGraph(){
		BorderLayout bl = (BorderLayout) panel.getLayout();
		panel.remove(bl.getLayoutComponent(BorderLayout.CENTER));
		panel.repaint();
		revalidate();
	}

	/**
	 * Updates the Graph with updated Values.
	 */
	public void updateGraph() {
		GraphReport newGraph = new GraphReport(textQuestion.getText());
		JPanel newGraphPanel = newGraph.createPiePanel();
		BorderLayout bl = (BorderLayout) panel.getLayout();
		if(bl.getLayoutComponent(BorderLayout.CENTER) == null){
			revalidate();
		}
		else{
			panel.remove(bl.getLayoutComponent(BorderLayout.CENTER));
			panel.add(newGraphPanel);
			revalidate();
		}

	}

	/**
	 * Toggles and switches the Text from Submit to Clear.
	 */
	public void toggleButton() {
		if (checkTables() == true) {
			editQButton.setText(GlobalUI.clearButtonText);
		} else {
			editQButton.setText(GlobalUI.submitButtonText);
		}
	}

	/*
	Checks for the length of all 5 textfields, check for booleans
	 */
	public boolean checkForCompleteFields() {
		if (textQuestion.getText().length() < GlobalUI.minQuestionLength) {
			return false;
		}
		int a = textValue1.getText().length();
		int b = textValue2.getText().length();
		int c = textValue3.getText().length();
		int d = textValue4.getText().length();
		int e = textValue5.getText().length();
		if ((a == 0) || (b == 0) || (c == 0) || (d == 0) || (e == 0)) {
			return false;
		} else {
			return true;
		}

	}

	public void checkForText() {
		if (checkTables() == false) {
			setEditableValueChoices(true);
		} else {
			setEditableValueChoices(false);
		}

	}

	/**
	 * Checks if there is anything in both Tables.
	 * 
	 * @return
	 */
	public boolean checkTables() {
		if (surveyTable.getQuestion() == null) {
			return false;
		} else {
			if (surveyTableLables.getValueLables().size() == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * Clears all of the TextFields.
	 */
	public void clearValueChoices() {
		textQuestion.setText(GlobalUI.CLEAR);
		textValue1.setText(GlobalUI.CLEAR);
		textValue2.setText(GlobalUI.CLEAR);
		textValue3.setText(GlobalUI.CLEAR);
		textValue4.setText(GlobalUI.CLEAR);
		textValue5.setText(GlobalUI.CLEAR);
	}

	/**
	 * Clears the Question and Labels from their respective Tables.
	 */
	public void clearQAndLabels() {
		surveyTable.removeAll();
		surveyTableLables.clear();

	}

	/**
	 * Adds the Question into the DB.
	 */
	public void addQuestion() {
		manager.removeAll();
		String question = textQuestion.getText();
		manager.addQuestion(question, startDate);
		textQuestion.setText(question);
	}

	/**
	 * Adds the Labels from the TextFields into the DB.
	 */
	public void addLabelsToTable() {
		surveyTableLables.clear();
		surveyTableLables.addResults(getSurveyLabelsFromTextField());
	}

	/**
	 * Sets the TextFields to be either noneditable or editable.
	 * 
	 * @param flag
	 */
	public void setEditableValueChoices(boolean flag) {
		textQuestion.setEditable(flag);
		textValue1.setEditable(flag);
		textValue2.setEditable(flag);
		textValue3.setEditable(flag);
		textValue4.setEditable(flag);
		textValue5.setEditable(flag);
	}

	/**
	 * Sets the Labels from the QuestionnairePanel's TextField to the
	 * RadioButtons in the StudentSurveyPanel.
	 */
	public void setLabelsToSurvey() {
		ArrayList<String> Labels = getSurveyLabelsFromTextField();
		studentSurveyPanel.setButtonValue(GlobalUI.responseOne, Labels.get(0));
		studentSurveyPanel.setButtonValue(GlobalUI.responseTwo, Labels.get(1));
		studentSurveyPanel.setButtonValue(GlobalUI.responseThree, Labels.get(2));
		studentSurveyPanel.setButtonValue(GlobalUI.responseFour, Labels.get(3));
		studentSurveyPanel.setButtonValue(GlobalUI.responseFive, Labels.get(4));
	}

	/**
	 * Gets the Response Labels stored in the surveyTableLabels and sets them to
	 * their respective TextField.
	 */
	public void setResponseLabels() {
		// if there is nothing stored, dont do anything
		if ((surveyTableLables.getValueLables().size() == 0) || (surveyTableLables.getValueLables() == null)) {

		} else {
			ArrayList<String> surveyLabels = surveyTableLables.getValueLables();
			textValue1.setText(surveyLabels.get(0));
			textValue2.setText(surveyLabels.get(1));
			textValue3.setText(surveyLabels.get(2));
			textValue4.setText(surveyLabels.get(3));
			textValue5.setText(surveyLabels.get(4));
		}

	}

	/**
	 * Gets the Survey Response Labels from their respective TextFields.
	 * 
	 * @return
	 */
	public ArrayList<String> getSurveyLabelsFromTextField() {
		ArrayList<String> labels = new ArrayList<String>();
		labels.add(textValue1.getText());
		labels.add(textValue2.getText());
		labels.add(textValue3.getText());
		labels.add(textValue4.getText());
		labels.add(textValue5.getText());
		return labels;

	}

	/**
	 * Gets the Results from the DB.
	 * 
	 * @return
	 */
	public ArrayList<Integer> getResult() {
		ArrayList<Integer> results = new ArrayList<Integer>();
		results = this.manager.getResults();
		return results;

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}


	/*
	Check for keyType events.
	Checks for the source of the EventListener.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		if (textValue1 == e.getSource()) {
			int strlen1 = textValue1.getText().length();
			if (strlen1 >= GlobalUI.truncateLimit) {
				e.consume();
			}
		}
		if (textValue2 == e.getSource()) {
			int strlen2 = textValue2.getText().length();
			if (strlen2 >= GlobalUI.truncateLimit) {
				e.consume();
			}
		}
		if (textValue3 == e.getSource()) {
			int strlen3 = textValue3.getText().length();
			if (strlen3 >= GlobalUI.truncateLimit) {
				e.consume();
			}
		}
		if (textValue4 == e.getSource()) {
			int strlen4 = textValue4.getText().length();
			if (strlen4 >= GlobalUI.truncateLimit) {
				e.consume();
			}
		}
		if (textValue5 == e.getSource()) {
			int strlen5 = textValue5.getText().length();
			if (strlen5 >= GlobalUI.truncateLimit) {
				e.consume();
			}

		}
		if (textQuestion == e.getSource()) {
			if (textQuestion.getText().length() >= GlobalUI.textQuestionLimit) {
				e.consume();
			}
		}

	}
}
