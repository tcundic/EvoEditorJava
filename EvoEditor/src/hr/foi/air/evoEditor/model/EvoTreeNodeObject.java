package hr.foi.air.evoEditor.model;

import java.util.UUID;

/**
 * Object that is used as a JTree node in the GUI.
 *
 */
public class EvoTreeNodeObject {
	
	private int orderNumber;
	private UUID objectId;
	private String objectName;
	
	public EvoTreeNodeObject(String objectName, int orderNumber, UUID objectId) {
		this.orderNumber = orderNumber;
		this.objectId = objectId;
		this.objectName = objectName;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public UUID getObjectId() {
		return objectId;
	}
	
	public void setObjectId(UUID objectId) {
		this.objectId = objectId;
	}
	
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String toString(){
		String objectShortId = objectId.toString().substring(0, 4);
		return objectName + " " + (orderNumber + 1) + " [" +  objectShortId + "]";
	}
}
