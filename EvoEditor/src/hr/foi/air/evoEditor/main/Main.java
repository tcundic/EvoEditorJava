package hr.foi.air.evoEditor.main;

import hr.foi.air.evoEditor.controller.EvoEditor;
import hr.foi.air.evoEditor.gui.EditorMainGUI;
import hr.foi.air.evoEditor.model.EvoAttribute;
import hr.foi.air.evoEditor.model.EvoImageResource;
import hr.foi.air.evoEditor.model.EvoTextResource;
import hr.foi.air.evoEditor.model.EvoVideoResource;
import hr.foi.air.evoEditor.model.RawGallery;
import hr.foi.air.evoEditor.model.RawPage;
import hr.foi.air.evoEditor.model.RawPageResource;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.UIManager;

public class Main {
	
	public static final String DESCRIPTION = "description";
	public static final String CONFIRMATION_TEXT = "confirmationText";
	
	public static final String SHOW_INDICATOR = "showIndicator";
	public static final String TRANSPARENCY = "transparency";
	public static final String[] GALLERY_ATTRIBUTES = {"name", "qrcode", "repeat", SHOW_INDICATOR, TRANSPARENCY};
	
	public static final String IMAGE_RESOURCE_NAME = "image";
	public static final String VIDEO_RESOURCE_NAME = "video";
	public static final String TEXT_RESOURCE_NAME = "text";
	public static final String AUDIO_RESOURCE_NAME = "audio";
	
	public static final int IMAGE_RESOURCE_TYPE = 1;
	public static final int VIDEO_RESOURCE_TYPE = 2;
	public static final int TEXT_RESOURCE_TYPE = 3;
	public static final int AUDIO_RESOURCE_TYPE = 4;
	
	public static final String PATH_RESOURCE_ATTRIBUTE_NAME = "path";
	

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
		LinkedHashSet<EvoAttribute> imageResourceAttributes = new LinkedHashSet<EvoAttribute>(1);
		LinkedHashSet<EvoAttribute> videoResourceAttributes = new LinkedHashSet<EvoAttribute>(1);
		imageResourceAttributes.add(new EvoAttribute(PATH_RESOURCE_ATTRIBUTE_NAME));
		videoResourceAttributes.add(new EvoAttribute(PATH_RESOURCE_ATTRIBUTE_NAME));
		
//		IPageResource image = new RawPageResource();
//    	image.setName(IMAGE_RESOURCE_NAME); 
//    	image.setPossibleAttributes(imageResourceAttributes);
//    	image.setCanHaveContent(false);
//    	image.setDefaultlyUsed(true);
//    	image.setDataType(IMAGE_RESOURCE_TYPE);
//    	image.setContainsExternalFile(true);
//    	image.setExternalFileLocationAttributeName(PATH_RESOURCE_ATTRIBUTE_NAME);
//    	image.setAcceptableFileExtensions(new String[]{"jpg","gif"});    	
    	
//    	IPageResource video = new RawPageResource();
//    	video.setName(VIDEO_RESOURCE_NAME);
//    	video.setPossibleAttributes(videoResourceAttributes);
//    	video.setCanHaveContent(false);
//    	video.setDataType(VIDEO_RESOURCE_TYPE);
//    	video.setContainsExternalFile(true);
//    	video.setExternalFileLocationAttributeName(PATH_RESOURCE_ATTRIBUTE_NAME);
//    	video.setAcceptableFileExtensions(new String[]{"mp4"});    	
    	
//    	IPageResource text = new RawPageResource();
//    	text.setName(TEXT_RESOURCE_NAME);
//    	text.setCanHaveContent(true);
//    	text.setDataType(TEXT_RESOURCE_TYPE);
//    	text.setContainsExternalFile(false);
		
		// audio resource
    	IPageResource audio = new RawPageResource();
    	audio.setName(AUDIO_RESOURCE_NAME);
		
    	// resource attributes
    	LinkedHashSet<EvoAttribute> audioPageResources = 
    			new LinkedHashSet<EvoAttribute>();    	
    	audioPageResources.add(new EvoAttribute("path"));
    	audioPageResources.add(new EvoAttribute("start_time"));
    	audioPageResources.add(new EvoAttribute("volume"));
    	audio.setPossibleAttributes(audioPageResources);
    	
    	// Meta data
    	audio.setCanHaveContent(true);
    	audio.setDataType(AUDIO_RESOURCE_TYPE);
    	audio.setContainsExternalFile(true);
    	audio.setExternalFileLocationAttributeName("path");
    	audio.setAcceptableFileExtensions(new String[]{"mp3"});   
    	
    	// Evo resources
    	IPageResource evoImage = new EvoImageResource(true);
    	IPageResource evoVideo = new EvoVideoResource();
    	IPageResource evoText = new EvoTextResource();
    	
    	ArrayList<IPageResource> pageResourceFormat = new ArrayList<IPageResource>(3);
    	pageResourceFormat.add(audio);
    	pageResourceFormat.add(evoImage);
    	pageResourceFormat.add(evoVideo);
    	pageResourceFormat.add(evoText);
    	
//    	pageResourceFormat.add(image);
//    	pageResourceFormat.add(video);
//    	pageResourceFormat.add(text);
    	
    	return pageResourceFormat;
	}
	
	/**
	 * Builds a page that a gallery can contain
	 * @param pageResourceFormat
	 * @param pageResourceRuleFormat
	 * @return
	 */
	private static IPage getPageFormat(ArrayList<IPageResource> pageResourceFormat) {
		EvoAttribute description = new EvoAttribute(DESCRIPTION);
		EvoAttribute confirmationText = new EvoAttribute(CONFIRMATION_TEXT);
		
		LinkedHashSet<EvoAttribute> possiblePageAttributeList = new LinkedHashSet<EvoAttribute>(2);		
		possiblePageAttributeList.add(description);		
		possiblePageAttributeList.add(confirmationText); 
    	
    	IPage pageFormat = new RawPage(possiblePageAttributeList, pageResourceFormat);
    	return pageFormat;
	}

	/**
	 * 
	 * @return
	 */
	private static IGallery getGalleryFormat(IPage pageType) {
		Set<EvoAttribute> possibleGalleryAttributeSet = new LinkedHashSet<EvoAttribute>(10);
		String[] galleryAttributes = {"name", "qrcode", "repeat", SHOW_INDICATOR, TRANSPARENCY};
    	for(String attributeName : galleryAttributes){
    		possibleGalleryAttributeSet.add(new EvoAttribute(attributeName));
    	}
    	
    	IGallery galleryFormat = new RawGallery(possibleGalleryAttributeSet, pageType);
    	return galleryFormat;
	}

}
