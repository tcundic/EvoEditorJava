package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.GalleryTreeController;
import hr.foi.air.evoEditor.model.EvoTreeNodeObject;
import hr.foi.air.evoEditor.model.interfaces.IPage;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;

public class GalleryTreePanel extends JPanel {

	/**
	 * Generated ID
	 */
	private static final long serialVersionUID = 1231909779810617773L;
	
	private static final String GALLERY_NAME = "Gallery";
	private static final String PAGE_NAME = "Page";
	
	public static final int TREE_MIN_WIDTH = 200;
	public static final int TREE_MIN_HEIGHT = 20;
	
	GalleryTreeController controller;
	DefaultMutableTreeNode galleryTreeRoot;
	JTree tree;
	
	public GalleryTreePanel(GalleryTreeController controller) {
		this.controller = controller;
		initialize();
	}

	/**
	 * Initializes all GUI components.
	 */
	private void initialize() {
		super.setLayout(new BorderLayout());
		EvoTreeNodeObject rootNode = new EvoTreeNodeObject(GALLERY_NAME, 0, controller.getGalleryID());
		galleryTreeRoot = new DefaultMutableTreeNode(rootNode);
		tree = new JTree(galleryTreeRoot);
		tree.setShowsRootHandles(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);		
		JScrollPane scrollPanePageTree = new JScrollPane(tree);
		scrollPanePageTree.setPreferredSize(new Dimension(TREE_MIN_WIDTH, TREE_MIN_HEIGHT));
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
	 * @param page Node that contains this page will be the parent node of the newly created node.
	 */
	public void addNodeToTree(IPage page){	
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		EvoTreeNodeObject nodeObject = new EvoTreeNodeObject(PAGE_NAME, page.getOrderNumber(), page.getId());
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(nodeObject);
		DefaultMutableTreeNode parentNode = findNode(page.getParentID());
		int[] childIndices = {0};
		
		if(parentNode != null){
			parentNode.add(childNode);
			childIndices[0] = parentNode.getChildCount() - 1;
			model.nodesWereInserted(parentNode, childIndices);			
		}				
	}
	
	/**
	 * A  node is removed from the tree.
	 * @param page
	 */
	public void removePageFromTree(IPage page) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		EvoTreeNodeObject rootNodeObject = (EvoTreeNodeObject)galleryTreeRoot.getUserObject();
		
		if(rootNodeObject.getObjectId() != page.getId()){
			DefaultMutableTreeNode nodeToDelete = findNode(page.getId());
			if(nodeToDelete != null){
				model.removeNodeFromParent(nodeToDelete);
			}			
		}		
	}

	/**
	 * Builds the tree using recursion and the current gallery object
	 * @param node
	 */
	private void buildGalleryTreeFromNode(DefaultMutableTreeNode node) {
		EvoTreeNodeObject parentNode = (EvoTreeNodeObject)node.getUserObject();
		ArrayList<IPage> childPagesList = controller.getChildPages(parentNode.getObjectId());
		
		// recursion 
		for(IPage page : childPagesList){
			EvoTreeNodeObject nodeObject = new EvoTreeNodeObject(PAGE_NAME, page.getOrderNumber(), page.getId());
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(nodeObject);
			node.add(childNode);
			buildGalleryTreeFromNode(childNode);
		}		
	}
	
	/**
	 * Finds a node in the tree that contains the user object with the given ID.
	 * @param nodeId Id that is looked for in the tree.
	 * @return node that contains the user object with given ID
	 */
	private DefaultMutableTreeNode findNode(UUID nodeId){
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = galleryTreeRoot.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			EvoTreeNodeObject nodeObject = (EvoTreeNodeObject) node.getUserObject();
			if (nodeObject.getObjectId() == nodeId) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Reattach all child nodes and their nodes of the node that contains the given page.
	 * @param page
	 */
	public void reattachNodes(IPage page){
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode node = findNode(page.getId());
		if(node != null){
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node.getParent();			
			parentNode.removeAllChildren();
			buildGalleryTreeFromNode(parentNode);
			model.reload(galleryTreeRoot);
		}				
	}
	
	public void setTreeSelectionPath(TreePath path) {
		tree.setSelectionPath(path);
	}
}
