package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.EvoEditor;
import hr.foi.air.evoEditor.controller.GalleryDataPanelController;
import hr.foi.air.evoEditor.controller.GalleryTreeController;
import hr.foi.air.evoEditor.controller.PageDataController;
import hr.foi.air.evoEditor.controller.PagePreviewController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
	
	public static final int GUI_WIDTH = 1160;
	public static final int GUI_HEIGHT = 660;
	
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
		frmEvoeditor.setBounds(20, 20, GUI_WIDTH, GUI_HEIGHT);
		frmEvoeditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEvoeditor.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel controlItemsPanel = new JPanel();
		frmEvoeditor.getContentPane().add(controlItemsPanel, BorderLayout.NORTH);
		controlItemsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//CENTER component
		JPanel panelCenter = new JPanel();		
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		frmEvoeditor.getContentPane().add(panelCenter, BorderLayout.CENTER);
		
		//CENTER - top
		PagePreviewController pagePreviewController = evoEditor.getPagePreviewController();
		PagePreviewPanel panelPreview = new PagePreviewPanel(pagePreviewController);
		panelPreview.setPreferredSize(new Dimension(GUI_WIDTH, GUI_HEIGHT/2));
		pagePreviewController.setGuiObject(panelPreview);
		panelCenter.add(panelPreview);
		
		//CENTER - bottom
		PageDataController pageDataController = evoEditor.getPageDataController();
		PageDataPanel pageDataPanel  = new PageDataPanel(pageDataController);
		pageDataPanel.setPreferredSize(new Dimension(GUI_WIDTH, GUI_HEIGHT/2));
		pageDataController.setGui(pageDataPanel);
		pageDataController.addTableChangeListener(pagePreviewController);
		panelCenter.add(pageDataPanel);	
		
		// WEST
		GalleryTreeController galleryTreePanelController = evoEditor.getGalleryTreePanelController();
		GalleryTreePanel galleryTreePanel = new GalleryTreePanel(galleryTreePanelController);
		galleryTreePanelController.setGuiObject(galleryTreePanel);	
		galleryTreePanel.addTreeSelectionListener(galleryTreePanelController);
		galleryTreePanel.addTreeSelectionListener(pageDataController);
		galleryTreePanel.addTreeSelectionListener(pagePreviewController);
		frmEvoeditor.getContentPane().add(galleryTreePanel, BorderLayout.WEST);
		
		//EAST component
		GalleryDataPanelController galleryDataPanelController = evoEditor.getGalleryDataController();
		GalleryDataPanel galleryDataPanel = new GalleryDataPanel(galleryDataPanelController);
		galleryDataPanelController.setGui(galleryDataPanel);
		galleryDataPanelController.setInitialData();
		galleryDataPanelController.addGalleryDataChangeListener(pagePreviewController);
		frmEvoeditor.getContentPane().add(galleryDataPanel, BorderLayout.EAST);
		
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
