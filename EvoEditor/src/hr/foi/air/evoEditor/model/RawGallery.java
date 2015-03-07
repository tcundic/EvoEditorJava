package hr.foi.air.evoEditor.model;

import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The class represents an undefined Gallery that will be used in the editor. It expects the
 * get the Attribute list and the detail of the pages it contains in its creation.
 * 
 * @author Tadija
 *
 */
public class RawGallery implements IGallery{
	
	private final static Logger LOGGER = Logger.getLogger(RawGallery.class.getName()); 

    private static final String DEFAULT_STRING = "";

    private UUID iD;    
    
    private HashMap<String, String> galleryAttributeMap;
    
    private ArrayList<IPage> pageList;
    
    private boolean galleryDefined;
    private IPage pageType;
    
    public RawGallery(Set<String> galleryAttributeNames, IPage pageType){
    	
    	this.iD = UUID.randomUUID();
    	
    	this.pageType = pageType;
    	this.pageList = new ArrayList<IPage>();
        
    	this.galleryAttributeMap = new HashMap<String, String>();
    	for(String attributeName : galleryAttributeNames){
        	this.galleryAttributeMap.put(attributeName, DEFAULT_STRING);
        }        
    }
    
    @Override
    public RawGallery getInstance(){    	
    	galleryDefined = true;
    	
    	return new RawGallery(galleryAttributeMap.keySet(), pageType);
    }

	/**
	 * Get current gallery id.
	 * @return
	 */
	public UUID getID(){
		return this.iD;
	}

	/**
	 * Return page with id iD.
	 * @param iD
	 * @return
	 */
	public IPage findPageByID(UUID iD){
		IPage retPage = null;
		for(IPage page : pageList){
			if(page.getId().compareTo(iD) == 0){
				retPage = page;
				break;
			}
		}
		return retPage;
	}

	/**
	 * Get Children of parent with id parentID.
	 * @param parentID
	 * @return
	 */
	public ArrayList<IPage> getChildPageList(UUID parentID){
    	ArrayList<IPage> childList = new ArrayList<IPage>();
    	if(parentID != null){
    		for(IPage page : pageList){
        		if(page.getParentID().compareTo(parentID) == 0){
        			childList.add(page);
        		}
        	}
    	}else{
    		LOGGER.info("parentID = " + parentID);
    	}
    	
    	return childList;
    }

	/**
	 * Add blank page as child of page with id parentID.
	 * @param parentID
	 * @return
	 */
    public UUID addBlankPage(UUID parentID){
		int orderNumber = getChildPageList(parentID).size();
    	IPage newPage = pageType.getInstance(parentID, orderNumber);
    	
    	pageList.add(newPage);
    	return newPage.getId();
    }

	/**
	 * Delete page with id iD.
	 * @param iD
	 */
    public void deletePage(UUID iD){
    	IPage page = findPageByID(iD);
    	UUID parentID = null;
    	
    	if(page != null){
    		parentID = page.getParentID();
    		removePageAndSubpages(page);
    		resetChildPagesOrderNumber(parentID);
    	}
    }

	/**
	 * Move page with id iD up in tree. If the page order number was 3, after this
	 * the order number will be 2.
	 * @param iD
	 */
    public void increaseOrderNumber(UUID iD){
    	IPage pageToMoveUp = findPageByID(iD);
    	if(pageToMoveUp != null && pageToMoveUp.getOrderNumber() != 0){
    		for(IPage page : pageList){
    			if(page.getParentID() == pageToMoveUp.getParentID()){
    				if(page.getOrderNumber() == pageToMoveUp.getOrderNumber() - 1){
    					page.incrementOrderNumber();
    					pageToMoveUp.decrementOrderNumber();
    					Collections.sort(pageList);
    					break;
    				}
    			}
    		}
    	}	
    }

	/**
	 * Move page with id iD down in tree. If the page order number was 3, after this
	 * the order number will be 4.
	 * @param iD
	 */
    public void decreaseOrderNumber(UUID iD){
    	IPage pageToMoveDown = findPageByID(iD);
    	ArrayList<IPage> childPages = getChildPageList(pageToMoveDown.getParentID());
    	if(pageToMoveDown != null && pageToMoveDown.getOrderNumber() != childPages.size() - 1){
    		for(IPage page : childPages){
				if(page.getOrderNumber() == pageToMoveDown.getOrderNumber() + 1){
					page.decrementOrderNumber();
					pageToMoveDown.incrementOrderNumber();
					Collections.sort(pageList);
					break;
				}
    		}
    	}	
    }

	/**
	 * Reset child pages ordering when deleting page.
	 * @param parentID
	 */
    public void resetChildPagesOrderNumber(UUID parentID){
    	ArrayList<IPage> childPages = getChildPageList(parentID);
    	if(parentID != null){
    		int i = 0;
        	for(IPage page : childPages){
        		page.setOrderNumber(i);
        		i++;
        	}
    	}else{
    		LOGGER.info("parentID = " + null);
    	}
    }

	/**
	 * Change value of attribute attributeName to attributeValue of page pageID.
	 * @param pageID
	 * @param attributeName
	 * @param attributeValue
	 */
    public void editPageAttribute(UUID pageID, String attributeName, String attributeValue){
    	IPage page = findPageByID(pageID);
    	if(page != null){
    		page.setPageAttribute(attributeName, attributeValue);
    	}
    }
    
	/**
	 * Change resource which is used on page pageID to resourceName.
	 * @param pageID
	 * @param resourceName
	 */
    public void changePageResourceToUsed(UUID pageID, String resourceName){
    	IPage page = findPageByID(pageID);
    	if(page != null){
    		page.usePageResource(resourceName);;
    	}    	
    }

	/**
	 * Create new child of page pageID.
	 * @param pageID
	 */
    public void makePageSubpage(UUID pageID){    	
    	IPage page = findPageByID(pageID);
    	if(page != null){
    		ArrayList<IPage> neighborPages = getChildPageList(page.getParentID());
        	if(neighborPages.size() != 1 && page.getOrderNumber() != 0){
        		// if the page is not the first or the only page on a level
        		for(IPage neighborPage : neighborPages){
        			if(neighborPage.getOrderNumber() == page.getOrderNumber() - 1){
        				//if the neighbor page is above the edited page make it the parent
        				page.setParentID(neighborPage.getId());
        				break;
        			}
        		}
        	}
    	}// else page is not found    	
    }

	/**
	 * Remove page and its children.
	 * @param page
	 */
	private void removePageAndSubpages(IPage page){    	
		ArrayList<IPage> childPages = getChildPageList(page.getId());
		if(childPages.size() != 0){
			for(IPage childPage : childPages){
				removePageAndSubpages(childPage);
			}
		}
		pageList.remove(page);
	}

	public Set<String> getGalleryAttributeSet() {
		return this.galleryAttributeMap.keySet();
	}
	
	public String getGalleryAttribute(String attributeName) {
		return this.galleryAttributeMap.get(attributeName);
	}
	
	public void setGalleryAttribute(String attributeName, String attributeValue) {
		this.galleryAttributeMap.put(attributeName, attributeValue);
	}

	public void setGalleryAttributeMap(HashMap<String, String> galleryAttributeMap) {
		this.galleryAttributeMap = galleryAttributeMap;
	}
	
	@Override
	public boolean isGallerySet() {
		return this.galleryDefined;
	}
}

