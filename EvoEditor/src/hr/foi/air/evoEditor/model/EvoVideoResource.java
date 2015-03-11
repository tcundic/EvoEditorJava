package hr.foi.air.evoEditor.model;

import java.util.LinkedHashSet;

import hr.foi.air.evoEditor.model.interfaces.IPageResource;

public class EvoVideoResource implements IPageResource {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefaultlyUsed(boolean isUsedByDefault) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDefaultlyUsed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUsed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setUsed(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCanHaveContent(boolean canHaveContent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canHaveContent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setContent(String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataType(int dataType) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDataType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setContainsExternalFile(boolean containsExternalFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsExternalFile() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setExternalFileLocationAttributeName(
			String externalFileLocationAttributeName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getExternalFileLocationAttributeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPossibleAttribute(EvoAttribute possibleAttribute) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsAttributeWithName(String attributeName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPossibleAttributes(
			LinkedHashSet<EvoAttribute> possibleAttributesSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public LinkedHashSet<EvoAttribute> getAttributeSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EvoAttribute getAttributeByName(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAcceptableFileExtensions(String[] extensions) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getAcceptableFileExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPageResource clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
