package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.events.PageChangeListener;
import hr.foi.air.evoEditor.gui.EditorMainGUI;
import hr.foi.air.evoEditor.gui.GalleryTreePanel;
import hr.foi.air.evoEditor.model.EvoTreeNodeObject;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;

/**
 * This class is controller for Gallery tree panel.
 */

public class GalleryTreeController implements TreeSelectionListener,ActionListener{
	
	IGallery gallery;
	IPage selectedPage;
	GalleryTreePanel gui;
	
	private ArrayList<PageChangeListener> listeners = new ArrayList<PageChangeListener>();
	
	public GalleryTreeController(IGallery gallery) {
		this.gallery = gallery;
	}
	
	public void setGuiObject(GalleryTreePanel gui){
		this.gui = gui;
	}

	public UUID getGalleryID() {
		return gallery.getID();
	}

	public ArrayList<IPage> getChildPages(UUID parentId) {
		return gallery.getChildPageList(parentId);
	}

	/**
	 * Returns the UUID of the selected page or null if no page is selected.
	 * @return Selected page or null
	 */
	public IPage getSelectedPage() {
		return selectedPage;
	}

    /**
     * Make specific page on tree selected.
     * @param page
     */
	public void selectNodeWithPage(IPage page) {
		gui.setTreeSelectionPath(getNodeTreePath(page));
	}
	
	/**
	 * Adds a new node to parent node with given ID
	 * @param parentPage
	 */
	private void addNodeToParent(IPage parentPage){
		UUID parentId;
		if(parentPage == null){
			parentId = getGalleryID();
		}else{
			parentId = parentPage.getId();
		}
		UUID childId = gallery.addBlankPage(parentId);
		IPage pageToAdd = gallery.findPageByID(childId);
		gui.addNodeToTree(pageToAdd);
		tablesChanged();
	}
	
	/**
	 * Does work when a node in a tree is selected.
	 */
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
	}
	
	/**
	 * Does work when a main control button is pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String btnText = ((JButton) e.getSource()).getText();
		switch(btnText){
		case(EditorMainGUI.ADD_PAGE_BTN_TEXT):
			if(selectedPage != null){
				addNodeToParent(gallery.findPageByID(selectedPage.getParentID()));
			}else{
				//Add node to gallery..
				addNodeToParent(null);
			}
			break;
		case(EditorMainGUI.ADD_SUBPAGE_BTN_TEXT):
			if(selectedPage != null){
				addNodeToParent(selectedPage);
			}			
			break;
		case(EditorMainGUI.MOVE_UP_BTN_TEXT):
			movePageUp();
			break;
		case(EditorMainGUI.MOVE_DOWN_BTN_TEXT):
			movePageDown();
			break;
		case(EditorMainGUI.PREVIOUS_PAGE_BTN_TEXT):
			selectPreviousPage();
			break;
		case(EditorMainGUI.NEXT_PAGE_BTN_TEXT):
			selectNextPage();
			break;
		case(EditorMainGUI.DELETE_PAGE_BTN_TEXT):
			deletePage();
			break;
		}
	}

	/**
	 * Returns the path to node with given Id.
	 * @param page
	 * @return TreePath
	 */
	private TreePath getNodeTreePath(IPage page) {
		DefaultMutableTreeNode treeRootNode = gui.getGalleryTreeRoot();
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = treeRootNode.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			EvoTreeNodeObject nodeObject = (EvoTreeNodeObject)node.getUserObject();
			if(page == null){
				break;
			}
			if (nodeObject.getObjectId() == page.getId()) {
				return new TreePath(node.getPath());
			}
		}
		return null;
	}

	/**
	 * Returns the UUID of the node that is above the node with the given ID.
	 * @param page
	 * @return
	 */
	private IPage getPreviousPage(IPage page){
		IPage previousPage = null;
		if(page != null){
			UUID pageId = page.getId();
			ArrayList<IPage> pageList = gallery.getChildPageList(page.getParentID());			
			
			int index = 0; //Get page index
			for(IPage p : pageList){
				if(p.getId() == pageId){
					break;
				}
				index++;
			}
			
			if(index > 0){
				index -= 1;
				previousPage = pageList.get(index);
			}else{
				previousPage = page;
			}
		}			
		return previousPage;
	}

	/**
	 * Returns the UUID of the node that is under the node with the given ID.
	 * @param page
	 * @return
	 */
	private IPage getNextPage(IPage page){
		IPage nextPage = null;
		if(page != null){
			UUID pageId = page.getId();
			ArrayList<IPage> pageChildList = gallery.getChildPageList(page.getParentID());			
			
			int index = 0; //Get page index
			for(IPage p : pageChildList){
				if(p.getId() == pageId){
					break;
				}
				index++;
			}			
			if(index < pageChildList.size() - 1){
				index += 1;
				nextPage = pageChildList.get(index);
			}else{
				nextPage = page;
			}		
		}
		return nextPage;
	}

	/**
	 * Deletes currently selected page.
	 */
	private void deletePage() {
		if(selectedPage != null){
			IPage pageToSelect = getPreviousPage(selectedPage);
			if(pageToSelect.getId() == selectedPage.getId()){
				pageToSelect = getNextPage(selectedPage);
			}
			gallery.deletePage(selectedPage.getId());
			gui.removePageFromTree(selectedPage);
			gui.reattachNodes(pageToSelect);
			selectNodeWithPage(pageToSelect);
			tablesChanged();
		}
	}

	/**
	 * Selects the next neighbor page of the selected page or reselects the currently
	 * selected page if no next page is available.
	 */
	private void selectNextPage() {
		if(selectedPage != null){
			IPage nextPage = getNextPage(selectedPage);
			selectNodeWithPage(nextPage);
		}
	}

	/**
	 * Selects the previous neighbor page of the selected page or reselects the currently
	 * selected page if no previous page is available.
	 */
	private void selectPreviousPage() {
		if(selectedPage != null){
			IPage previousPage = getPreviousPage(selectedPage);
			selectNodeWithPage(previousPage);
		}		
	}

	/**
	 * Moves the page down in the tree.
	 */
	private void movePageDown() {
		if(selectedPage != null){
			IPage selectedPage = this.selectedPage;
			gallery.decreaseOrderNumber(selectedPage.getId());
			gui.reattachNodes(selectedPage);
			this.selectedPage = selectedPage;
			selectNodeWithPage(this.selectedPage);		
		}		
	}

	/**
	 * Moves the page up in the tree.
	 */
	private void movePageUp() {
		if(selectedPage != null){
			IPage selectedPage = this.selectedPage;
			gallery.increaseOrderNumber(selectedPage.getId());
			gui.reattachNodes(selectedPage);
			this.selectedPage = selectedPage;
			selectNodeWithPage(this.selectedPage);		
		}			
	}
	
	private void tablesChanged(){
		for(PageChangeListener pl : listeners){
			pl.pageDataChanged();
		}
	}

	public void addTableChangeListener(PageChangeListener pagePreviewController) {
		listeners.add(pagePreviewController);		
	}
}
