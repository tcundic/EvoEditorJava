package hr.foi.air.evoEditor.model.interfaces;

import hr.foi.air.evoEditor.model.EvoAttribute;

import java.util.LinkedHashSet;

public interface IPageResource {
	
	/**
	 * @return Name of the resource element
	 */
	public String getName();

	/**
	 * Sets the resource element name to the given attribute
	 */
	public void setName(String name);

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
	 * Flag that indicates if the resource should be visible in the final XML
	 * @return
	 */
	public boolean isUsed();
	
	/**
	 * Defines if the resource should be visible in the final XML
	 */
	public void setUsed(boolean isUsed);
	
	/**
	 * Defines if the Resource can have content.
	 * 
	 */
	public void setCanHaveContent(boolean canHaveContent);

	/**
	 * Checks it the resource element can have content
	 * @return true if the elemet can have content
	 */
	public boolean canHaveContent();
	
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
	 * Sets an identifier of the type of data this resource will use. This is meant 
	 * to be used as help for displaying this resource.
	 * @param dataType
	 */
	public void setDataType(int dataType);
	
	/**
	 * Returns an identifier of the type of data this resource will use or a default
	 * value if no identifier is set. This is meant to be used as help for displaying this resource.
	 * @return
	 */
	public int getDataType();
	
	/**
	 * Sets if this resource contains external files.
	 * @param containsExternalFile
	 */
	public void setContainsExternalFile(boolean containsExternalFile);
	
	/**
	 * Returns true if this resource contains external files.
	 * @return
	 */
	public boolean containsExternalFile();
	
	/**
	 * Sets which attribute from the defined attribute set is used to specify the
	 * location of an external file.
	 * @param externalFileLocationAttributeName
	 */
	public void setExternalFileLocationAttributeName(String externalFileLocationAttributeName);
	
	/**
	 * Returns the attribute from the defined attribute set that is used to specify
	 * the location of an external file.
	 */
	public String getExternalFileLocationAttributeName();

	/**
	 * Adds a new possible attribute to the list of existing page resource attributes.
	 * @param possibleAttribute
	 */
	public void addPossibleAttribute(EvoAttribute possibleAttribute);

	/**
	 * Checks if this resource contains an attribute with the given name.
	 * @param attributeName
	 * @return
	 */
	public boolean containsAttributeWithName(String attributeName);
    
	/**
	 * Defines possible attributes for this resource
	 * @param possibleAttributesSet
	 */
	public void setPossibleAttributes(LinkedHashSet<EvoAttribute> possibleAttributesSet);

	/**
	 * 
	 * @return The list of all defined tags of the resource element
	 */
	public LinkedHashSet<EvoAttribute> getAttributeSet();

	/**
	 * Gets the attribute value of the given attribute name
	 * 
	 * @param attributeName name of the attribute of which the value should be returned
	 * @return
	 */
	public EvoAttribute getAttributeByName(String attributeName);
	
	/**
	 * Sets which files will this resource accept, e.g. ".jpg", ".gif".
	 * @param extensions
	 */
	public void setAcceptableFileExtensions(String[] extensions);
	
	/**
	 * Returns the array of file extensions this resource will accept.
	 * @return
	 */
	public String[] getAcceptableFileExtensions(); 
	
	/**
	 * Deep copy of a PageResource object. 
	 */
	public IPageResource clone();
}
