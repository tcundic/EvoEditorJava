package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.PagePreviewController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Panel for page preview.
 */
public class PagePreviewPanel extends JPanel {

	/**
	 * AutoGenerated ID
	 */
	private static final long serialVersionUID = 6775088016396378378L;
	
	public static final int DESCRIPTION_WIDTH = 465;
	public static final int DESCRIPTION_HEIGHT = 55;
	
//	public static final int GOOGLE_GLASS_WIDTH = 640;
//	public static final int GOOGLE_GLASS_HEIGHT = 360;	
	public static final int GOOGLE_GLASS_WIDTH = 360;
	public static final int GOOGLE_GLASS_HEIGHT = 202;
		
	JPanel resourcePreviewPanel;
	
	JPanel descriptionPanel;		
	JTextArea textArea;
	
	JPanel infoIconsPanel;
	ImageIcon needConfirmationIcon;	
	JLabel needConfirmationIconLabel;
	ImageIcon hasSubpagesIcon;
	JLabel hasSubpagesIconLabel;

	PagePreviewController controller;
	
	public PagePreviewPanel(PagePreviewController controller) {
		this.controller = controller;		
		initialize();
	}

	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setVisible(false);		
		descriptionPanel = new JPanel();
		descriptionPanel.setPreferredSize(new Dimension(DESCRIPTION_WIDTH, DESCRIPTION_HEIGHT));		
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea.setLineWrap(true);
		textArea.setRows(3);
		textArea.setColumns(65);
		descriptionPanel.add(textArea);	
		
		resourcePreviewPanel = new JPanel(new BorderLayout());
		resourcePreviewPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		resourcePreviewPanel.setMaximumSize(
				new Dimension(GOOGLE_GLASS_WIDTH, GOOGLE_GLASS_HEIGHT));
		resourcePreviewPanel.setPreferredSize(
				new Dimension(GOOGLE_GLASS_WIDTH, GOOGLE_GLASS_HEIGHT));
		
		infoIconsPanel = new JPanel();
		infoIconsPanel.setMaximumSize(new Dimension(GOOGLE_GLASS_WIDTH, 0));
		
		needConfirmationIconLabel = new JLabel(
				new ImageIcon("Resources/Icons/hasConfirmationText.gif"));
		needConfirmationIconLabel.setToolTipText("Page contains confirmation text attribute.");
		infoIconsPanel.add(needConfirmationIconLabel);
		
		hasSubpagesIconLabel = new JLabel(
				new ImageIcon("Resources/Icons/hasSubpages.gif"));
		hasSubpagesIconLabel.setToolTipText("Page has subpages");
		infoIconsPanel.add(hasSubpagesIconLabel);
				
		add(infoIconsPanel);
		infoIconsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		add(resourcePreviewPanel);
		add(descriptionPanel);
	}
	
	/**
	 * Makes a component visible.
	 * @param isEnabeled
	 */
	public void setPagePrevireVisible(boolean isEnabeled) {
		this.setVisible(isEnabeled);	
	}
	
	public void setIconPanelVisible(boolean isIconsVisible){
		infoIconsPanel.setVisible(isIconsVisible);
	}
	
	public void setResourcePreviewVisible(boolean isResourcePreviewVisible){
		resourcePreviewPanel.setVisible(isResourcePreviewVisible);
	}
	
	public void setDescriptionPanelVisible(boolean isDescriptionPanelVisible){
		descriptionPanel.setVisible(isDescriptionPanelVisible);
	}
	
	public void hideAllPanels() {
		descriptionPanel.setVisible(false);
		resourcePreviewPanel.setVisible(false);
		infoIconsPanel.setVisible(false);
	}
	
	public void makePanelVisible(boolean isVisible){
		this.setVisible(isVisible);
	}
	
	public void setTextAreaText(String text){
		textArea.setText(text);
	}

	/**
	 * Sets if hasSubpages or confirmationText icons are shown in GUI
	 * @param isSubpagesIconVisible
	 * @param isConfirmationTextVisible
	 */
	public void showIcons(boolean isSubpagesIconVisible, boolean isConfirmationTextVisible) {
		needConfirmationIconLabel.setVisible(isConfirmationTextVisible);
		hasSubpagesIconLabel.setVisible(isSubpagesIconVisible);		
	}
	
	/**
	 * Shows the image with the given path in the GUI.
	 * @param imagePath
	 */
	public void setImageToPreviewPanel(String imagePath){
		BufferedImage img;
		try {
			img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading given image: "+ imagePath);
			return;
		}
		Image scaledImg = img.getScaledInstance(GOOGLE_GLASS_WIDTH, GOOGLE_GLASS_HEIGHT, Image.SCALE_DEFAULT);
		ImageIcon scaledImgIcon = new ImageIcon(scaledImg);
		JLabel resourceImage = new JLabel(scaledImgIcon);
		showImage(resourceImage);
	}
	
	public void setTextToPreviewPanel(String text){
		JTextArea resourceText = new JTextArea();
		resourceText.setEditable(false);
		resourceText.setWrapStyleWord(true);
		resourceText.setFont(new Font("Monospaced", Font.PLAIN, 14));
		resourceText.setLineWrap(true);
		resourceText.setRows(10);
		resourceText.setColumns(65);
		resourceText.setText(text);
		showText(resourceText);
	}
	
	public void showText(JTextArea resourceText){
		resourcePreviewPanel.removeAll();
		resourcePreviewPanel.add(resourceText, BorderLayout.NORTH);
	}
	
	private void showImage(JLabel resourceImage){
		resourcePreviewPanel.removeAll();
		resourcePreviewPanel.add(resourceImage, BorderLayout.NORTH);
	}

	public void setVisibleImageToDefault() {
		setImageToPreviewPanel("Resources/Icons/defaultImage.gif");		
	}

	public void setDefaultVideoImage() {
		setImageToPreviewPanel("Resources/Icons/videoSelected.gif");		
	}

	public void setDefaultUnknownResourceImage() {
		setImageToPreviewPanel("Resources/Icons/UnknownResource.gif");		
	}
}
