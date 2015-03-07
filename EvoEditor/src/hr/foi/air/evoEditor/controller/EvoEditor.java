package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;

import java.util.ArrayList;
import java.util.UUID;

public class EvoEditor {
	private IGallery gallery;
	private GalleryDataPanelController galleryDataPanelController;
	private PagePreviewController pagePreviewController;
	private GalleryTreeController galleryTreePanelController;
	private PageDataController pageDataController;

	public EvoEditor(IGallery galleryFormat) {
		this.gallery = galleryFormat;
		this.galleryDataPanelController = new GalleryDataPanelController(gallery);
		this.galleryTreePanelController = new GalleryTreeController(gallery);
		this.pagePreviewController = new PagePreviewController(gallery);
		this.pageDataController = new PageDataController(gallery);
	}
	
	public PageDataController getPageDataController(){
		return this.pageDataController;
	}
	
	public PagePreviewController getPagePreviewController() {
		return this.pagePreviewController;
	}

	public GalleryDataPanelController getGalleryDataController(){
		return this.galleryDataPanelController;
	}

	public GalleryTreeController getGalleryTreePanelController() {
		return this.galleryTreePanelController;
	}

	public UUID getGalleryID() {
		return this.gallery.getID();
	}

	public ArrayList<IPage> getChildPages(UUID parentId) {
		return gallery.getChildPageList(parentId);
	}
}
