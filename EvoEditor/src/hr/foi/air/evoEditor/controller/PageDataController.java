package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.PageDataPanel;
import hr.foi.air.evoEditor.model.interfaces.IGallery;

public class PageDataController {
	
	private IGallery gallery;
	private PageDataPanel gui;

	public PageDataController(IGallery gallery) {
		this.gallery = gallery;
	}
	
	public void setGui(PageDataPanel gui){
		this.gui = gui;
	}

}
