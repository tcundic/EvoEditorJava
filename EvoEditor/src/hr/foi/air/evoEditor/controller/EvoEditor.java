package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.EditorMainGUI;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.util.ArrayList;
import java.util.UUID;

public class EvoEditor {
	
	EditorMainGUI gui;
	IGallery gallery;
	
	private static final String PAGE_NOT_SELECTED = "Page not selected..";

	public EvoEditor(IGallery galleryFormat) {
		this.gallery = galleryFormat;
	}

	public void setGUIObject(EditorMainGUI gui) {
		this.gui = gui;		
	}

	public UUID getGalleryID() {
		return this.gallery.getID();
	}

	public ArrayList<IPage> getChildPages(UUID parentId) {
		return gallery.getChildPageList(parentId);
	}

	public void btnAddPageClicked() {		
		UUID childId = gallery.addBlankPage(gallery.getID());
		gui.addNodeToTree(gallery.getID(), childId);
	}

	public void btnAddSubpageClicked(UUID parentId) {
		UUID childId = gallery.addBlankPage(parentId);
		gui.addNodeToTree(parentId, childId);
	}

	public void btnBtnMoveUpClicked(UUID selectedPageId) {
		if(selectedPageId == null){
			return;
		}
		IPage page = gallery.findPageByID(selectedPageId);
		if(page != null){
			gallery.increaseOrderNumber(selectedPageId);
			gui.reattachNodes(page.getParentID(), gallery.getChildPageList(page.getParentID()));
		}	
	}

	public void btnMoveDownClicked(UUID selectedPageId) {
		if(selectedPageId != null){
			
		}
		IPage page = gallery.findPageByID(selectedPageId);
		if(page != null){
			gallery.decreaseOrderNumber(selectedPageId);
			gui.reattachNodes(page.getParentID(), gallery.getChildPageList(page.getParentID()));
		}			
	}

	public void btnDeletePageClicked(UUID selectedPageId) {
		if(selectedPageId != null){
			gallery.deletePage(selectedPageId);
			gui.removePageFromTree(selectedPageId);	
		}
	}

	public String getSelectedPageResourceName(UUID selectedPageId) {
		IPage page = gallery.findPageByID(selectedPageId);
		String resourceName = PAGE_NOT_SELECTED;
		
		if(page != null){			
			ArrayList<IPageResource> resurces = page.getPageResources();
			for(IPageResource resource : resurces){
				if(resource.isUsed()){
					resourceName = resource.getName();
				}
			}			
		}
		return resourceName;
	}

	public int getSelectedPageOrderNumber(UUID selectedPageId) {
		IPage page = gallery.findPageByID(selectedPageId);
		int orderNumber = -1;
		if(page != null){
			orderNumber = page.getOrderNumber();
		}
		return orderNumber;
	}

}
