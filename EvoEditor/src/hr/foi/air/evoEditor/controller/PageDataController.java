package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.PageDataPanel;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class PageDataController implements TreeSelectionListener, ActionListener, TableModelListener{
	
	private static final int NAME_COLUMN = 0;
	private static final int VALUE_COLUMN = 1;
	private static final String CONTENT_NAME = "Content";
	
	private IGallery gallery;
	private PageDataPanel gui;
	
	private IPage page;
	private boolean active = true;    

	public PageDataController(IGallery gallery) {
		this.gallery = gallery;
	}
	
	public void setGui(PageDataPanel gui){
		this.gui = gui;
	}

	public void setActive(boolean active){
        this.active = active;
    }

	private void setResourceOptions() {
		if(page != null){
			setActive(false);
			ArrayList<String> resourceOptions = new ArrayList<String>();
			int selectedIndex=0;
			for(IPageResource resource : page.getPageResources()){
				resourceOptions.add(resource.getName());
				if(resource.isUsed()){
					selectedIndex = resourceOptions.size() - 1;
				}
			}
			gui.setResourceOptions(resourceOptions, selectedIndex);
			setActive(true);
		}		
	}

	private void refreshPageResourceTable() {
		setActive(false);
		gui.removeAllRowsFromTable(PageDataPanel.RESOURCE_TABLE);
		if(page != null){
			IPageResource pageResource = page.getUsedResource();
			for(String attributeName : pageResource.getAttributeSet()){
				Object[] dataRow = new Object[]{
										attributeName, 
										pageResource.getAttributeValue(attributeName)
									};
				gui.addRowToTable(PageDataPanel.RESOURCE_TABLE, dataRow);
			}
			if(pageResource.canHaveContent()){
				Object[] dataRow = new Object[]{
						CONTENT_NAME, 
						pageResource.getContent()
					};
				gui.addRowToTable(PageDataPanel.RESOURCE_TABLE, dataRow);
			}
		}
		setActive(true);
	}

	private void refreshPageAttributeTable() {
		setActive(false);
		gui.removeAllRowsFromTable(PageDataPanel.ATTRIBUTE_TABLE);
		if(page != null){
			for(String attributeName : page.getPageAttributeSet()){
				Object[] dataRow = new Object[]{
										attributeName, 
										page.getPageAttribute(attributeName)
									};
				gui.addRowToTable(PageDataPanel.ATTRIBUTE_TABLE, dataRow);
			}
		}
		setActive(true);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if(active){
			setActive(false);
			JTree tree = (JTree)e.getSource();		
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			if(selectedNode != null){
				page = gallery.findPageByID((UUID) selectedNode.getUserObject());
			}else{
				page = null;
			}
			refreshPageAttributeTable();
			refreshPageResourceTable();
			setResourceOptions();
			setActive(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(active && page != null && e.getSource() instanceof JComboBox<?>){
			setActive(false);
			@SuppressWarnings("unchecked")
			JComboBox<String> cmb = (JComboBox<String>)e.getSource();
			String resource = (String) cmb.getSelectedItem();
			page.usePageResource(resource);
			refreshPageResourceTable();
			setActive(true);
		}		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		if(active){
				savePageAttributeData(gui.getTableModel(PageDataPanel.ATTRIBUTE_TABLE));
				savePageResourceData(gui.getTableModel(PageDataPanel.RESOURCE_TABLE));
		}		
	}

	private void savePageResourceData(DefaultTableModel tableModel) {
		if(page != null){
			IPageResource resource = page.getUsedResource();
			int rowCount = tableModel.getRowCount();
			for(int i = 0; i < rowCount; i++){
				String attributeName = (String)tableModel.getValueAt(i, NAME_COLUMN);
				String attributeValue = (String)tableModel.getValueAt(i, VALUE_COLUMN);
				if(attributeName.equalsIgnoreCase(CONTENT_NAME)){
					resource.setContent(attributeValue);
				}else{
					resource.setAttributeValue(attributeName, attributeValue);
				}				
			}
		}	
	}

	private void savePageAttributeData(DefaultTableModel tableModel) {
		if(page != null){
			int rowCount = tableModel.getRowCount();
			for(int i = 0; i < rowCount; i++){
				String attributeName = (String)tableModel.getValueAt(i, NAME_COLUMN);
				String attributeValue = (String)tableModel.getValueAt(i, VALUE_COLUMN);
				page.setPageAttribute(attributeName, attributeValue);
			}	
		}			
	}
}
