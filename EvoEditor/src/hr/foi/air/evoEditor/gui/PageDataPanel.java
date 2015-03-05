package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.PageDataController;

import java.awt.BorderLayout;

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
		
		tblAttributesModel = new DefaultTableModel();
		tblAttributesModel.addColumn("Page attribute name");
		tblAttributesModel.addColumn("Page attribute value");
		tablePageAttributes = new JTable(tblAttributesModel);
		
		tblResourcesModel = new DefaultTableModel();
		tblResourcesModel.addColumn("Resource attribute name");
		tblResourcesModel.addColumn("Resource attribute value");
		tablePageResources = new JTable(tblResourcesModel);
		
		
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
		panelPageResourceChooser.add(comboBoxPageResource);
		
		JButton btnAddPageResource = new JButton(EditorMainGUI.ADD_RESOURCE_BTN_TEXT);
		panelPageResourceChooser.add(btnAddPageResource);
		
		JButton btnSelectPageResource = new JButton(EditorMainGUI.SELECT_RESOURCE_BTN_TEXT);
		panelPageResourceChooser.add(btnSelectPageResource);
		
		JButton btnSavePageData = new JButton(EditorMainGUI.SAVE_PAGE_DATA_BTN_TEXT);
		panelPageResourceChooser.add(btnSavePageData);		
	}
	
	public void removeAllRowsFromAttributeTable(){
		for(int i = 1; i < tblAttributesModel.getRowCount(); i++){
			tblAttributesModel.removeRow(i);
		}
	}
	
	public void removeAllRowsFromResourceTable(){
		for(int i = 1; i < tblResourcesModel.getRowCount(); i++){
			tblResourcesModel.removeRow(i);
		}
	}

}
