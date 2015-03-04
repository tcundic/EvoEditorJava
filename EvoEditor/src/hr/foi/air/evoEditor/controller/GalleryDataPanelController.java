package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.GalleryDataPanel;
import hr.foi.air.evoEditor.model.interfaces.IGallery;

public class GalleryDataPanelController {
	
	private IGallery gallery;
	private GalleryDataPanel gui;

	public GalleryDataPanelController(IGallery gallery){
		this.gallery = gallery;
	}

	public void setGui(GalleryDataPanel gui) {
		this.gui = gui;
	}

	public void setInitialData() {
		for (String attributeName : gallery.getGalleryAttributeSet()) {
			if (!gui.getUnchangeableElements().contains(attributeName))
				gui.addAttributes(attributeName, gallery.getGalleryAttribute(attributeName));
		}
	}
}
