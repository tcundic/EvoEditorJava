package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.events.PageChangeListener;
import hr.foi.air.evoEditor.gui.PageDataPanel;
import hr.foi.air.evoEditor.main.Main;
import hr.foi.air.evoEditor.model.EvoAttribute;
import hr.foi.air.evoEditor.model.EvoTreeNodeObject;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This is controller class for pages attributes panel.
 */

public class PageDataController implements TreeSelectionListener, ActionListener, TableModelListener{
	
	private static final int RESOURCE_CONTENT_COLUMN = 1;
	private static final int IS_USED_COLUMN = 0;
	private static final int NAME_COLUMN = 1;
	private static final int VALUE_COLUMN = 2;
	
	private IGallery gallery;
	private PageDataPanel gui;
	
	private IPage selectedPage;
	private boolean active = true; 
	
	private ArrayList<PageChangeListener> listeners = new ArrayList<PageChangeListener>();

    public void addListener(PageChangeListener toAdd) {
        listeners.add(toAdd);
    }

	public PageDataController(IGallery gallery) {
		this.gallery = gallery;
	}
	
	public void setGui(PageDataPanel gui){
		this.gui = gui;
	}

	public void setActive(boolean active){
        this.active = active;
    }

	/**
	 * Calls the action to populate the combobox with the possible page
	 * resource options.
	 */
	private void setResourceOptions() {
		if(selectedPage != null){
			setActive(false);
			ArrayList<String> resourceOptions = new ArrayList<String>();
			int selectedIndex=0;
			for(IPageResource resource : selectedPage.getPageResources()){
				resourceOptions.add(resource.getName());
				if(resource.isUsed()){
					selectedIndex = resourceOptions.size() - 1;
				}
			}
			gui.setResourceOptions(resourceOptions, selectedIndex);
			setActive(true);
		}		
	}

    /**
     * If resource can have content this method add
     * row "Content" to attributes table.
     */
	private void refreshResourceContentTable(){
		setActive(false);
		gui.removeAllRowsFromTable(PageDataPanel.RESOURCE_CONTENT_TABLE);
		if(selectedPage != null){
			IPageResource pageResource = selectedPage.getUsedResource();
			// only one resource can be selected.. for now
			if(pageResource.canHaveContent()){
				Object[] dataRow = new Object[]{
						pageResource.getName(), 
						pageResource.getContent()
					};
				gui.addRowToTable(PageDataPanel.RESOURCE_CONTENT_TABLE, dataRow);
			}
		}
		setActive(true);
	}

    /**
     * Populate resource attributes table with
     * attributes that used resource have.
     */
	private void refreshPageResourceTable() {
		setActive(false);
		gui.removeAllRowsFromTable(PageDataPanel.RESOURCE_TABLE);
		if(selectedPage != null){
			IPageResource pageResource = selectedPage.getUsedResource();
			for(EvoAttribute attribute : pageResource.getAttributeSet()){
				Object[] dataRow = new Object[]{
						attribute.isUsed(),
						attribute.getAttributeName(),
						attribute.getAttributeValue()
						};
				gui.addRowToTable(PageDataPanel.RESOURCE_TABLE, dataRow);
			}		
		}
		setActive(true);
	}

    /**
     * Populate page attributes table with attributes that
     * page have.
     */
	private void refreshPageAttributeTable() {
		setActive(false);
		gui.removeAllRowsFromTable(PageDataPanel.ATTRIBUTE_TABLE);
		if(selectedPage != null){
			for(EvoAttribute attribute : selectedPage.getPageAttributeSet()){
				Object[] dataRow = new Object[]{
						attribute.isUsed(),
						attribute.getAttributeName(),
						attribute.getAttributeValue()
						};
				gui.addRowToTable(PageDataPanel.ATTRIBUTE_TABLE, dataRow);
			}
		}
		setActive(true);
	}

	/**
	 * Happens when a node is selected in the tree. Repopulates page data tables.
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if(active){
			setActive(false);
			JTree tree = (JTree)e.getSource();		
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			if(selectedNode != null){
				EvoTreeNodeObject nodeObject = (EvoTreeNodeObject) selectedNode.getUserObject();
				selectedPage = gallery.findPageByID(nodeObject.getObjectId());
			}else{
				selectedPage = null;
			}
			
			if(selectedPage != null){
				makePanelVisible(true);
				refreshPageAttributeTable();
				refreshPageResourceTable();
				refreshResourceContentTable();
				setResourceOptions();
			}else{
				makePanelVisible(false);
			}
			setActive(true);
		}
	}

    /**
     * Hide page data panel when no page is selected, and
     * show when user select page.
     * @param isVisible
     */
	private void makePanelVisible(boolean isVisible) {
		gui.makePanelVisible(isVisible);
	}

	/**
	 * Does work when user add resource to page.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(active && selectedPage != null && e.getSource() instanceof JComboBox<?>){
			setActive(false);
			@SuppressWarnings("unchecked")
			JComboBox<String> cmb = (JComboBox<String>)e.getSource();
			String resource = (String) cmb.getSelectedItem();
			selectedPage.usePageResource(resource);
			refreshPageResourceTable();
			refreshResourceContentTable();
			tablesChanged();
			setActive(true);
		}
		if(active && selectedPage != null && e.getSource() instanceof JButton){
			JButton button = (JButton)e.getSource();
			switch (button.getText()) {
			case PageDataPanel.ADD_RESOURCE_BTN_TEXT:
				addResourceButtonClicked();
				break;
			default:
				break;
			}
		}
	}

    /**
     * Does work when user choose resource file.
     */
	private void addResourceButtonClicked() {
		IPageResource resource = selectedPage.getUsedResource();
		if(resource.containsExternalFile()){
			JFileChooser chooser = new JFileChooser();
			String description = "";
			String[] possibleExtensions = resource.getAcceptableFileExtensions();
			for(String extension : possibleExtensions){
				description += extension;
				description += " ";
			}
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(description, possibleExtensions);		    	   
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	setResourcePathAttributeToUsed(selectedPage.getUsedResource());
		    	setResourcePath(chooser.getSelectedFile().getAbsolutePath());
		    	refreshPageResourceTable();
				refreshResourceContentTable();
				tablesChanged();
		    }
		}else{
			JOptionPane.showMessageDialog(gui, "This resource requires no file.");
		}
	}

	private void refreshPageData(){
		refreshPageAttributeTable();
		refreshPageResourceTable();
		refreshResourceContentTable();
		tablesChanged();
	}

	private void setResourcePathAttributeToUsed(IPageResource usedResource) {
		EvoAttribute pathAttribute = usedResource.getAttributeByName(Main.PATH_RESOURCE_ATTRIBUTE_NAME);
		pathAttribute.setUsed(true);		
	}

	private void setResourcePath(String absolutePath) {
		IPageResource resource = selectedPage.getUsedResource();
		EvoAttribute attribute = resource.getAttributeByName(resource.getExternalFileLocationAttributeName());
		attribute.setAttributeValue(absolutePath);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		if(active){
			savePageAttributeData(gui.getTableModel(PageDataPanel.ATTRIBUTE_TABLE));
			savePageResourceData(gui.getTableModel(PageDataPanel.RESOURCE_TABLE));
			saveResourceContentData(gui.getTableModel(PageDataPanel.RESOURCE_CONTENT_TABLE));
			tablesChanged();
		}		
	}

    /**
     * Save content attribute to resource if it has one.
     * @param tableModel
     */
	private void saveResourceContentData(DefaultTableModel tableModel) {
		if(selectedPage != null){
			//Page can use only one resource.. for now
			IPageResource resource = selectedPage.getUsedResource();
			int rowCount = tableModel.getRowCount();
			for(int i = 0; i < rowCount; i++){
				String contentText = (String)tableModel.getValueAt(i, RESOURCE_CONTENT_COLUMN);				
				resource.setContent(contentText);			
			}
		}
		
	}

    /**
     * Save resource attributes.
     * @param tableModel
     */
	private void savePageResourceData(DefaultTableModel tableModel) {
		if(selectedPage != null){
			IPageResource resource = selectedPage.getUsedResource();
			EvoAttribute resourceAttribute;
			int rowCount = tableModel.getRowCount();
			for(int i = 0; i < rowCount; i++){
				boolean isAttributeUsed = (boolean)tableModel.getValueAt(i, IS_USED_COLUMN);
				String attributeName = (String)tableModel.getValueAt(i, NAME_COLUMN);
				String attributeValue = (String)tableModel.getValueAt(i, VALUE_COLUMN);				
				resourceAttribute = resource.getAttributeByName(attributeName);
				resourceAttribute.setAttributeName(attributeName);
				resourceAttribute.setAttributeValue(attributeValue);
				resourceAttribute.setUsed(isAttributeUsed);
			}
		}	
	}

    /**
     * Save page attributes.
     * @param tableModel
     */
	private void savePageAttributeData(DefaultTableModel tableModel) {
		if(selectedPage != null){
			EvoAttribute pageAttribute;
			int rowCount = tableModel.getRowCount();
			for(int i = 0; i < rowCount; i++){
				boolean isAttributeUsed = (boolean)tableModel.getValueAt(i, IS_USED_COLUMN);
				String attributeName = (String)tableModel.getValueAt(i, NAME_COLUMN);
				String attributeValue = (String)tableModel.getValueAt(i, VALUE_COLUMN);
				pageAttribute = selectedPage.getPageAttribute(attributeName);
				pageAttribute.setAttributeName(attributeName);
				pageAttribute.setAttributeValue(attributeValue);
				pageAttribute.setUsed(isAttributeUsed);
			}	
		}			
	}
	
	private void tablesChanged(){
		for(PageChangeListener pl : listeners){
			pl.pageDataChanged();
		}
	}

	public void addTableChangeListener(PageChangeListener pagePreviewController) {
		listeners.add(pagePreviewController);		
	}
}
