package com.msip.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.msip.ui.GlobalUI;

public class AdminTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Admin> admins;

	public AdminTableModel(ArrayList<Admin> admins) {
		this.admins = admins;
	}

	@Override
	public int getColumnCount() {
		return GlobalUI.numOfColumnsInAdmin;
	}

	@Override
	public int getRowCount() {
		return (admins == null) ? 0 : admins.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch (columnIndex) {
		case GlobalUI.columnIndexFirstName:
			return admins.get(rowIndex).getFirstName();
		case GlobalUI.columnIndexLastName:
			return admins.get(rowIndex).getLastName();
		case GlobalUI.columnIndexKNumber:
			return admins.get(rowIndex).getkNumber();
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case GlobalUI.columnIndexFirstName:
			return GlobalUI.columnNameFirstName;
		case GlobalUI.columnIndexLastName:
			return  GlobalUI.columnNameLastName;
		case GlobalUI.columnIndexKNumber:
			return  GlobalUI.columnNameKNumber;
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	/**
	 * @return the students
	 */
	public ArrayList<Admin> getAdmins() {
		return admins;
	}

	/**
	 * @param students the students to set
	 */
	public void setAdmins(ArrayList<Admin> admins) {
		this.admins = admins;
		fireTableDataChanged();
		fireTableStructureChanged();
	}
}
