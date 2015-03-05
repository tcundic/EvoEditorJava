package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.PageDataController;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PageDataPanel extends JPanel {

	/**
	 * Generated Id
	 */
	private static final long serialVersionUID = 834187390425254335L;
	
	public static final int ATTRIBUTE_TABLE = 0;
	public static final int RESOURCE_TABLE = 1;
	
	private PageDataController controller;
	private JTable tablePageAttributes;
	private JTable tablePageResources;
	private DefaultTableModel tblResourcesModel;
	private DefaultTableModel tblAttributesModel;
	JComboBox<String> comboBoxPageResource;
	
	public PageDataPanel(PageDataController controller) {
		this.controller = controller;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel dataTablesPanel = new JPanel();
		add(dataTablesPanel, BorderLayout.CENTER);
		dataTablesPanel.setLayout(new BoxLayout(dataTablesPanel, BoxLayout.Y_AXIS));
		
		tblAttributesModel = getTableModel();
		tblAttributesModel.addColumn("Page attribute name");
		tblAttributesModel.addColumn("Page attribute value");
		tblAttributesModel.addTableModelListener(controller);
		tablePageAttributes = new JTable(tblAttributesModel);
		tablePageAttributes.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		tblResourcesModel = getTableModel();
		tblResourcesModel.addColumn("Resource attribute name");
		tblResourcesModel.addColumn("Resource attribute value");
		tblResourcesModel.addTableModelListener(controller);
		tablePageResources = new JTable(tblResourcesModel);
		tablePageResources.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		
		JScrollPane atributesTblScrollPane = new JScrollPane(tablePageAttributes);
		dataTablesPanel.add(atributesTblScrollPane);
		
		JScrollPane ResourcesTblScrollPane = new JScrollPane(tablePageResources);
		dataTablesPanel.add(ResourcesTblScrollPane);		
		
		JPanel panelPageResourceChooser = new JPanel();
		add(panelPageResourceChooser,BorderLayout.EAST);
		panelPageResourceChooser.setLayout(new BoxLayout(panelPageResourceChooser, BoxLayout.Y_AXIS));
		
		JLabel lblResource = new JLabel("Resource");
		panelPageResourceChooser.add(lblResource);
		
		comboBoxPageResource = new JComboBox<String>();
		comboBoxPageResource.setMaximumSize(comboBoxPageResource.getPreferredSize());
		comboBoxPageResource.addActionListener(controller);
		panelPageResourceChooser.add(comboBoxPageResource);
		
		JButton btnAddPageResource = new JButton(EditorMainGUI.ADD_RESOURCE_BTN_TEXT);
		panelPageResourceChooser.add(btnAddPageResource);
		
		JButton btnSelectPageResource = new JButton(EditorMainGUI.SELECT_RESOURCE_BTN_TEXT);
		panelPageResourceChooser.add(btnSelectPageResource);
		
		JButton btnSavePageData = new JButton(EditorMainGUI.SAVE_PAGE_DATA_BTN_TEXT);
		panelPageResourceChooser.add(btnSavePageData);		
	}
	
	public DefaultTableModel getTableModel(int table){
		DefaultTableModel model;
		switch (table) {
		case ATTRIBUTE_TABLE:
			model = tblAttributesModel;
			break;
		case RESOURCE_TABLE:
			model = tblResourcesModel;
			break;
		default:
			model = null;
			break;
		}
		return model;
	}
	
	/**
	 * Returns the table model that allows only the second column to be edited
	 * @return
	 */
	private DefaultTableModel getTableModel(){
		DefaultTableModel tableModel = new DefaultTableModel(){	        
			private static final long serialVersionUID = -2272342654671031121L;
			
			boolean[] canEdit = new boolean[]{ false, true};

	        public boolean isCellEditable(int rowIndex, int columnIndex) {
	            return canEdit[columnIndex];
	        }
	    };
	    
		return tableModel;
	}
	
	/**
	 * Starts the action the removal of rows in the table.
	 * 
	 */
	public void removeAllRowsFromTable(int table){
		switch (table) {
		case ATTRIBUTE_TABLE:
			removeAllRowsFromTable(tblAttributesModel);
			break;
		case RESOURCE_TABLE:
			removeAllRowsFromTable(tblResourcesModel);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Removes all rows from the table.
	 * @param model Model of the table that need to get its rows removed.
	 */
	private void removeAllRowsFromTable(DefaultTableModel model){
		for(int i = model.getRowCount() - 1; i >=0; i--){
			model.removeRow(i);
		}
	}	
	
	
	/**
	 * Starts the action for refreshing the table.
	 * 
	 */
	public void refreshPageTable(int table){
		switch (table) {
		case ATTRIBUTE_TABLE:
			refreshPageTable(tblAttributesModel);
			break;
		case RESOURCE_TABLE:
			refreshPageTable(tblResourcesModel);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Refreshes the table of the given model.
	 * @param model
	 */
	private void refreshPageTable(DefaultTableModel model){
		model.fireTableDataChanged();
	}
	
	/**
	 * Starts the action for adding a new row to table.
	 * 
	 */
	public void addRowToTable(int table, Object[] dataRow){
		switch (table) {
		case ATTRIBUTE_TABLE:
			addRowToTable(tblAttributesModel, dataRow);
			break;
		case RESOURCE_TABLE:
			addRowToTable(tblResourcesModel, dataRow);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Adds a row to a table with the given model.
	 * @param model
	 * @param dataRow
	 */
	private void addRowToTable(DefaultTableModel model, Object[] dataRow){
		model.addRow(dataRow);
	}

	public void setResourceOptions(ArrayList<String> resourceOptions, int selectedIndex) {
		comboBoxPageResource.removeAllItems();
		for(String resourceName : resourceOptions){
			comboBoxPageResource.addItem(resourceName);	
		}
		comboBoxPageResource.setSelectedIndex(selectedIndex);
		comboBoxPageResource.setMaximumSize(comboBoxPageResource.getPreferredSize());
	}

	public boolean isSomeCellSelected() {
		return tablePageAttributes.hasFocus() || tablePageResources.hasFocus();		
	}	
}
