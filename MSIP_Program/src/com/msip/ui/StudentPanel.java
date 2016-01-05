/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.msip.manager.MISPCore;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;

public class StudentPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	private JButton btnAdd;
	private JButton btnRemove;

	public StudentPanel(MISPCore msipCore) {
		this.setManager(msipCore);

		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		StudentTable studentTable = new StudentTable(getManager().getStudents());
		JScrollPane studentScrollPane = new JScrollPane(studentTable);
		add(studentScrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		panel.add(btnAdd);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);

		btnRemove = new JButton("Remove");
		panel.add(btnRemove);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAdd) {

		}
		
		if (e.getSource() == btnRemove) {

		}

	}

	/**
	 * @return the manager
	 */
	public MISPCore getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(MISPCore manager) {
		this.manager = manager;
	}
}
