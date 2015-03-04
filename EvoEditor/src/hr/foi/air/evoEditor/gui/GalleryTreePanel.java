package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.GalleryTreeController;
import hr.foi.air.evoEditor.model.interfaces.IPage;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class GalleryTreePanel extends JPanel {

	/**
	 * Generated ID
	 */
	private static final long serialVersionUID = 1231909779810617773L;
	
	GalleryTreeController controller;
	DefaultMutableTreeNode galleryTreeRoot;
	JTree tree;
	
	public GalleryTreePanel(GalleryTreeController controller) {
		this.controller = controller;
		initialize();
	}

	private void initialize() {
		super.setLayout(new BorderLayout());
		galleryTreeRoot = new DefaultMutableTreeNode(controller.getGalleryID());
		tree = new JTree(galleryTreeRoot);
		tree.setShowsRootHandles(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);		
		JScrollPane scrollPanePageTree = new JScrollPane(tree);		
		super.add(scrollPanePageTree, BorderLayout.CENTER);
	}
	
	public DefaultMutableTreeNode getGalleryTreeRoot(){
		return galleryTreeRoot;
	}
	
	public void addTreeSelectionListener(TreeSelectionListener listener){
		tree.addTreeSelectionListener(listener);
	}
	
	/**
	 * A new node is added to the tree.
	 * @param parentId
	 * @param childId
	 */
	public void addNodeToTree(UUID parentId, UUID childId){	
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childId);
		DefaultMutableTreeNode parentNode = findNode(parentId);
		int[] childIndices = {0};
		
		if(parentNode != null){
			parentNode.add(childNode);
			childIndices[0] = parentNode.getChildCount() - 1;
			model.nodesWereInserted(parentNode, childIndices);			
		}				
	}
	
	/**
	 * A  node is removed from the tree.
	 * @param parentId
	 * @param childId
	 */
	public void removePageFromTree(UUID pageToDeleteId) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		
		if((UUID)galleryTreeRoot.getUserObject() != pageToDeleteId){
			DefaultMutableTreeNode nodeToDelete = findNode(pageToDeleteId);
			model.removeNodeFromParent(nodeToDelete);
		}		
	}

	/**
	 * Builds the tree using recursion and the current gallery object
	 * @param node
	 */
	private void buildGalleryTreeFromNode(DefaultMutableTreeNode node) {
		UUID parentId = (UUID)node.getUserObject();
		ArrayList<IPage> childPagesList = controller.getChildPages(parentId);
		
		// recursion 
		for(IPage page : childPagesList){
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(page.getId());
			node.add(childNode);
			buildGalleryTreeFromNode(childNode);
		}		
	}
		
	private DefaultMutableTreeNode findNode(UUID nodeId){
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = galleryTreeRoot.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			if (node.toString().equalsIgnoreCase(String.valueOf(nodeId))) {
				return node;
			}
		}
		return null;
	}
	
	public void reattachNodes(UUID nodeId){
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode node = findNode(nodeId);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node.getParent();
		
		parentNode.removeAllChildren();
		buildGalleryTreeFromNode(parentNode);
		model.reload(galleryTreeRoot);		
	}
	
	public void setTreeSelectionPath(TreePath path) {
		tree.setSelectionPath(path);
	}
}
