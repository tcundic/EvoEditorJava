package hr.foi.air.evoEditor.model.interfaces;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public interface IPage extends Comparable<IPage>{

    /**
     * Returns a page of the type that implements the IPage interface and
     * sets the page parent ID and order number within that parent.
     * @param parentiD
     * @param orderNumber
     * @return
     */
	public IPage getInstance(UUID parentiD, int orderNumber);
	
	@Override
    public boolean equals(Object obj);

    /**
     * Check if page is defined.
     * @return
     */
	public boolean isPageDefined();

    /**
     * Get current page position number.
     * @return
     */
    public int getOrderNumber();

    /**
     * Set position for current page.
     * @param orderNumber
     */
    public void setOrderNumber(int orderNumber);

    /**
     * Move page up in tree.
     */
    public void incrementOrderNumber();

    /**
     * Move page down in tree.
     */
    public void decrementOrderNumber();

    /**
     * Get current page id.
     * @return
     */
    public UUID getId() ;

    /**
     * Set current page parent.
     * @param parentID
     */
    public void setParentID(UUID parentID);

    /**
     * Get parent of current page.
     * @return
     */
    public UUID getParentID() ;

    /**
     * Get attribute attributeName of current page.
     * @param attributeName
     * @return
     */
    public String getPageAttribute(String attributeName);

    /**
     * Set value of attribute attributeName of current page to attributeValue.
     * @param attributeName
     * @param attributeValue
     */
    public void setPageAttribute(String attributeName, String attributeValue);

    /**
     * Get attributes of current page.
     * @return
     */
    public Set<String> getPageAttributeSet();

    /**
     * Get resources used on current page.
     * @return
     */
    public ArrayList<IPageResource> getPageResources();

    /**
     * Set resources that can be used on page.
     * @param possiblePageResources
     */
    public void setPossibleResources(ArrayList<IPageResource> possiblePageResources);

    /**
     * Get rule by which resource can be added on page.
     * @return
     */
    public IPageResourceRule getPageResourceRule();

    /**
     * Set rule by which page can be added to page.
     * @param pageResourceRule
     */
	public void setPageResourceRule(IPageResourceRule pageResourceRule);

    /**
     * Use resource pageResourceName on current page.
     * @param pageResourceName
     */
    public void usePageResource(String pageResourceName);

    /**
     * Change value of resource attribute attributeName on current page to attributeValue.
     * @param resourceName
     * @param attributeName
     * @param attributeValue
     */
    public void editResourceAttribute(String resourceName, String attributeName, String attributeValue);

    /**
     * Change content of resource resourceName on current page to content.
     * @param resourceName
     * @param content
     */
    public void editResourceContent(String resourceName, String content);
}
