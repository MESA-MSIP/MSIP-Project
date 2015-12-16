package com.msip.ui;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminLogin extends JPanel 
{
	private JTextField txtEnterPassword;
	private JTextField txtEnterUsername;
	private JLabel lblMesaIcon;
	public AdminLogin() {
		setBackground(Color.RED);
		setLayout(null);
		
		txtEnterPassword = new JTextField();
		txtEnterPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtEnterPassword.setText("Enter Password:");
		txtEnterPassword.setBounds(119, 181, 196, 39);
		add(txtEnterPassword);
		txtEnterPassword.setColumns(10);
		
		JLabel lblWelcome = DefaultComponentFactory.getInstance().createTitle("Welcome!");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblWelcome.setForeground(Color.BLACK);
		lblWelcome.setBounds(119, 61, 196, 56);
		add(lblWelcome);
		
		txtEnterUsername = new JTextField();
		txtEnterUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtEnterUsername.setText("Enter Username:\r\n");
		txtEnterUsername.setBounds(119, 130, 196, 39);
		add(txtEnterUsername);
		txtEnterUsername.setColumns(10);
		
		lblMesaIcon = new JLabel("MESA ICON");
		lblMesaIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblMesaIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblMesaIcon.setBounds(353, 0, 97, 62);
		add(lblMesaIcon);
	}
}
