package hr.foi.air.evoEditor.model.interfaces;

import java.util.ArrayList;
import java.util.Set;

public interface IPageResource {
	
	/**
	 * Sets if the resource should be set to used by default
	 * @param isUsedByDefault
	 */
	public void setDefaultlyUsed(boolean isUsedByDefault);
	
	/**
	 * Returns true if the resource is to be used by default.
	 * @return
	 */
	public boolean isDefaultlyUsed();
	
	/**
	 * @return Name of the resource element
	 */
	public String getName();
	
	/**
	 * Sets the resource element name to the given attribute
	 */
	public void setName(String name);	
	
	/**
	 * 
	 * @return The list of all defined tags of the resource element
	 */
	public Set<String> getAttributeSet();
	
	/**
	 * Gets the attribute value of the given attribute name
	 * 
	 * @param attributeName name of the attribute of which the value should be returned
	 * @return
	 */
	public String getAttributeValue(String attributeName);
	
	/**
	 * Flag that indicates if the resource should be visible in the final XML
	 * @return
	 */
	public boolean isUsed();
	
	/**
	 * Defines if the resource should be visible in the final XML
	 */
	public void setUsed(boolean isUsed);
	
	/**
	 * Checks it the resource element can have content
	 * @return true if the elemet can have content
	 */
	public boolean canHaveContent();
	
	/**
	 * Sets the sets the attribute value if it exists
	 */
	public void setAttributeValue(String attributeName, String attributeValue);
	
	/**
	 * Defines possible attributes for this resource
	 * @param possibleAttributesArray
	 */
	public void setPossibleAttributes(ArrayList<String> possibleAttributesArray);
	
	/**
	 * Defines if the Resource can have content.
	 * 
	 */
	public void setCanHaveContent(boolean canHaveContent);	
	
	/**
	 * Sets the content of the resource element. 
	 * 
	 */
	public void setContent(String content);
	
	/**
	 * 
	 * @return The content of the resource element
	 */
	public String getContent();
	
	/**
	 * Deep copy of a PageResource object. 
	 */
	public IPageResource clone();
}
