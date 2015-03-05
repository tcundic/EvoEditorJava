package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.EditorMainGUI;
import hr.foi.air.evoEditor.gui.GalleryTreePanel;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class GalleryTreeController implements TreeSelectionListener,ActionListener{
	
	IGallery gallery;
	GalleryTreePanel gui;
	UUID selectedPageId;
	
	
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
	 * @return
	 */
	public UUID getSelectedPageId() {
		return selectedPageId;
	}
	
	public void selectNodeWithPage(UUID pageId) {
		gui.setTreeSelectionPath(getNodeTreePath(pageId));
	}
	
	/**
	 * Returns the path to node with given Id.
	 * @param pageId
	 * @return
	 */
	private TreePath getNodeTreePath(UUID pageId) {
		DefaultMutableTreeNode treeRootNode = gui.getGalleryTreeRoot();
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = treeRootNode.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			if(pageId == null){
				break;
			}
			if (node.toString().equalsIgnoreCase(pageId.toString())) {
				return new TreePath(node.getPath());
			}
		}
		return null;
	}
	
	/**
	 * Returns the UUID of the node that is above the node with the given ID.
	 * @param pageId
	 * @return
	 */
	private UUID getPreviousNodeId(UUID pageId){
		IPage page = gallery.findPageByID(pageId);
		UUID previousNodeId;
		if(pageId != gallery.getID()){
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
				previousNodeId = pageList.get(index).getId();
			}else{
				previousNodeId = pageId;
			}
		}else{
			previousNodeId = gallery.getID();
		}		
		
		return previousNodeId;
	}	
	
	/**
	 * Returns the UUID of the node that is under the node with the given ID.
	 * @param pageId
	 * @return
	 */
	private UUID getNextNodeId(UUID pageId){
		IPage page = gallery.findPageByID(pageId);
		UUID nextNodeId = null;
		if(page != null){
			ArrayList<IPage> pageChildList = gallery.getChildPageList(page.getParentID());
			
			if(pageId != gallery.getID()){
				ArrayList<IPage> pageList = gallery.getChildPageList(page.getParentID());			
				
				int index = 0; //Get page index
				for(IPage p : pageList){
					if(p.getId() == pageId){
						break;
					}
					index++;
				}
				
				if(index < pageChildList.size() - 1){
					index += 1;
					nextNodeId = pageList.get(index).getId();
				}else{
					nextNodeId = pageId;
				}
			}else{
				nextNodeId = gallery.getID();
			}			
		}else{
			nextNodeId = pageId;
		}
		return nextNodeId;
	}	
	
	
	/**
	 * Adds a new node to parent node with given ID
	 * @param parrentId
	 */
	public void addNodeToParent(UUID parentId){
		UUID childId = gallery.addBlankPage(parentId);
		gui.addNodeToTree(parentId, childId);
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree)e.getSource();		
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(selectedNode != null){
			selectedPageId = (UUID) selectedNode.getUserObject();
		}else{
			selectedPageId = null;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String btnText = ((JButton) e.getSource()).getText();
		switch(btnText){
		case(EditorMainGUI.ADD_PAGE_BTN_TEXT):
			addNodeToParent(getGalleryID());
			break;
		case(EditorMainGUI.ADD_SUBPAGE_BTN_TEXT):
			if(selectedPageId != null){
				addNodeToParent(selectedPageId);
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

	private void deletePage() {
		if(selectedPageId != null){
			UUID nodeToSelectId = getPreviousNodeId(selectedPageId);
			if(nodeToSelectId == selectedPageId){
				nodeToSelectId = getNextNodeId(selectedPageId);
			}
			gallery.deletePage(selectedPageId);
			gui.removePageFromTree(selectedPageId);
			selectNodeWithPage(nodeToSelectId);
		}
	}

	private void selectNextPage() {
		if(selectedPageId != null){
			UUID nextPageId = getNextNodeId(selectedPageId);
			selectNodeWithPage(nextPageId);
		}
	}

	private void selectPreviousPage() {
		if(selectedPageId != null){
			UUID previousPageId = getPreviousNodeId(selectedPageId);
			selectNodeWithPage(previousPageId);
		}		
	}

	private void movePageDown() {
		if(selectedPageId != null){
			UUID selectedPageId = this.selectedPageId;
			
			IPage page = gallery.findPageByID(selectedPageId);
			if(page != null){
				gallery.decreaseOrderNumber(selectedPageId);
				gui.reattachNodes(page.getId());
			}	
			this.selectedPageId = selectedPageId;
			selectNodeWithPage(selectedPageId);		
		}
		
	}

	private void movePageUp() {
		if(selectedPageId != null){
			UUID selectedPageId = this.selectedPageId;
			
			IPage page = gallery.findPageByID(selectedPageId);
			if(page != null){
				gallery.increaseOrderNumber(selectedPageId);
				gui.reattachNodes(page.getId());
			}	
			this.selectedPageId = selectedPageId;
			selectNodeWithPage(selectedPageId);		
		}		
	}
}
