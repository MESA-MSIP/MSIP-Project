package com.msip.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.msip.manager.MISPCore;
import com.msip.model.Student.ParcipitationState;
import com.msip.ui.GlobalUI;

public class StudentTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Student> students;
	private MISPCore manager;

	public StudentTableModel(ArrayList<Student> students, MISPCore manager) {
		this.students = students;
		this.manager = manager;
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
		case GlobalUI.columnIndexLastName:
			return students.get(rowIndex).getLastName();
		case GlobalUI.columnIndexFirstName:
			return students.get(rowIndex).getFirstName();
		case GlobalUI.columnIndexKNumber:
			return students.get(rowIndex).getkNumber();
		case GlobalUI.columnIndexMajor:
			return students.get(rowIndex).getMajor();
		case GlobalUI.columnIndexParticipation:

			int number = students.get(rowIndex).getkNumber();
			ParcipitationState participation = manager.isStudentActive(number);

			if (ParcipitationState.LOW_ACTIVE_STUDENT == participation) {
				return "Low";
			} else if (ParcipitationState.MEDIAN_ACTIVE_STUDENT == participation) {
				return "Medium";
			} else if (ParcipitationState.HIGH_ACTIVE_STUDENT == participation) {
				return "High";
			} else {
				return "";
			}
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case GlobalUI.columnIndexLastName:
			return GlobalUI.columnNameLastName;
		case GlobalUI.columnIndexFirstName:
			return GlobalUI.columnNameFirstName;
		case GlobalUI.columnIndexKNumber:
			return GlobalUI.columnNameKNumber;
		case GlobalUI.columnIndexMajor:
			return GlobalUI.columnNameMajor;
		case GlobalUI.columnIndexParticipation:
			return GlobalUI.columnNameParticipation;
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
	 * @param students
	 *            the students to set
	 */
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
		fireTableDataChanged();
		fireTableStructureChanged();
	}

}
