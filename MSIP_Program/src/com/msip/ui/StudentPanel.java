/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.msip.external.Utility;
import com.msip.manager.MISPCore;
import com.msip.model.Student;
import com.msip.model.StudentTableModel;

public class StudentPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	private JButton btnAdd;
	private JButton btnRemove;

	private StudentTableModel studentModel;
	private StudentTable studentTable;
	private JButton btnEdit;
	private Component horizontalStrut_1;
	private AdminToolsPanel adminToolsPanel;
	private JButton btnImport;
	private popUpResponse warning;

	public StudentPanel(MISPCore msipCore, AdminToolsPanel adminToolsPanel) {
		this.setManager(msipCore);
		this.setAdminToolsPanel(adminToolsPanel);

		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		studentModel = new StudentTableModel(msipCore.getStudents(), msipCore);
		studentTable = new StudentTable(studentModel);
		JScrollPane studentScrollPane = new JScrollPane(studentTable);
		add(studentScrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		btnAdd.setFont(GlobalUI.GlobalFont);
		btnAdd.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		btnAdd.addActionListener(this);
		panel.add(btnAdd);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);

		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(this);
		btnRemove.setFont(GlobalUI.GlobalFont);
		btnRemove.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		panel.add(btnRemove);

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(this);
		btnEdit.setFont(GlobalUI.GlobalFont);
		btnEdit.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);
		panel.add(btnEdit);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_2);

		btnImport = new JButton("Import");
		btnImport.setFont(GlobalUI.GlobalFont);
		btnImport.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		btnImport.addActionListener(this);
		panel.add(btnImport);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAdd) {

			StudentAddEditDialog dialog = new StudentAddEditDialog(
					GlobalUI.ADDSTUDENT);
			if (dialog.getResults() == JOptionPane.YES_OPTION) {
				try {
					getManager().addStudent(dialog.getStudent());
					reloadStudentTable();
				} catch (NumberFormatException e1) {
					getAdminToolsPanel().setStatusMsg(
							GlobalUI.PLEASE_SEE_DEVELOPER);
					e1.printStackTrace();
				} catch (SQLException e1) {
					getAdminToolsPanel().setStatusMsg("Could not add Student");
					e1.printStackTrace();
				}
			}
		}

		if (e.getSource() == btnEdit) {

			int rowIndex = studentTable.getSelectedRow();

			// Make sure they made a selection on the table
			if (rowIndex >= 0) {
				rowIndex = studentTable.convertRowIndexToModel(rowIndex);
				// Get the student they selected
				Student studentToEdit = studentModel.getStudents()
						.get(rowIndex);

				StudentAddEditDialog dialog = new StudentAddEditDialog(
						GlobalUI.MODIFYSTUDENT, studentToEdit);
				if (dialog.getResults() == JOptionPane.YES_OPTION) {
					try {
						getManager().modifyStudent(dialog.getStudent());
						reloadStudentTable();
					} catch (Exception e1) {
						getAdminToolsPanel().setStatusMsg(
								"Could not edit student");
						System.out.println("StudentPanel.actionPerformed() "
								+ e1.getMessage());
					}
				}
			} else {
				getAdminToolsPanel().setStatusMsg(GlobalUI.SELECTASTUDENT);
			}
		}

		if (e.getSource() == btnRemove) {
			int rowIndex = studentTable.getSelectedRow();

			// Make sure they made a selection on the table
			if (rowIndex >= 0) {
				rowIndex = studentTable.convertRowIndexToModel(rowIndex);
				// Get the student they selected
				Student studentToDelete = studentModel.getStudents().get(
						rowIndex);
				int selectedValue = JOptionPane.showConfirmDialog(this,
						"All logins of this student will be deleted. Are you sure you want to delete "
								+ studentToDelete.getFullName() + "?",
						"Remove Student from Database",
						JOptionPane.YES_NO_OPTION);

				// If they say yes then delete them!
				if (selectedValue == JOptionPane.YES_OPTION) {
					try {
						getManager()
								.deleteStudent(studentToDelete.getkNumber());
						reloadStudentTable();
					} catch (SQLException e1) {
						getAdminToolsPanel().setStatusMsg(
								"Could not delete student");
						System.out.println("StudentPanel.actionPerformed() "
								+ e1.getMessage());
					}
				}
			} else {
				getAdminToolsPanel().setStatusMsg(GlobalUI.SELECTASTUDENT);
			}
		}

		if (e.getSource() == btnImport) {
			JOptionPane
					.showMessageDialog(
							this,
							"Make sure the CSV File being imported is in the following format.\n"
									+ "Where the...\n"
									+ "first column 	= Last Name\n"
									+ "second column	= First Name\n"
									+ "third column 	= Major\n"
									+ "fourth column 	= K-Number\n\n"
									+ "Example Format:\n"
									+ "Last Name,First Name,Major,K-Number\n"
									+ "Garcia,Miguel,Computer Science,99999999\n"
									+ "Garcia,Miguel,Computer Science,99999999\n"
									+ "...\n\n"
									+ "Duplicate K-Numbers will update Students information");

			final JFileChooser fc = new JFileChooser(
					System.getProperty("file.separator") + "MesaReports");
			fc.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					Utility.ImportStudentsFromCSVFile(getManager(),
							file.getAbsolutePath());
				} catch (IOException | SQLException e1) {
					getAdminToolsPanel().setStatusMsg(
							"There was an eror importing your file");
					e1.printStackTrace();
				}
				getAdminToolsPanel().setStatusMsg(
						"Importing Students into Database");
				reloadStudentTable();
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

	/**
	 * @return the adminToolsPanel
	 */
	public AdminToolsPanel getAdminToolsPanel() {
		return adminToolsPanel;
	}

	/**
	 * @param adminToolsPanel
	 *            the adminToolsPanel to set
	 */
	public void setAdminToolsPanel(AdminToolsPanel adminToolsPanel) {
		this.adminToolsPanel = adminToolsPanel;
	}
}
