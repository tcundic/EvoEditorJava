package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.EvoEditor;
import hr.foi.air.evoEditor.controller.PageDataController;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class PageDataPanel extends JPanel {

	/**
	 * Generated Id
	 */
	private static final long serialVersionUID = 834187390425254335L;
	
	private PageDataController controller;
	private JTable tablePageAttributesAndResources;
	
	public PageDataPanel(PageDataController controller) {
		this.controller = controller;
		initialize();
	}

	private void initialize() {
		tablePageAttributesAndResources = new JTable();
		add(tablePageAttributesAndResources);
		
		JPanel panelPageResourceChooser = new JPanel();
		add(panelPageResourceChooser);
		panelPageResourceChooser.setLayout(new BoxLayout(panelPageResourceChooser, BoxLayout.Y_AXIS));
		
		JLabel lblResource = new JLabel("Resource");
		panelPageResourceChooser.add(lblResource);
		
		JComboBox comboBoxPageResource = new JComboBox();
		panelPageResourceChooser.add(comboBoxPageResource);
		
		JButton btnAddPageResource = new JButton(EditorMainGUI.ADD_RESOURCE_BTN_TEXT);
		panelPageResourceChooser.add(btnAddPageResource);
		
		JButton btnSelectPageResource = new JButton(EditorMainGUI.SELECT_RESOURCE_BTN_TEXT);
		panelPageResourceChooser.add(btnSelectPageResource);
		
		JButton btnSavePageData = new JButton(EditorMainGUI.SAVE_PAGE_DATA_BTN_TEXT);
		panelPageResourceChooser.add(btnSavePageData);		
	}

}
