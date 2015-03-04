package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.EvoEditor;
import hr.foi.air.evoEditor.controller.GalleryTreeController;
import hr.foi.air.evoEditor.controller.PageDataController;
import hr.foi.air.evoEditor.controller.PagePreviewController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class EditorMainGUI{
	
	public static final String ADD_PAGE_BTN_TEXT = "Add page";
	public static final String ADD_SUBPAGE_BTN_TEXT = "Add subpage";
	public static final String NEXT_PAGE_BTN_TEXT = "Next page";
	public static final String PREVIOUS_PAGE_BTN_TEXT = "Previous page";
	public static final String DELETE_PAGE_BTN_TEXT = "Delete page";
	public static final String MOVE_UP_BTN_TEXT = "Move up";
	public static final String MOVE_DOWN_BTN_TEXT = "Move down";
	public static final String EXPORT_BTN_TEXT = "Export";
	public static final String ADD_RESOURCE_BTN_TEXT = "Add resource";
	public static final String SELECT_RESOURCE_BTN_TEXT = "Select resource";
	public static final String SAVE_PAGE_DATA_BTN_TEXT = "Save page data";
	
	private EvoEditor evoEditor;

	private JFrame frmEvoeditor;

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
		
		JMenuBar menuBar = new JMenuBar();
		frmEvoeditor.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JPanel controlItemsPanel = new JPanel();
		frmEvoeditor.getContentPane().add(controlItemsPanel, BorderLayout.NORTH);
		controlItemsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//EAST component
		JPanel galleryDataPanel = new GalleryDataPanel(evoEditor.getGalleryDataController());
		frmEvoeditor.getContentPane().add(galleryDataPanel, BorderLayout.EAST);		
		
		//CENTER component
		JPanel panelCenter = new JPanel();		
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		frmEvoeditor.getContentPane().add(panelCenter, BorderLayout.CENTER);
		
		PagePreviewController pagePreviewController = evoEditor.getPagePreviewController();
		PagePreviewPanel panelPreview = new PagePreviewPanel(pagePreviewController);
		pagePreviewController.setGuiObject(panelPreview);
		panelCenter.add(panelPreview);
		
		PageDataController pageDataController = evoEditor.getPageDataController();
		PageDataPanel pageDataPanel  = new PageDataPanel(pageDataController);
		pageDataController.setGui(pageDataPanel);
		panelCenter.add(pageDataPanel);
		pageDataPanel.setLayout(new BoxLayout(pageDataPanel, BoxLayout.X_AXIS));	
		
		// WEST
		GalleryTreeController galleryTreePanelController = evoEditor.getGalleryTreePanelController();
		GalleryTreePanel galleryTreePanel = new GalleryTreePanel(galleryTreePanelController);
		galleryTreePanelController.setGuiObject(galleryTreePanel);	
		galleryTreePanel.addTreeSelectionListener(galleryTreePanelController);
		frmEvoeditor.getContentPane().add(galleryTreePanel, BorderLayout.WEST);
		
		// NORTH
		JButton btnAddPage = new JButton(ADD_PAGE_BTN_TEXT);
		btnAddPage.addActionListener(galleryTreePanelController);
		controlItemsPanel.add(btnAddPage);
		
		JButton btnAddSubpage = new JButton(ADD_SUBPAGE_BTN_TEXT);
		btnAddSubpage.addActionListener(galleryTreePanelController);
		controlItemsPanel.add(btnAddSubpage);		
		
		JButton btnSelectPrevious = new JButton(PREVIOUS_PAGE_BTN_TEXT);
		btnSelectPrevious.addActionListener(galleryTreePanelController);
		controlItemsPanel.add(btnSelectPrevious);
		
		JButton btnSelectNext = new JButton(NEXT_PAGE_BTN_TEXT);
		btnSelectNext.addActionListener(galleryTreePanelController);
		controlItemsPanel.add(btnSelectNext);
		
		JButton btnDeletePage = new JButton(DELETE_PAGE_BTN_TEXT);
		btnDeletePage.addActionListener(galleryTreePanelController);
		controlItemsPanel.add(btnDeletePage);
		
		JButton btnMoveUp = new JButton(MOVE_UP_BTN_TEXT);
		btnMoveUp.addActionListener(galleryTreePanelController);
		controlItemsPanel.add(btnMoveUp);
		
		JButton btnMoveDown = new JButton(MOVE_DOWN_BTN_TEXT);
		btnMoveDown.addActionListener(galleryTreePanelController);
		controlItemsPanel.add(btnMoveDown);
		
		JButton btnExport = new JButton(EXPORT_BTN_TEXT);
		btnExport.addActionListener(galleryTreePanelController);
		controlItemsPanel.add(btnExport);
	}
}
