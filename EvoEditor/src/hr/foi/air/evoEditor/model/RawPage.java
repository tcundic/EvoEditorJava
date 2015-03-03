package hr.foi.air.evoEditor.model;

import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;
import hr.foi.air.evoEditor.model.interfaces.IPageResourceRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class RawPage implements IPage{

    public static final String DEFAULT_STRING = "";   
    
    private UUID iD;
    private UUID parentID;
    private int orderNumber;
    private boolean pageDefined;
    
    private HashMap<String, String> pageAttributeMap;
    private ArrayList<IPageResource> pageResourceList;
    private IPageResourceRule pageResourceRule;
    
    public RawPage(Set<String> pageAttributeNames,
    		ArrayList<IPageResource> pageResourceList,
    		IPageResourceRule pageResourceRule){
    	
    	this.iD = UUID.randomUUID();
    	
        this.pageResourceRule = pageResourceRule;        
        
        this.pageResourceList = new ArrayList<IPageResource>();       
        for(IPageResource pageResource : pageResourceList){
    		this.pageResourceList.add(pageResource.clone());
    	}
        
        this.pageAttributeMap = new HashMap<String, String>();
        for(String attributeName : pageAttributeNames){
        	this.pageAttributeMap.put(attributeName, DEFAULT_STRING);
        }
    }
    
    
    @Override
	public IPage getInstance(UUID parentiD, int orderNumber) {    	
    	IPage page = new RawPage(pageAttributeMap.keySet(), pageResourceList, pageResourceRule);
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

    public String getPageAttribute(String attributeName) {
        return this.pageAttributeMap.get(attributeName);
    }

    public void setPageAttribute(String attributeName, String attributeValue) {
        this.pageAttributeMap.put(attributeName, attributeValue);
    }  
    
    public Set<String> getPageAttributeSet(){
    	return pageAttributeMap.keySet();
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
    
    public IPageResourceRule getPageResourceRule() {
		return pageResourceRule;
	}

	public void setPageResourceRule(IPageResourceRule pageResourceRule) {
		this.pageResourceRule = pageResourceRule;
	}
    
    public void usePageResource(String pageResourceName){
    	this.pageResourceRule.setResourceAsUsable(pageResourceList, pageResourceName);
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
        			pageResource.setAttributeValue(attributeName, attributeValue);
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
}

