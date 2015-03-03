package hr.foi.air.evoEditor.main;

import hr.foi.air.evoEditor.controller.XMLGenerator;
import hr.foi.air.evoEditor.model.EvoPageResourceRule;
import hr.foi.air.evoEditor.model.RawGallery;
import hr.foi.air.evoEditor.model.RawPage;
import hr.foi.air.evoEditor.model.RawPageResource;
import hr.foi.air.evoEditor.model.VideoPageResource;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;
import hr.foi.air.evoEditor.model.interfaces.IPageResourceRule;

import java.util.ArrayList;
import java.util.UUID;

public class SimulateUse {
	
	private static final String DESCRIPTION = "description";
	private static final String CONFIRMATION_TEXT = "confirmationText";
	private static final String FINAL_XML_PATH = "C://Users/Tadija/Desktop/gallery1.xml";
	
	private static final String NAME_TEXT = "name";
	private static final String Q_R_CODE = "qrcode";
	private static final String REPEAT = "repeat";
	private static final String SHOW_INDICATOR = "showIndicator";
	private static final String TRANSPARENCY = "transparency";
	
	private static IGallery gallery;

    public static void main(String[] args){
    	
    	IPage pageType = new RawPage();
    	IGallery galleryType = new RawGallery();
    	
    	// page setup
    	ArrayList<String> possiblePageAttributeList = new ArrayList<String>();
    	possiblePageAttributeList.add(DESCRIPTION);
    	possiblePageAttributeList.add(CONFIRMATION_TEXT);
    	    	
    	IPageResource image = new RawPageResource();
    	image.setName("image");
    	image.setUsed(false);
    	image.setCanHaveContent(false);
    	image.setAttributeValue("path", "");
    	
    	IPageResource text = new RawPageResource();
    	text.setName("text");
    	text.setUsed(false);
    	text.setCanHaveContent(true);
    	text.setContent("");
    	
    	IPageResource video = new VideoPageResource(); // VIDEO
    	video.setUsed(false);
    	video.setAttributeValue("path", "");
    	
    	ArrayList<IPageResource> possiblePageResourceList = new ArrayList<IPageResource>();
    	possiblePageResourceList.add(text);
    	possiblePageResourceList.add(video);
    	possiblePageResourceList.add(image);
    	
    	IPageResourceRule pageResourceRule = new EvoPageResourceRule(); // RULE
    	
    	// gallery setup
    	ArrayList<String> possibleGalleryAttributeList = new ArrayList<String>();
    	possibleGalleryAttributeList.add("name");
    	possibleGalleryAttributeList.add("qrcode");
    	possibleGalleryAttributeList.add("repeat");
    	possibleGalleryAttributeList.add("showIndicator");
    	possibleGalleryAttributeList.add("transparency");      	
    	
    	gallery = galleryType.getInstance(possibleGalleryAttributeList, possiblePageAttributeList,
    			possiblePageResourceList, pageResourceRule, pageType);

    	gallery.setGalleryAttribute(NAME_TEXT, NAME_TEXT);
    	gallery.setGalleryAttribute(Q_R_CODE, Q_R_CODE);
    	gallery.setGalleryAttribute(REPEAT, REPEAT);
    	gallery.setGalleryAttribute(SHOW_INDICATOR, SHOW_INDICATOR);
    	gallery.setGalleryAttribute(TRANSPARENCY, TRANSPARENCY);
    	
    	simulateClicking(text, video, image); // SIMULACIJA

        XMLGenerator generator = new XMLGenerator();
        generator.setFile(FINAL_XML_PATH);        
        generator.setIndent("6"); // set indent for xml nodes
        generator.generateXmlFile(gallery);
    }
    
    /**
     * This method simulates the user clicking on the controls of the interface. 
     * 
     * 
     * @param text
     * @param video
     * @param image
     */
    public static void simulateClicking(IPageResource text, IPageResource video, IPageResource image){
    	
    	UUID selectedGuiElement = gallery.getID();
    	
    	// klik na add page, ništa nije odabrano
        UUID page1 = clickAddNewPage(selectedGuiElement);
        
        // klik na add page, ništa nije odabrano
        UUID page2 = clickAddNewPage(selectedGuiElement);
        
        // klik na add page, ništa nije odabrano
        UUID page3 = clickAddNewPage(selectedGuiElement);
        
        // klik na add page, ništa nije odabrano
        UUID page4 = clickAddNewPage(selectedGuiElement);
        
        // klik na add page, ništa nije odabrano
        UUID page5 = clickAddNewPage(selectedGuiElement);
        
        // klik na add page, ništa nije odabrano
        UUID page6 = clickAddNewPage(selectedGuiElement);
        
        //Uredi opis odabran 1 page
        selectedGuiElement = clickOnPage(page1);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "1");
        
        //Uredi opis odabran 2 page
        selectedGuiElement = clickOnPage(page2);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "2");
        
        //Uredi opis odabran 3 page
        selectedGuiElement = clickOnPage(page3);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "3");
        
        //Uredi opis odabran 4 page
        selectedGuiElement = clickOnPage(page4);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "4");
        editPageAttribut(selectedGuiElement, CONFIRMATION_TEXT, "Confirmation 4");
        
        //Uredi opis odabran 5 page
        selectedGuiElement = clickOnPage(page5);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "5");
        
        //Uredi opis odabran 6 page
        selectedGuiElement = clickOnPage(page6);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "6");
        
        //klik na add subpage, odabran je drugi page
        selectedGuiElement = clickOnPage(page2);
        UUID page2_1 = clickAddNewPage(selectedGuiElement);
        
        //klik na add subpage, odabran je drugi page
        UUID page2_2 = clickAddNewPage(selectedGuiElement);
        
        //klik na add subpage, odabran je drugi page
        UUID page2_3 = clickAddNewPage(selectedGuiElement);
        
      //Uredi opis odabran 1. subpage 2 page
        selectedGuiElement = clickOnPage(page2_1);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "2_1");
        
        //Uredi opis odabran 2. subpage 2 page
        selectedGuiElement = clickOnPage(page2_2);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "2_2");
        
        //Uredi opis odabran 3. subpage 2 page
        selectedGuiElement = clickOnPage(page2_3);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "2_3");
        
        // klik na add subpage, odabran 4. page
        selectedGuiElement = clickOnPage(page4);
        UUID page4_1 = clickAddNewPage(selectedGuiElement);
        
        // klik na add subpage, odabran 4. page
        UUID page4_2 = clickAddNewPage(selectedGuiElement);      
        
        //Uredi opis odabran 1. subpage 4 page
        selectedGuiElement = clickOnPage(page4_1);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "4_1");
        
        //Uredi opis odabran 2. subpage 4 page
        selectedGuiElement = clickOnPage(page4_2);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "4_2");
        
        //Uredi opis odabran 2. subpage 2 page
        selectedGuiElement = clickOnPage(page2_2);
        editPageAttribut(selectedGuiElement, DESCRIPTION, "2_2_ispravljeno");
        
        //Klik uredi resurs 3. subpage 2 page
        selectedGuiElement = clickOnPage(page2_3);
        clickSelectResource(selectedGuiElement, video.getName());
        clickEditResourceTag(selectedGuiElement, video.getName(), video.getAttributeSet().iterator().next(), "putanjaVidea");
        
      //Klik uredi resurs 2. subpage 2 page
        selectedGuiElement = clickOnPage(page3);
        clickSelectResource(selectedGuiElement, text.getName());
        clickEnterResourceContent(selectedGuiElement, text.getName(), "Ovo je moj text");
        clickSelectResource(selectedGuiElement, video.getName());
        clickEditResourceTag(selectedGuiElement, video.getName(), video.getAttributeSet().iterator().next(), "putanjaVidea");
        
        //Dodaj sliku na podpage
        selectedGuiElement = clickOnPage(page4_1);
        clickSelectResource(selectedGuiElement, image.getName());
        clickEditResourceTag(selectedGuiElement, image.getName(), image.getAttributeSet().iterator().next(), "putanjaSlike");
        
        //Dodaj text na podpage
        selectedGuiElement = clickOnPage(page4_2);
        clickSelectResource(selectedGuiElement, text.getName());
        clickEnterResourceContent(selectedGuiElement, text.getName(), "Ovo je moj text");
        // poveèaj poziciju
        clickIncrementPagePosition(selectedGuiElement);
        
        // kliknut poveèaj poziciju 2. subpage od 2. pagea
        selectedGuiElement = clickOnPage(page2_2);
        clickIncrementPagePosition(selectedGuiElement);
//        
        // kliknut smanji poziciju 2. subpage od 2. pagea
        clickDecrementPagePosition(selectedGuiElement);
        
        // kliknut smanji poziciju 2. subpage od 2. pagea
        clickDecrementPagePosition(selectedGuiElement);
        
        // kliknut smanji poziciju 2. subpage od 2. pagea
        clickDecrementPagePosition(selectedGuiElement);
        
        // kliknut poveèaj poziciju 4. pagea
        selectedGuiElement = clickOnPage(page4);
        clickIncrementPagePosition(selectedGuiElement);
        
        // kliknut poveèaj poziciju 4. pagea
        clickIncrementPagePosition(selectedGuiElement);
        
        //kliknut obriši page odabran 2. subpage od 2. pagea
//        odabraniElementGUI = klikNaPage(page2_2);
//        klikObrisiPage(odabraniElementGUI);
      	
       // kliknut obriši page odabran 2. page
//        odabraniElementGUI = klikNaPage(page2);
//        klikObrisiPage(odabraniElementGUI);
//        
        selectedGuiElement = clickOnPage(page3);
        clickIncrementPagePosition(selectedGuiElement);
        
        selectedGuiElement = clickOnPage(page6);
        clickSelectResource(selectedGuiElement, image.getName());
        clickEnterResourceContent(selectedGuiElement, image.getName(), "Slika");
        // image.getAttributeSet().iterator().next()
        clickEditResourceTag(selectedGuiElement, image.getName(), "path", "Ovo je putanja");
    }
    
    public static UUID clickOnPage(UUID pageID){
    	return pageID;
    }
    
    public static UUID clickOutOfPageArea(){
    	return gallery.getID();
    }
    
    public static UUID clickAddNewPage(UUID parentID){
    	return gallery.addBlankPage(parentID);    	
    }
    
    public static void clickIncrementPagePosition(UUID pageID){
    	 gallery.increaseOrderNumber(pageID);
    }
    
    public static void clickDecrementPagePosition(UUID pageID){
    	gallery.decreaseOrderNumber(pageID);
    }
    
    public static void editPageAttribut(UUID pageID, String attributeName, String attributeValue){
    	gallery.editPageAttribute(pageID, attributeName, attributeValue);
    }
    
    public static void clickDeletePage(UUID pageID){
    	gallery.deletePage(pageID);
    }
    
    public static void clickSelectResource(UUID pageID, String resourceName){
    	gallery.changePageResourceToUsed(pageID, resourceName);
    }
    
    public static void clickEnterResourceContent(UUID pageID, 
    		String resourceName, String content){
    	gallery.editPageResourceContent(pageID, resourceName, content);
    }
    
    public static void clickEditResourceTag(UUID pageID, String resourceName,
    		String attributeName, String attributeValue){
    	gallery.editPageResourceAtribute(pageID, resourceName, attributeName, attributeValue);
    }
}

