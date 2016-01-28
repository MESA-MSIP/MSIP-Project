/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.msip.manager.MISPCore;
import com.msip.model.Admin;
import com.msip.model.AdminTableModel;
import com.msip.model.Student;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AdminPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnEdit;
	private Component horizontalStrut_1;

	private AdminTable adminTable;
	private AdminTableModel adminModel;
	private AdminToolsPanel adminToolsPanel;

	public AdminPanel(MISPCore mispCore, AdminToolsPanel adminToolsPanel) {
		this.setManager(mispCore);
		this.setAdminToolsPanel(adminToolsPanel);

		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		adminModel = new AdminTableModel(mispCore.getAdmins());
		adminTable = new AdminTable(adminModel);
		JScrollPane studentScrollPane = new JScrollPane(adminTable);
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
		btnEdit.addActionListener(this);
		btnEdit.setPreferredSize(GlobalUI.ButtonDimenesions);

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);
		panel.add(btnEdit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAdd) {
			AdminAddEditDialog dialog = new AdminAddEditDialog(GlobalUI.ADDADMIN);
			if (dialog.getResults() == JOptionPane.YES_OPTION) {
				try {
					getManager().addAdmin(dialog.getAdmin());
				} catch (NumberFormatException | NoSuchAlgorithmException e1) {
					getAdminToolsPanel().setStatusMsg(GlobalUI.PLEASE_SEE_DEVELOPER);
					e1.printStackTrace();
				} catch (SQLException e1) {
					getAdminToolsPanel().setStatusMsg("Could not add Admin");
					e1.printStackTrace();
				}
				reloadAdminTable();
			}
		}

		if (e.getSource() == btnEdit) {

			int rowIndex = adminTable.getSelectedRow();

			// Make sure they made a selection on the table
			if (rowIndex >= 0) {

				// Get the admin they selected
				Admin adminToEdit = adminModel.getAdmins().get(rowIndex);

				AdminAddEditDialog dialog = new AdminAddEditDialog(GlobalUI.MODIFY_ADMIN, adminToEdit);
				if (dialog.getResults() == JOptionPane.YES_OPTION) {
					try {
						getManager().modifyAdmin(dialog.getAdmin());
					} catch (Exception e1) {
						getAdminToolsPanel().setStatusMsg("Could not edit Admin");
						System.out.println("AdminPanel.actionPerformed() " + e1.getMessage());
					}
					reloadAdminTable();
				}
			} else {
				getAdminToolsPanel().setStatusMsg(GlobalUI.SELECTANADMIN);
			}
		}

		if (e.getSource() == btnRemove) {
			int rowIndex = adminTable.getSelectedRow();

			// Make sure they made a selection on the table
			if (rowIndex >= 0) {

				// Get the admin they selected
				Admin adminToDelete = adminModel.getAdmins().get(rowIndex);
				int selectedValue = JOptionPane.showConfirmDialog(this,
						"Would you like to remove " + adminToDelete.getFullName() + "?", "Remove Student from Database",
						JOptionPane.YES_NO_OPTION);

				// If they say yes then delete them!
				if (selectedValue == JOptionPane.YES_OPTION) {
					try {
						getManager().deleteAdmin(adminToDelete.getkNumber());
					} catch (SQLException e1) {
						getAdminToolsPanel().setStatusMsg("Could not edit Admin");
						System.out.println("AdminPanel.actionPerformed() " + e1.getMessage());
						e1.printStackTrace();
					}
					reloadAdminTable();
				}
			} else {
				getAdminToolsPanel().setStatusMsg(GlobalUI.SELECTANADMIN);
			}
		}
	}

	/**
	 * reload table
	 */
	public void reloadAdminTable() {
		adminModel.setAdmins(getManager().getAdmins());
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
