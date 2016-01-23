package com.msip.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import com.msip.ui.GlobalUI;

public class StudentTableModel extends AbstractTableModel   {

	private static final long serialVersionUID = 1L;
	private ArrayList<Student> students;

	public StudentTableModel(ArrayList<Student> students) {
		this.students = students;
	}

	@Override
	public int getColumnCount() {
		return GlobalUI.numOfColumnsInStudent;
	}

	@Override
	public int getRowCount() {
		return (students == null) ? 0 : students.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		switch (columnIndex) {
		case GlobalUI.columnIndexFirstName:
			return students.get(rowIndex).getFirstName();
		case GlobalUI.columnIndexLastName:
			return students.get(rowIndex).getLastName();
		case GlobalUI.columnIndexKNumber:
			return students.get(rowIndex).getkNumber();
		case GlobalUI.columnIndexMajor:
			return students.get(rowIndex).getMajor();
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
		case GlobalUI.columnIndexMajor:
			return  GlobalUI.columnNameMajor;
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
	public ArrayList<Student> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
		fireTableDataChanged();
		fireTableStructureChanged();
	}

}
