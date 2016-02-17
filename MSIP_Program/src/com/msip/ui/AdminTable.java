package com.msip.ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import com.msip.model.AdminTableModel;

public class AdminTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public AdminTable(AdminTableModel adminModel) {
		super(adminModel);
		
		this.setFillsViewportHeight(true);
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		this.getTableHeader().setFont(GlobalUI.GlobalFont);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setFont(GlobalUI.GlobalFont);
		this.setRowHeight(35);
	}
}
