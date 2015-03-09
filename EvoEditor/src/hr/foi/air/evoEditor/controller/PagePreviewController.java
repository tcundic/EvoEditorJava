package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.events.GalleryChangeListener;
import hr.foi.air.evoEditor.events.PageChangeListener;
import hr.foi.air.evoEditor.gui.PagePreviewPanel;
import hr.foi.air.evoEditor.main.Main;
import hr.foi.air.evoEditor.model.EvoAttribute;
import hr.foi.air.evoEditor.model.EvoTreeNodeObject;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.util.UUID;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class PagePreviewController implements TreeSelectionListener, GalleryChangeListener, PageChangeListener {
	
	IGallery gallery;
	IPage selectedPage;
	PagePreviewPanel gui;
	
	
	public PagePreviewController(IGallery gallery) {
		this.gallery = gallery;
	}
	
	public void setGuiObject(PagePreviewPanel previewPanel){
		this.gui = previewPanel;
	}

	/**
	 * Returns the name of the used resource.
	 * @param selectedPageId
	 * @return
	 */
	public String getSelectedPageResourceName(UUID selectedPageId) {
		return gallery.findPageByID(selectedPageId).getUsedResource().getName();
	}
	
	/**
	 * Return the resource used by the page.
	 * @param selectedPageId
	 * @return
	 */
	public IPageResource getUsedResource(UUID selectedPageId){
		return gallery.findPageByID(selectedPageId).getUsedResource();
	}

	public IPage getSelectedPage(UUID selectedPageId) {
		return gallery.findPageByID(selectedPageId);
	}
	
	public void enabelPageComponents(){
		
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree)e.getSource();		
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(selectedNode != null){
			EvoTreeNodeObject nodeObject = (EvoTreeNodeObject) selectedNode.getUserObject();
			selectedPage = gallery.findPageByID(nodeObject.getObjectId());
		}else{
			selectedPage = null;
		}
		
		if(selectedPage != null){
			gui.setVisible(true);
			loadAppropriatePagePreviewElements();
		}else{
			gui.setVisible(false);
		}		
	}

	private void loadAppropriatePagePreviewElements() {
		if(selectedPage != null){			
			gui.hideAllPanels();
			int resourceDataType = selectedPage.getUsedResource().getDataType();
			switch (resourceDataType) {
			case Main.IMAGE_RESOURCE_TYPE:
				loadImagePreviewElements();
				break;
			case Main.TEXT_RESOURCE_TYPE:
				loadTextPreviewElements();
				break;
			case Main.VIDEO_RESOURCE_TYPE:
				loadVideoPreviewElements();
				break;
			default:
				loadDefaultPreviewElements();
				break;
			}
		}		
	}

	private void loadDefaultPreviewElements() {
		if(selectedPage != null){
			loadIcons();
			loadUnknownResourceElements();
			loadText();			
			
			gui.setIconPanelVisible(true);
			gui.setResourcePreviewVisible(true);
			gui.setDescriptionPanelVisible(true);
		}
		
	}
	
	private void loadUnknownResourceElements() {
		gui.setDefaultUnknownResourceImage();
		
	}

	private void loadVideoPreviewElements() {
		if(selectedPage != null){
			loadIcons();
			loadText();
			loadVideoResource();
			
			gui.setIconPanelVisible(true);
			gui.setResourcePreviewVisible(true);
			gui.setDescriptionPanelVisible(true);
		}
		
	}

	private void loadVideoResource() {
		gui.setDefaultVideoImage();
		
	}

	private void loadTextPreviewElements() {
		if(selectedPage != null){
			loadIcons();
			loadText();
			loadTextResource();
			
			gui.setIconPanelVisible(true);
			gui.setResourcePreviewVisible(true);
			gui.setDescriptionPanelVisible(true);
		}
		
	}

	private void loadTextResource() {
		IPageResource resource = selectedPage.getUsedResource();
		String text = resource.getContent();
		gui.setTextToPreviewPanel(text);
		
	}

	private void loadImagePreviewElements() {
		if(selectedPage != null){
			loadIcons();
			loadText();
			loadImageResource();			
			
			gui.setIconPanelVisible(true);
			gui.setResourcePreviewVisible(true);
			gui.setDescriptionPanelVisible(true);
		}	
	}

	private void loadImageResource() {
		if(selectedPage != null){
			IPageResource resource = selectedPage.getUsedResource();
			String locationAttributeName = resource.getExternalFileLocationAttributeName();
			EvoAttribute locationAttribute = resource.getAttributeByName(locationAttributeName);
			if(locationAttribute.isUsed()){
				String imagePath = locationAttribute.getAttributeValue();
				if(imagePath.length() != 0){
					gui.setImageToPreviewPanel(imagePath);
				}else{
					gui.setVisibleImageToDefault();
				}
			}else{
				gui.setVisibleImageToDefault();
			}
		}	
	}

	private void loadText() {
		if(selectedPage.getPageAttribute(Main.DESCRIPTION).isUsed()){
			String descriptionText = selectedPage.getPageAttribute(Main.DESCRIPTION).getAttributeValue();
			gui.setTextAreaText(descriptionText);
		}else{
			gui.setTextAreaText("");
		}		
	}	

	private void loadIcons() {
		if(selectedPage != null){
			boolean showConfirmationText = false;
			boolean showHasSubpages = false;
			for(EvoAttribute attribute : selectedPage.getPageAttributeSet()){
				if(attribute.getAttributeName().equals(Main.CONFIRMATION_TEXT)){
					showConfirmationText = attribute.isUsed();
				}
			}
			String showIndicator = gallery.getGalleryAttribute(Main.SHOW_INDICATOR).getAttributeValue();
			if(showIndicator.equalsIgnoreCase("true")){
				boolean pageHasChildren = !gallery.getChildPageList(selectedPage.getId()).isEmpty();
				if(pageHasChildren){
					showHasSubpages = true;
				}				
			}
			gui.showIcons(showHasSubpages, showConfirmationText);
		}		
	}

	@Override
	public void pageDataChanged() {
		loadAppropriatePagePreviewElements();		
	}

	@Override
	public void galleryDataChanged() {
		loadIcons();		
	}
}
