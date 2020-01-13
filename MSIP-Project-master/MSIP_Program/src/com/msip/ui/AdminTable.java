package com.msip.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
		
		//Sort First Column
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.getModel());
		this.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(GlobalUI.columnIndexLastName, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}
}
