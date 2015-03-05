package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.PagePreviewPanel;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class PagePreviewController implements TreeSelectionListener{
	
	IGallery gallery;
	PagePreviewPanel gui;
	UUID selectedPageId;
	
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

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree)e.getSource();		
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(selectedNode != null){
			selectedPageId = (UUID) selectedNode.getUserObject();
			gui.setLblText(selectedPageId.toString());
		}else{
			selectedPageId = null;
		}  	
	}
	
	public void setInitialData() {
		gui.add(new ImagePanel());
	}
	
	private class ImagePanel extends Component {
		
		BufferedImage img;
		
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
		
		public ImagePanel() {
			try {
				img = ImageIO.read(new File("C:\\Users\\tomi\\DesktopAVL483_Hinweis_Reinigung_1_split1.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
