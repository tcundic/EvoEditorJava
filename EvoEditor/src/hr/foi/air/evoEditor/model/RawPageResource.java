package hr.foi.air.evoEditor.model;

import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.util.LinkedHashSet;

public class RawPageResource implements IPageResource {
	
	private static final String DEFAULT_STRING = "";
	private static final boolean DEFAULT_BOOLEAN = false;
	private static final int DEFAULT_INT = -1;
	
	private String name;
	private String content;
	private LinkedHashSet<EvoAttribute> attributeSet;
	private boolean isUsed;
	private boolean isUSedByDefault;
	private boolean canHaveContent;
	private boolean containsExternalFile;
	private String externalFileLocationAttributeName;
	private int dataType;
	private String[] fileExtensions;
	
	
	public RawPageResource() {
		name = DEFAULT_STRING;
		content = DEFAULT_STRING;
		attributeSet = new LinkedHashSet<EvoAttribute>();
		isUsed = DEFAULT_BOOLEAN;
		canHaveContent = DEFAULT_BOOLEAN;
		isUSedByDefault = DEFAULT_BOOLEAN;
		containsExternalFile = DEFAULT_BOOLEAN;
		externalFileLocationAttributeName = DEFAULT_STRING;
		dataType = DEFAULT_INT;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(String content) {
		if(canHaveContent){
			this.content = content;
		}		
	}

	/**
	 * Check if that resource can have content.
	 * @return
	 */
	@Override
	public boolean canHaveContent(){
		return this.canHaveContent;
	}

	/**
	 * Set that resource can or can't have content.
	 * @param canHaveContent
	 */
	@Override
	public void setCanHaveContent(boolean canHaveContent){
		this.canHaveContent = canHaveContent;
		if(canHaveContent == false){
			this.content = DEFAULT_STRING;
		}
	}
	
	/***
	 * Deep copy of a RawPageResource object. 
	 */
	@Override
	public RawPageResource clone(){
		RawPageResource pr = new RawPageResource();
		pr.setUsed(isUsed);
		pr.setContent(content);
		pr.setName(name);
		pr.setCanHaveContent(canHaveContent);
		pr.setDefaultlyUsed(isUSedByDefault);
		pr.setDataType(dataType);
		pr.setContainsExternalFile(containsExternalFile);
		pr.setAcceptableFileExtensions(fileExtensions);
		pr.setExternalFileLocationAttributeName(externalFileLocationAttributeName);

		//String is immutable?
		for(EvoAttribute attribute : attributeSet){
			EvoAttribute attributeClone = new EvoAttribute(
					attribute.getAttributeName(),
					attribute.getAttributeValue(),
					attribute.isUsed());			
			pr.addPossibleAttribute(attributeClone);
		}		
		return pr;
	}

	@Override
	public boolean isUsed() {
		return isUsed;
	}

	@Override
	public void setUsed(boolean isVisible) {
		this.isUsed = isVisible;
	}

	@Override
	public void setDefaultlyUsed(boolean isUsedByDefault) {
		this.isUSedByDefault = isUsedByDefault;		
	}

	@Override
	public boolean isDefaultlyUsed() {
		return isUSedByDefault;
	}

	@Override
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	@Override
	public int getDataType() {		
		return dataType;
	}

	@Override
	public void setContainsExternalFile(boolean containsExternalFile) {
		this.containsExternalFile = containsExternalFile;		
	}

	@Override
	public boolean containsExternalFile() {
		return containsExternalFile;
	}

	@Override
	public void setExternalFileLocationAttributeName(String externalFileLocationAttributeName) {
		this.externalFileLocationAttributeName = externalFileLocationAttributeName;		
	}

	@Override
	public String getExternalFileLocationAttributeName() {
		return externalFileLocationAttributeName;
	}

	@Override
	public void setAcceptableFileExtensions(String[] extensions) {
		if(extensions != null){
			this.fileExtensions = extensions.clone();
		}
		else{
			fileExtensions = null;
		}
	}

	@Override
	public String[] getAcceptableFileExtensions() {
		return fileExtensions;
	}

	@Override
	public void addPossibleAttribute(EvoAttribute possibleAttribute) {
		this.attributeSet.add(possibleAttribute);
		
	}

	@Override
	public boolean containsAttributeWithName(String attributeName) {
		boolean returnBoolean = false;
		for(EvoAttribute attribute : attributeSet){
			if(attribute.getAttributeName().equalsIgnoreCase(attributeName)){
				returnBoolean = true;
				break;
			}
		}
		return returnBoolean;
	}

	@Override
	public void setPossibleAttributes(
			LinkedHashSet<EvoAttribute> possibleAttributesArray) {
		this.attributeSet = possibleAttributesArray;
		
	}

	@Override
	public LinkedHashSet<EvoAttribute> getAttributeSet() {
		return attributeSet;
	}

	@Override
	public EvoAttribute getAttributeByName(String attributeName) {
		EvoAttribute attributeToReturn = null;
		for(EvoAttribute attribute : attributeSet){
			if(attribute.getAttributeName().equalsIgnoreCase(attributeName)){
				attributeToReturn = attribute;
				break;
			}
		}
		return attributeToReturn;
	}
}
