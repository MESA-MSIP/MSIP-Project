/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.msip.manager.MISPCore;
import com.msip.model.Student;
import com.msip.model.StudentTableModel;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;

public class StudentPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	private JButton btnAdd;
	private JButton btnRemove;

	private StudentTableModel studentModel;
	private StudentTable studentTable;
	private JButton btnEdit;
	private Component horizontalStrut_1;

	public StudentPanel(MISPCore msipCore) {
		this.setManager(msipCore);

		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		studentModel = new StudentTableModel(msipCore.getStudents());
		studentTable = new StudentTable(studentModel);
		JScrollPane studentScrollPane = new JScrollPane(studentTable);
		add(studentScrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		btnAdd.setPreferredSize(GlobalUI.ButtonDimenesions);
		btnAdd.addActionListener(this);
		panel.add(btnAdd);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);

		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(this);
		btnRemove.setPreferredSize(GlobalUI.ButtonDimenesions);
		panel.add(btnRemove);

		btnEdit = new JButton("Edit");
		btnRemove.addActionListener(this);
		btnEdit.setPreferredSize(GlobalUI.ButtonDimenesions);

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);
		panel.add(btnEdit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAdd) {

			StudentAddEditDialog dialog = new StudentAddEditDialog(GlobalUI.ADDSTUDENT);
			if (dialog.getResults() == JOptionPane.YES_OPTION) {
				getManager().addStudent(dialog.getStudent());
				reloadStudentTable();
			}
		}

		if (e.getSource() == btnEdit) {
			
			int rowIndex = studentTable.getSelectedRow();
			
			// Make sure they made a selection on the table
			if(rowIndex > 0){
				
				// Get the student they selected
				Student studentToEdit = studentModel.getStudents().get(rowIndex);
				
				StudentAddEditDialog dialog = new StudentAddEditDialog(GlobalUI.MODIFYSTUDENT);
				dialog.setFields(studentToEdit);
				if (dialog.getResults() == JOptionPane.YES_OPTION) {
					getManager().modifyStudent(dialog.getStudent());
					reloadStudentTable();
				}
			}else{
				//TOOD Message to user to select a Student
			}
		}

		if (e.getSource() == btnRemove) {
			int rowIndex = studentTable.getSelectedRow();

			// Make sure they made a selection on the table
			if (rowIndex > 0) {

				// Get the student they selected
				Student studentToDelete = studentModel.getStudents().get(rowIndex);
				int selectedValue = JOptionPane.showConfirmDialog(this,
						"Would you like to remove " + studentToDelete.getFullName() + "?",
						"Remove Student from Database", JOptionPane.YES_NO_OPTION);

				// If they say yes then delete them!
				if (selectedValue == JOptionPane.YES_OPTION) {
					getManager().deleteStudent(studentToDelete.getkNumber());
					reloadStudentTable();
				}
			}else{
				//TOOD Message to user to select a Student
			}
		}
	}

	/**
	 * reload table
	 */
	public void reloadStudentTable() {
		studentModel.setStudents(getManager().getStudents());
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
