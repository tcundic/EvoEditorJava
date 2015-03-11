package hr.foi.air.evoEditor.model;

import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.util.LinkedHashSet;

public class EvoTextResource implements IPageResource {

	public static final String TEXT_RESOURCE_NAME = "text";
	public static final int TEXT_RESOURCE_TYPE = 3;
	
	private boolean isUsedByDefault;
	private boolean isUsed;
	private String content;
	private LinkedHashSet<EvoAttribute> attributeSet;
	
	public EvoTextResource(boolean isUsedByDefault) {
		this.isUsedByDefault = isUsedByDefault;
		this.isUsed = false;
		this.content = "";
		attributeSet = new LinkedHashSet<EvoAttribute>();
	}
	
	public EvoTextResource() {
		this.isUsedByDefault = false;
		this.isUsed = false;
		this.content = "";
		attributeSet = new LinkedHashSet<EvoAttribute>();
	}
	
	private EvoTextResource(boolean isUsedByDefault, boolean isUsed, String content) {
		this.isUsedByDefault = isUsedByDefault;
		this.isUsed = isUsed;
		this.content = content;
		attributeSet = new LinkedHashSet<EvoAttribute>();
	}
	
	@Override
	public String getName() {
		return TEXT_RESOURCE_NAME;
	}

	@Override
	public void setName(String name) {}

	@Override
	public void setDefaultlyUsed(boolean isUsedByDefault) {
		this.isUsedByDefault = isUsedByDefault;
	}

	@Override
	public boolean isDefaultlyUsed() {
		return isUsedByDefault;
	}

	@Override
	public boolean isUsed() {
		return isUsed;
	}

	@Override
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	@Override
	public void setCanHaveContent(boolean canHaveContent) {
	}

	@Override
	public boolean canHaveContent() {
		return true;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setDataType(int dataType) {}

	@Override
	public int getDataType() {		
		return TEXT_RESOURCE_TYPE;
	}

	@Override
	public void setContainsExternalFile(boolean containsExternalFile) {	}

	@Override
	public boolean containsExternalFile() {
		return false;
	}

	@Override
	public void setExternalFileLocationAttributeName(
			String externalFileLocationAttributeName) {	}

	@Override
	public String getExternalFileLocationAttributeName() {
		return null;
	}

	@Override
	public void addPossibleAttribute(EvoAttribute possibleAttribute) {}

	@Override
	public boolean containsAttributeWithName(String attributeName) {
		return false;
	}

	@Override
	public void setPossibleAttributes(
			LinkedHashSet<EvoAttribute> possibleAttributesSet) {}

	@Override
	public LinkedHashSet<EvoAttribute> getAttributeSet() {
		return attributeSet;
	}

	@Override
	public EvoAttribute getAttributeByName(String attributeName) {
		return null;
	}

	@Override
	public void setAcceptableFileExtensions(String[] extensions) {
	}

	@Override
	public String[] getAcceptableFileExtensions() {
		return null;
	}

	@Override
	public IPageResource clone() {
		return new EvoTextResource(isUsedByDefault, isUsed, content);
	}

}
