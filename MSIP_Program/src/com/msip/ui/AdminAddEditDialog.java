/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.msip.external.Utility;
import com.msip.model.Admin;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;

public class AdminAddEditDialog extends JDialog implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldKNumber;
	private JTextField textFieldPassword;
	private JButton okButton;
	private int results;
	private JTextField textFieldPassword2;

	/**
	 * Create the dialog.
	 * 
	 * @wbp.parser.constructor
	 */
	public AdminAddEditDialog(String title) {

		setupUI(title);
		setVisible(true);
	}

	public AdminAddEditDialog(String title, Admin adminToEdit) {

		setupUI(title);
		setFields(adminToEdit);
		setVisible(true);
	}

	/**
	 * @param title
	 */
	private void setupUI(String title) {
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);

		setBounds(100, 100, 450, 294);
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
		textFieldFirstName.setBounds(115, 11, 309, 25);
		textFieldFirstName.addKeyListener(this);
		contentPanel.add(textFieldFirstName);
		textFieldFirstName.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(GlobalUI.LableFont);
		lblLastName.setBounds(25, 54, 80, 14);
		contentPanel.add(lblLastName);

		textFieldLastName = new JTextField();
		textFieldLastName.setFont(GlobalUI.TextFieldFont);
		textFieldLastName.setBounds(115, 49, 309, 25);
		textFieldLastName.addKeyListener(this);
		contentPanel.add(textFieldLastName);
		textFieldLastName.setColumns(10);

		JLabel lblKnumber = new JLabel("K - Number:");
		lblKnumber.setFont(GlobalUI.LableFont);
		lblKnumber.setBounds(25, 94, 89, 14);
		contentPanel.add(lblKnumber);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(GlobalUI.LableFont);
		lblPassword.setBounds(25, 134, 80, 14);
		contentPanel.add(lblPassword);

		textFieldPassword = new JTextField();
		textFieldPassword.setFont(GlobalUI.TextFieldFont);
		textFieldPassword.setBounds(115, 131, 309, 25);
		textFieldPassword.addKeyListener(this);
		contentPanel.add(textFieldPassword);

		textFieldKNumber = new JTextField();
		textFieldKNumber.setFont(GlobalUI.TextFieldFont);
		textFieldKNumber.setBounds(115, 91, 309, 25);
		textFieldKNumber.addKeyListener(this);
		contentPanel.add(textFieldKNumber);
		textFieldKNumber.setColumns(10);

		JLabel lblPassword2 = new JLabel("<html><p>Re-Type Password</p></html>");
		lblPassword2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword2.setBounds(25, 174, 80, 30);
		contentPanel.add(lblPassword2);

		textFieldPassword2 = new JTextField();
		textFieldPassword2.setFont(GlobalUI.TextFieldFont);
		textFieldPassword2.setBounds(115, 169, 309, 25);
		textFieldPassword2.addKeyListener(this);
		contentPanel.add(textFieldPassword2);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.setPreferredSize(GlobalUI.ButtonDimenesions);
		okButton.setEnabled(false);
		okButton.addActionListener(this);

		JLabel lblMessage = new JLabel("");
		buttonPane.add(lblMessage);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		buttonPane.add(horizontalStrut);
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

			if (!Utility.isValidPasswordFieldsMatch(textFieldPassword, textFieldPassword2)) {
				JOptionPane.showMessageDialog(this, "Passwords do not match.");
				return;
			} else if (!Utility.isValidPasswordStrength(textFieldPassword)) {
				JOptionPane.showMessageDialog(this,
						"Password should contain a charcter, a digit and be at least 8 characters long.");
				return;
			}

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
	public void setFields(Admin admin) {
		// TODO password check not blank, enforce, hash
		textFieldFirstName.setText(admin.getFirstName());
		textFieldLastName.setText(admin.getLastName());
		textFieldKNumber.setText(String.valueOf(admin.getkNumber()));
	}

	public Admin getAdmin() throws NumberFormatException, NoSuchAlgorithmException {
		return new Admin(textFieldFirstName.getText(), textFieldLastName.getText(),
				Integer.parseInt(textFieldKNumber.getText()), Utility.getHashedPassword(textFieldPassword.getText()));
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
				|| textFieldPassword.getText().isEmpty() || textFieldPassword2.getText().isEmpty()
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
