package com.msip.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class popUpResponse extends JFrame {

	public int popUp() {
		JFrame frame = new JFrame();
		frame.setSize(400, 240);
		Object[] options = { "Student", "Admin", "Neither, Log Out" };
		int n = JOptionPane.showOptionDialog(frame, "How Do you Want to Login As?: ",
				"MESSAGE", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[2]);
		return n;
	}
	
	public int shutDownPopUp(){
		String message = "Are you sure you want to shutdown?" 
	     + "\n" + "This action will shutdown your system so you can safely switch off power.";
		JFrame frame = new JFrame();
		frame.setSize(400, 240);
		Object[] options = { "Ok", "Cancel" };
		int n = JOptionPane.showOptionDialog(frame, message,
				"Shut Down", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[1]);
		return n;
	}
	public void questionnairePopUp(String message){
		JFrame frame = new JFrame();
		frame.setSize(400, 240);
		Object[] option = {"Ok"};
		JOptionPane.showMessageDialog(frame, message);
		
	}
}
