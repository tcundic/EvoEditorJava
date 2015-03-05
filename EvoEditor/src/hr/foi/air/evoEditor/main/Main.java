package hr.foi.air.evoEditor.main;

import hr.foi.air.evoEditor.controller.EvoEditor;
import hr.foi.air.evoEditor.gui.EditorMainGUI;
import hr.foi.air.evoEditor.model.RawGallery;
import hr.foi.air.evoEditor.model.RawPage;
import hr.foi.air.evoEditor.model.RawPageResource;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.UIManager;

public class Main {
	
	public static final String DESCRIPTION = "description";
	public static final String CONFIRMATION_TEXT = "confirmationText";
	
	public static final String[] GALLERY_ATTRIBUTES = {"name", "qrcode", "repeat", "showIndicator", "transparency"};
	
	public static final String IMAGE_RESOURCE_NAME = "image";
	public static final String VIDEO_RESOURCE_NAME = "video";
	public static final String TEXT_RESOURCE_NAME = "text";
	
	public static final String PATH_RESOURCE_ATTRIBUTE = "path";
	

	public static void main(String[] args) {
		
		ArrayList<IPageResource> pageResourceFormat = getPageResourceFormat();
		IPage pageFormat = getPageFormat(pageResourceFormat);
		IGallery galleryFormat = getGalleryFormat(pageFormat);
		
		final EvoEditor evoEditor = new EvoEditor(galleryFormat);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					EditorMainGUI window = new EditorMainGUI(evoEditor);
					evoEditor.setGUIObject(window);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Builds a resource list a page can use.
	 * @return
	 */
	private static ArrayList<IPageResource> getPageResourceFormat() {
		ArrayList<String> imageVideoResourceAttributes = new ArrayList<String>(1);
		imageVideoResourceAttributes.add(PATH_RESOURCE_ATTRIBUTE);
		
		IPageResource image = new RawPageResource();
    	image.setName(IMAGE_RESOURCE_NAME);
    	image.setDefaultlyUsed(true);
    	image.setCanHaveContent(false);
    	image.setPossibleAttributes(imageVideoResourceAttributes);
    	
    	IPageResource video = new RawPageResource();
    	video.setName(VIDEO_RESOURCE_NAME);
    	video.setCanHaveContent(false);
    	video.setPossibleAttributes(imageVideoResourceAttributes);
    	
    	IPageResource text = new RawPageResource();
    	text.setName(TEXT_RESOURCE_NAME);
    	text.setCanHaveContent(true);
    	text.setContent("");
    	
    	ArrayList<IPageResource> pageResourceFormat = new ArrayList<IPageResource>(3);
    	pageResourceFormat.add(image);
    	pageResourceFormat.add(video);
    	pageResourceFormat.add(text);
    	
    	return pageResourceFormat;
	}
	
	/**
	 * Builds a page that a gallery can contain
	 * @param pageResourceFormat
	 * @param pageResourceRuleFormat
	 * @return
	 */
	private static IPage getPageFormat(ArrayList<IPageResource> pageResourceFormat) {
		Set<String> possiblePageAttributeList = new HashSet<String>(2);
    	possiblePageAttributeList.add(DESCRIPTION);
    	possiblePageAttributeList.add(CONFIRMATION_TEXT);     	
    	
    	IPage pageFormat = new RawPage(possiblePageAttributeList, pageResourceFormat);
    	return pageFormat;
	}

	/**
	 * 
	 * @return
	 */
	private static IGallery getGalleryFormat(IPage pageType) {
		Set<String> possibleGalleryAttributeList = new HashSet<String>();
    	for(String attribute : GALLERY_ATTRIBUTES){
    		possibleGalleryAttributeList.add(attribute);
    	}
    	
    	IGallery galleryFormat = new RawGallery(possibleGalleryAttributeList, pageType);
    	return galleryFormat;
	}

}
