package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.PagePreviewPanel;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.util.UUID;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class PagePreviewController implements TreeSelectionListener{
	
	IGallery gallery;
	PagePreviewPanel gui;
	
	public PagePreviewController(IGallery gallery) {
		this.gallery = gallery;
	}
	
	public void setGuiObject(PagePreviewPanel previewPanel){
		this.gui = previewPanel;
	}

	/**
	 * Returns the name of the used resource.
	 * @param selectedPageId
	 * @return
	 */
	public String getSelectedPageResourceName(UUID selectedPageId) {
		return gallery.findPageByID(selectedPageId).getUsedResource().getName();
	}
	
	/**
	 * Return the resource used by the page.
	 * @param selectedPageId
	 * @return
	 */
	public IPageResource getUsedResource(UUID selectedPageId){
		return gallery.findPageByID(selectedPageId).getUsedResource();
	}

	public IPage getSelectedPage(UUID selectedPageId) {
		return gallery.findPageByID(selectedPageId);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree)e.getSource();		
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
    	
		String resourceName;
		int orderNumber;
		
    	if (selectedNode == null){ //No node is selected    		
    		resourceName = "No Page selected...";
    		orderNumber = -1;
    	}else if(selectedNode == (DefaultMutableTreeNode)tree.getModel().getRoot()){ // Root/Gallery node is selected    		
    		resourceName = "No Page selected...";
    		orderNumber = -1;
	    }else{ // a node (page) is selected	    	
	    	UUID selectedPageId = (UUID) selectedNode.getUserObject();
	    	IPage selectedPage = getSelectedPage(selectedPageId);
	    	resourceName = selectedPage.getUsedResource().getName();
	    	orderNumber = selectedPage.getOrderNumber();	    	
	    }	
    	gui.setLblText(resourceName + " Order No. " + orderNumber);
	}
}
