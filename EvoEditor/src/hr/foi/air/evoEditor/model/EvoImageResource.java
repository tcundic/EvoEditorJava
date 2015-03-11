package hr.foi.air.evoEditor.model;

import java.util.LinkedHashSet;

import hr.foi.air.evoEditor.model.interfaces.IPageResource;

public class EvoImageResource implements IPageResource {
	
	public static final int IMAGE_RESOURCE_TYPE = 1;
	public static final String PATH_RESOURCE_ATTRIBUTE_NAME = "path";
	public static final String IMAGE_RESOURCE_NAME = "image";
	
	private boolean isUsedByDefault;
	private boolean isUsed;
	private LinkedHashSet<EvoAttribute> attributeSet;
	
	public EvoImageResource(boolean isUsedByDefault) {
		this.isUsedByDefault = isUsedByDefault;
		this.isUsed = false;
		attributeSet = new LinkedHashSet<EvoAttribute>();
		EvoAttribute path = new EvoAttribute(PATH_RESOURCE_ATTRIBUTE_NAME, "", true);
		attributeSet.add(path);
	}
	
	public EvoImageResource() {
		this.isUsedByDefault = false;
		this.isUsed = false;
		attributeSet = new LinkedHashSet<EvoAttribute>();
		EvoAttribute path = new EvoAttribute(PATH_RESOURCE_ATTRIBUTE_NAME, "", true);
		attributeSet.add(path);
	}
	
	private EvoImageResource(boolean isUsedByDefault, boolean isUsed, String pathValue){
		this.isUsedByDefault = isUsedByDefault;
		this.isUsed = isUsed;
		attributeSet = new LinkedHashSet<EvoAttribute>();
		EvoAttribute path = new EvoAttribute(PATH_RESOURCE_ATTRIBUTE_NAME, pathValue, true);
		attributeSet.add(path);
	}

	@Override
	public String getName() { return IMAGE_RESOURCE_NAME; }

	@Override
	public void setName(String name) { }

	@Override
	public void setDefaultlyUsed(boolean isUsedByDefault) {	this.isUsedByDefault = isUsedByDefault;	}

	@Override
	public boolean isDefaultlyUsed() { return isUsedByDefault; }

	@Override
	public boolean isUsed() { return this.isUsed; }

	@Override
	public void setUsed(boolean isUsed) { this.isUsed = isUsed; }

	@Override
	public void setCanHaveContent(boolean canHaveContent) { }

	@Override
	public boolean canHaveContent() { return false; }

	@Override
	public void setContent(String content) { }

	@Override
	public String getContent() { return null; }

	@Override
	public void setDataType(int dataType) { }

	@Override
	public int getDataType() { return IMAGE_RESOURCE_TYPE; }

	@Override
	public void setContainsExternalFile(boolean containsExternalFile) { }

	@Override
	public boolean containsExternalFile() {	return true; }

	@Override
	public void setExternalFileLocationAttributeName(
			String externalFileLocationAttributeName) {	}

	@Override
	public String getExternalFileLocationAttributeName() { return PATH_RESOURCE_ATTRIBUTE_NAME; }

	@Override
	public void addPossibleAttribute(EvoAttribute possibleAttribute) {	}

	@Override
	public boolean containsAttributeWithName(String attributeName) {
		if(attributeName.equalsIgnoreCase(PATH_RESOURCE_ATTRIBUTE_NAME)){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public void setPossibleAttributes(LinkedHashSet<EvoAttribute> possibleAttributesSet) { }

	@Override
	public LinkedHashSet<EvoAttribute> getAttributeSet() { return attributeSet;	}

	@Override
	public EvoAttribute getAttributeByName(String attributeName) {		
		if(attributeName.equalsIgnoreCase(PATH_RESOURCE_ATTRIBUTE_NAME)){
			return (EvoAttribute) attributeSet.toArray()[0];
		}else{
			return null;
		}
	}

	@Override
	public void setAcceptableFileExtensions(String[] extensions) { }

	@Override
	public String[] getAcceptableFileExtensions() {		
		return new String[]{".jpg", ".gif"};
	}

	@Override
	public IPageResource clone() {
		String pathAttributeValue = getAttributeByName(PATH_RESOURCE_ATTRIBUTE_NAME).getAttributeValue();
		return new EvoImageResource(isUsedByDefault, isUsed, pathAttributeValue);
	}

}
