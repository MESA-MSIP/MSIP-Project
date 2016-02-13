package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.msip.external.Utility;
import com.msip.model.Student;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class StudentAddEditDialog extends JDialog implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JComboBox<String> cmboBoxMajor;
	private JTextField textFieldKNumber;
	private JButton okButton;
	private int results;

	/**
	 * Create the dialog.
	 * 
	 * @wbp.parser.constructor
	 */
	public StudentAddEditDialog(String title) {

		setupUI(title);
		setVisible(true);
	}

	public StudentAddEditDialog(String title, Student studentToEdit) {

		setupUI(title);
		setFields(studentToEdit);
		setVisible(true);
	}

	/**
	 * @param title
	 */
	private void setupUI(String title) {
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);

		setBounds(100, 100, 450, 251);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setFont(GlobalUI.LableFont);
		lblFirstName.setBounds(25, 14, 80, 14);
		contentPanel.add(lblFirstName);

		textFieldFirstName = new JTextField();
		textFieldFirstName.setFont(GlobalUI.TextFieldFont);
		textFieldFirstName.addKeyListener(this);
		textFieldFirstName.setBounds(105, 11, 319, 25);
		contentPanel.add(textFieldFirstName);
		textFieldFirstName.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(GlobalUI.LableFont);
		lblLastName.setBounds(25, 54, 80, 14);
		contentPanel.add(lblLastName);

		textFieldLastName = new JTextField();
		textFieldLastName.setFont(GlobalUI.TextFieldFont);
		textFieldLastName.setBounds(105, 49, 319, 25);
		textFieldLastName.addKeyListener(this);
		contentPanel.add(textFieldLastName);
		textFieldLastName.setColumns(10);

		JLabel lblKnumber = new JLabel("K - Number:");
		lblKnumber.setFont(GlobalUI.LableFont);
		lblKnumber.setBounds(25, 94, 80, 14);
		contentPanel.add(lblKnumber);

		JLabel lblMajor = new JLabel("Major:");
		lblMajor.setFont(GlobalUI.LableFont);
		lblMajor.setBounds(25, 134, 80, 14);
		contentPanel.add(lblMajor);

		cmboBoxMajor = new JComboBox<String>(GlobalUI.getListMajors());
		cmboBoxMajor.setFont(GlobalUI.TextFieldFont);
		cmboBoxMajor.setBounds(105, 131, 319, 25);
		contentPanel.add(cmboBoxMajor);

		textFieldKNumber = new JTextField();
		textFieldKNumber.setFont(GlobalUI.TextFieldFont);
		textFieldKNumber.setBounds(105, 91, 319, 25);
		textFieldKNumber.addKeyListener(this);
		contentPanel.add(textFieldKNumber);
		textFieldKNumber.setColumns(10);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.setPreferredSize(GlobalUI.ButtonDimenesions);
		okButton.addActionListener(this);
		okButton.setEnabled(false);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(GlobalUI.ButtonDimenesions);
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);

		textFieldKNumber.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldKNumber.getText().length() >= 8) {
					e.consume();
				}
				char keychar = e.getKeyChar();
				if ((!Character.isDigit(keychar)) && (keychar != '\b') && (keychar != '')) {
					e.consume();
				}
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (okButton == e.getSource()) {
			setResults(JOptionPane.YES_OPTION);
		} else {

			setResults(JOptionPane.NO_OPTION);
		}
		this.dispose();
	}

	/**
	 * Sets the fields in the dialog
	 * 
	 * @param student
	 */
	public void setFields(Student student) {
		textFieldFirstName.setText(student.getFirstName());
		textFieldLastName.setText(student.getLastName());
		textFieldKNumber.setText(String.valueOf(student.getkNumber()));
		cmboBoxMajor.setSelectedIndex((getIndexfromMajorComboBox(student.getMajor())));

		// Check to see if valid fields to enable OK button
		if (textFieldFirstName.getText().isEmpty() || textFieldLastName.getText().isEmpty()
				|| (!Utility.isValidKNumberLength(textFieldKNumber))) {
			okButton.setEnabled(false);
		} else {
			okButton.setEnabled(true);
		}
	}

	private int getIndexfromMajorComboBox(String major) {
		int results = 0;

		String[] majors = GlobalUI.getListMajors();
		for (int i = 0; i < majors.length; i++) {
			if (majors[i].compareTo(major) == 0) {
				return i;
			}
		}
		return results;
	}

	public Student getStudent() throws NumberFormatException {
		return new Student(textFieldFirstName.getText(), textFieldLastName.getText(),
				Integer.parseInt(textFieldKNumber.getText()), (String) cmboBoxMajor.getModel().getSelectedItem());
	}

	/**
	 * @return the results
	 */
	public int getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	private void setResults(int results) {
		this.results = results;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (textFieldFirstName.getText().isEmpty() || textFieldLastName.getText().isEmpty()
				|| (!Utility.isValidKNumberLength(textFieldKNumber))) {
			okButton.setEnabled(false);
		} else {
			okButton.setEnabled(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
