package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.EditorMainGUI;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.util.ArrayList;
import java.util.UUID;

public class EvoEditor {
	
	private EditorMainGUI gui;
	private IGallery gallery;
	private GalleryDataPanelController galleryDataPanelController;
	private PagePreviewController pagePreviewController;
	private GalleryTreeController galleryTreePanelController;
	private PageDataController pageDataController;
	
	
	private static final String PAGE_NOT_SELECTED = "Page not selected..";

	public EvoEditor(IGallery galleryFormat) {
		this.gallery = galleryFormat;
		this.galleryDataPanelController = new GalleryDataPanelController(gallery);
		this.pagePreviewController = new PagePreviewController(gallery);
		this.galleryTreePanelController = new GalleryTreeController(gallery);
		this.pageDataController = new PageDataController(gallery);
	}
	
	public PageDataController getPageDataController(){
		return this.pageDataController;
	}
	
	public GalleryDataPanelController getGalleryDataController(){
		return this.galleryDataPanelController;
	}

	public PagePreviewController getPagePreviewController() {
		return this.pagePreviewController;
	}
	
	public GalleryTreeController getGalleryTreePanelController() {
		return this.galleryTreePanelController;
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
