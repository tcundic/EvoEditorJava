package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.EditorMainGUI;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Here are all panels on UI declared.
 * Application is programmed in such way that every panel
 * is one class, so replacing some element on UI is easy.
 * Just create new class instead of that you want to replace and
 * include it here, and configure it in EditorMainGUI.java class.
 */

public class EvoEditor implements ActionListener {
	private IGallery gallery;
	private XMLGenerator xmlGenerator;

	private GalleryDataPanelController galleryDataPanelController;
	private PagePreviewController pagePreviewController;
	private GalleryTreeController galleryTreePanelController;
	private PageDataController pageDataController;

	public EvoEditor(IGallery galleryFormat) {
		this.gallery = galleryFormat;
		this.xmlGenerator = new XMLGenerator();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String btnText = ((JButton) e.getSource()).getText();
		switch(btnText){
		case(EditorMainGUI.EXPORT_BTN_TEXT):
			exportXmlFIle();
			break;
		}
		
	}

	private void exportXmlFIle() {
		String filePath="";
		JFileChooser c = new JFileChooser();
        int rVal = c.showSaveDialog(null);
	    if(rVal == JFileChooser.APPROVE_OPTION){
	    	filePath = c.getSelectedFile().getPath();
	    	xmlGenerator.setFile(filePath);
	    	xmlGenerator.generateXmlFile(gallery);
	    }
	    if(rVal == JFileChooser.CANCEL_OPTION){
	    	
	    }		
	}
}
