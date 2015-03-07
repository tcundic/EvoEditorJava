package hr.foi.air.evoEditor.model;

import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class RawPage implements IPage{

    public static final String DEFAULT_STRING = "";
    public static final boolean DEFAULT_BOOLEAN = false;
    
    private UUID iD;
    private UUID parentID;
    private int orderNumber;
    private boolean pageDefined;
    
    private LinkedHashSet<EvoAttribute> pageAttributeSet;
    private ArrayList<IPageResource> pageResourceList;
    
    public RawPage(LinkedHashSet<EvoAttribute> pageAttrbuteSet,
    		ArrayList<IPageResource> pageResourceList){
    	
    	this.iD = UUID.randomUUID();
    	this.pageAttributeSet = new LinkedHashSet<EvoAttribute>();
    	for(EvoAttribute attribute : pageAttrbuteSet){
    		this.pageAttributeSet.add(attribute.clone());
    	}      
        
        /*
         * Uses the user defined type of PageResources and sets to used the first
         * resource with the attribute isDefaultlyUsed set to true
         */
        boolean defaultResourceIsSet = false;
        this.pageResourceList = new ArrayList<IPageResource>();       
        for(IPageResource pageResourceType : pageResourceList){
        	IPageResource pageResource = pageResourceType.clone();
        	pageResource.setUsed(false);
        	if(!defaultResourceIsSet && pageResource.isDefaultlyUsed()){
        		pageResource.setUsed(true);
        		defaultResourceIsSet = true;
        	}
    		this.pageResourceList.add(pageResource);
    	}        
    }
    
    
    @Override
	public IPage getInstance(UUID parentiD, int orderNumber) {
    	IPage page = new RawPage(pageAttributeSet, pageResourceList);
    	page.setParentID(parentiD);
    	page.setOrderNumber(orderNumber);
    	
    	this.pageDefined = true; 
		return page;
	}
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IPage other = (IPage) obj;
        if (this.iD != other.getId()){
        	return false;
        }            
        return true;
    }
    
    @Override
    public int compareTo(IPage page) {
        return this.orderNumber - page.getOrderNumber();
    }
    
    public int getOrderNumber() {
		return this.orderNumber;
	}
    
    public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
    
    /**
     * Increments order number by 1. No rule is applied,
     * the order number can go to maximum integer value.
     */
    public void incrementOrderNumber(){
    		this.orderNumber++;    	
    }
    
    /**
     * Decrements order number by 1. No rule is applied,
     * the order number can go below 0 to minimum integer value.
     */
    public void decrementOrderNumber(){
    	this.orderNumber--;
    }
    
    public UUID getId() {
        return this.iD;
    }

    public void setParentID(UUID parentID) {
        this.parentID = parentID;
    }
    
    public UUID getParentID() {
        return parentID;
    }

    public EvoAttribute getPageAttribute(String attributeName) {    	
		return findAttributeByName(attributeName);
    }
    
    private EvoAttribute findAttributeByName(String attributeName){
    	EvoAttribute attributeToReturn = null;
		for(EvoAttribute attribute : pageAttributeSet){
			if(attribute.getAttributeName().equalsIgnoreCase(attributeName)){
				attributeToReturn = attribute;
				break;
			}
		}
		return attributeToReturn;
    }

    public void setPageAttribute(String attributeName, String attributeValue) {
    	EvoAttribute attribute = findAttributeByName(attributeName);
    	if(attribute != null){
    		attribute.setAttributeValue(attributeValue);
    	}
    }  
    
    public Set<EvoAttribute> getPageAttributeSet(){
    	return pageAttributeSet;
    }
    
    public ArrayList<IPageResource> getPageResources(){
    	return this.pageResourceList;    	
    }    
    
    public void setPossibleResources(ArrayList<IPageResource> possiblePageResources){
    	this.pageResourceList = new ArrayList<IPageResource>();
    	for(IPageResource pageResource : possiblePageResources){
    		this.pageResourceList.add(pageResource.clone());
    	}
    }
	
    public void usePageResource(String pageResourceName){
    	for(IPageResource pageResource : pageResourceList){
			if(pageResource.getName().compareTo(pageResourceName) == 0){
				pageResource.setUsed(true);
			}else{
				pageResource.setUsed(false);
			}
		}
    }

    /**
     * Change value of resource attribute attributeName on current page to attributeValue.
     * @param resourceName
     * @param attributeName
     * @param attributeValue
     */
    public void editResourceAttribute(String resourceName, String attributeName, String attributeValue){
    	if(pageResourceList != null){
    		for(IPageResource pageResource : pageResourceList){
        		if(pageResource.getName().equalsIgnoreCase(resourceName)){
        			Set<EvoAttribute> resourceAttributeSet = pageResource.getAttributeSet();
        			for(EvoAttribute attribute : resourceAttributeSet){
        				if(attribute.getAttributeName().equalsIgnoreCase(attributeName)){
        					attribute.setAttributeValue(attributeValue);
        				}
        			}
        		}
        	}
    	}    	 	
    }

    /**
     * Change content of resource resourceName on current page to content.
     * @param resourceName
     * @param content
     */
    public void editResourceContent(String resourceName, String content){
    	if(pageResourceList != null){
    		for(IPageResource pageResource : pageResourceList){
        		if(pageResource.getName().equalsIgnoreCase(resourceName)){
        			pageResource.setContent(content);   			
        		}
        	}
    	}    	
    }

	@Override
	public boolean isPageDefined() {
		return this.pageDefined;
	}


	@Override
	public IPageResource getUsedResource() {
		IPageResource usedPageResource = null;
		for(IPageResource resource : pageResourceList){
			if(resource.isUsed()){
				usedPageResource = resource;
				break;
			}
		}
		return usedPageResource;
	}
}

