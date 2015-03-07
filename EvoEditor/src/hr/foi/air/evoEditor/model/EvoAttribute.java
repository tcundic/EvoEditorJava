package hr.foi.air.evoEditor.model;

public class EvoAttribute {
	public static final String DEFAULT_VALUE = "";
	public static final boolean DEFAULT_IS_USED = false;
	
	private String attributeName;
	private String attributeValue;
	private boolean isUsed;
	
	public EvoAttribute(String attributeName, String attributeValue,
			boolean isUsed) {
		super();
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
		this.isUsed = isUsed;
	}
	
	public EvoAttribute(String attributeName){
		this.attributeName = attributeName;
		this.attributeValue = DEFAULT_VALUE;
		this.isUsed = DEFAULT_IS_USED;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	public EvoAttribute clone(){
		return new EvoAttribute(attributeName, attributeValue, isUsed);
	}
}
