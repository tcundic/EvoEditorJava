package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.EvoEditor;
import hr.foi.air.evoEditor.model.interfaces.IPage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class EditorMainGUI implements TreeSelectionListener{
	
	private EvoEditor evoEditor;
	private UUID selectedPageId;

	private JFrame frmEvoeditor;
	private JTable tblGalleryAttributes;
	private JTextField textFieldTransparencyValue;
	private JTable tablePageAttributesAndResources;
	private JTree tree;
	private JLabel lblPreviewText;
	DefaultMutableTreeNode galleryTreeRoot;

	/**
	 * Create the application.
	 * @param evoEditor 
	 */
	public EditorMainGUI(EvoEditor evoEditor) {
		this.evoEditor = evoEditor;
		initialize();
	}
	
	public void setVisible(boolean isVisible){
		frmEvoeditor.setVisible(isVisible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEvoeditor = new JFrame();
		frmEvoeditor.setResizable(false);
		frmEvoeditor.setTitle("EvoEditor");
		frmEvoeditor.setBounds(70, 50, 1150, 630);
		frmEvoeditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEvoeditor.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel controlItemsPanel = new JPanel();
		frmEvoeditor.getContentPane().add(controlItemsPanel, BorderLayout.NORTH);
		controlItemsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAddPage = new JButton("Add page");
		btnAddPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnAddPageClick();
			}
		});
		controlItemsPanel.add(btnAddPage);
		
		JButton btnAddSubpage = new JButton("Add subpage");
		btnAddSubpage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnAddSubpageClick();
			}
		});
		controlItemsPanel.add(btnAddSubpage);
		
		JButton btnSelectNext = new JButton("Next page");
		btnSelectNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnNextPageClick();
			}
		});
		
		JButton btnDeletePage = new JButton("Delete page");
		btnDeletePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnDeletePageClick();
			}
		});
		controlItemsPanel.add(btnDeletePage);
		controlItemsPanel.add(btnSelectNext);
		
		JButton btnSelectPrevious = new JButton("Previous page");
		btnSelectPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnPreviousPageClick();
			}
		});
		controlItemsPanel.add(btnSelectPrevious);
		
		JButton btnMoveUp = new JButton("Move up");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnMoveUpClick();
			}
		});
		controlItemsPanel.add(btnMoveUp);
		
		JButton btnMoveDown = new JButton("Move down");
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnMoveDownClick();
			}
		});
		controlItemsPanel.add(btnMoveDown);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnExportClick();
			}
		});
		controlItemsPanel.add(btnExport);
		
		//East component
		JPanel galleryDataPanel = new GalleryDataPanel(evoEditor.getGalleryDataPanelController());
		frmEvoeditor.getContentPane().add(galleryDataPanel, BorderLayout.EAST);		
		
		JPanel panelCenter = new JPanel();
		frmEvoeditor.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		JPanel panelPreview = new JPanel();
		panelCenter.add(panelPreview);
		
		lblPreviewText = new JLabel("preview text");
		panelPreview.add(lblPreviewText);
		
		JPanel panelPageData = new JPanel();
		panelCenter.add(panelPageData);
		panelPageData.setLayout(new BoxLayout(panelPageData, BoxLayout.X_AXIS));
		
		tablePageAttributesAndResources = new JTable();
		panelPageData.add(tablePageAttributesAndResources);
		
		JPanel panelPageResourceChooser = new JPanel();
		panelPageData.add(panelPageResourceChooser);
		panelPageResourceChooser.setLayout(new BoxLayout(panelPageResourceChooser, BoxLayout.Y_AXIS));
		
		JLabel lblResource = new JLabel("Resource");
		panelPageResourceChooser.add(lblResource);
		
		JComboBox comboBoxPageResource = new JComboBox();
		panelPageResourceChooser.add(comboBoxPageResource);
		
		JButton btnAddPageResource = new JButton("Add resource");
		panelPageResourceChooser.add(btnAddPageResource);
		
		JButton btnSelectPageResource = new JButton("Select resource");
		panelPageResourceChooser.add(btnSelectPageResource);
		
		JButton btnSavePageData = new JButton("Save page data");
		panelPageResourceChooser.add(btnSavePageData);
		
		JPanel panelPageTree = new JPanel();
		frmEvoeditor.getContentPane().add(panelPageTree, BorderLayout.WEST);
		panelPageTree.setLayout(new BorderLayout(0, 0));		
		
		galleryTreeRoot = new DefaultMutableTreeNode(evoEditor.getGalleryID());
		tree = new JTree(galleryTreeRoot);
		tree.setShowsRootHandles(true);
		// only one node can be selected
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		JScrollPane scrollPanePageTree = new JScrollPane(tree);		
		panelPageTree.add(scrollPanePageTree, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frmEvoeditor.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
	}

	protected void onBtnDeletePageClick() {
		if(selectedPageId != null){
			evoEditor.btnDeletePageClicked(selectedPageId);
		}
		
	}

	protected void onBtnExportClick() {
		// TODO Auto-generated method stub
		
	}

	protected void onBtnMoveDownClick() {
		if(selectedPageId != null){
			UUID selectedPageId = this.selectedPageId;
			evoEditor.btnMoveDownClicked(selectedPageId);
			this.selectedPageId = selectedPageId;
			reselectNode();
			
		}
		
	}

	protected void onBtnMoveUpClick() {
		if(selectedPageId != null){
			UUID selectedPageId = this.selectedPageId;
			evoEditor.btnBtnMoveUpClicked(selectedPageId);
			this.selectedPageId = selectedPageId;
			reselectNode();
			
		}
	}

	protected void onBtnPreviousPageClick() {
		// TODO Auto-generated method stub
		
	}

	protected void onBtnNextPageClick() {
		// TODO Auto-generated method stub
		
	}

	protected void onBtnAddSubpageClick() {
		if(selectedPageId != null && selectedPageId != galleryTreeRoot.getUserObject()){
			evoEditor.btnAddSubpageClicked(selectedPageId);
		}	
	}

	protected void onBtnAddPageClick() {
		evoEditor.btnAddPageClicked();
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
		ArrayList<IPage> childPagesList = evoEditor.getChildPages(parentId);
		
		// recursion 
		for(IPage page : childPagesList){
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(page.getId());
			node.add(childNode);
			buildGalleryTreeFromNode(childNode);
		}		
	}
	
	/**
	 * Find parent node using recursion
	 * @param parentId
	 * @param root
	 * @return
	 */
	private DefaultMutableTreeNode findNodeRecursion(UUID nodeId, DefaultMutableTreeNode root){
		if((UUID)root.getUserObject() == nodeId){
			return root;
		}
		
		DefaultMutableTreeNode node = null;
		
		@SuppressWarnings("rawtypes")
		Enumeration children = root.children();
		while(children.hasMoreElements()){
			node = findNodeRecursion(nodeId, (DefaultMutableTreeNode)children.nextElement());
            if(node != null){
            	break;
            }            
        }		
		return node;
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
	
	public void reselectNode() {
		tree.setSelectionPath(getSelectedNodeTreePath());
	}
	
	private TreePath getSelectedNodeTreePath() {
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = galleryTreeRoot.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			if (node.toString().equalsIgnoreCase(String.valueOf(selectedPageId))) {
				return new TreePath(node.getPath());
			}
		}
		return null;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		//Returns the last path element of the selection.
	    //This method is useful only when the selection model allows a single selection.
	    DefaultMutableTreeNode node = 
	    		(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

	    if (node == null){
	    	selectedPageId = null;
	    }else{
	    	// a node (page) is selected
	    	selectedPageId = (UUID) node.getUserObject();
	    	String resourceName = evoEditor.getSelectedPageResourceName(selectedPageId);
	    	int orderNumber = evoEditor.getSelectedPageOrderNumber(selectedPageId);
	    	this.lblPreviewText.setText(resourceName + " Order No. " + orderNumber);
	    }	    	
	}	
}
