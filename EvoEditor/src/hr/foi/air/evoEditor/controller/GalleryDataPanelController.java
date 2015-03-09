package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.events.GalleryChangeListener;
import hr.foi.air.evoEditor.gui.GalleryDataPanel;
import hr.foi.air.evoEditor.main.Main;
import hr.foi.air.evoEditor.model.EvoAttribute;
import hr.foi.air.evoEditor.model.interfaces.IGallery;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This is controller class for data panel on UI. This controller
 * update gallery attributes and update panel on which user can change
 * gallery attributes.
 */

public class GalleryDataPanelController implements ChangeListener, ActionListener {
	
	private IGallery gallery;
	private GalleryDataPanel gui;
	
	private ArrayList<GalleryChangeListener> listeners = new ArrayList<GalleryChangeListener>();


	public GalleryDataPanelController(IGallery gallery){
		this.gallery = gallery;
	}

	public void setGui(GalleryDataPanel gui) {
		this.gui = gui;
	}

    /**
     * This method create attributes fields on gallery attributes panel
     * and set values to default values in gallery object.
     */
	public void setInitialData() {
		for (EvoAttribute attr : gallery.getGalleryAttributeSet()) {
			if (!gui.getUnchangeableElements().contains(attr.getAttributeName()))
				gui.addAttributes(attr.getAttributeName(), attr.getAttributeValue());
		}
		
		gui.setGalleryTransparency(gallery.getGalleryAttribute(Main.TRANSPARENCY).getAttributeValue());
	}

    /**
     * This metode listen when user change transparency value,
     * whether with slider or with number spinner.
     * @param e
     */
	@Override
	public void stateChanged(ChangeEvent e) {
		
		if (e.getSource() instanceof JSpinner) {
			gui.setSlider(((Number)((JSpinner)e.getSource()).getValue()).intValue());
		} else if(e.getSource() instanceof JSlider) {
			gui.setSpinner((int)((JSlider)e.getSource()).getValue());
		}
	}

    /**
     * This method listen when user press button "Save gallery attributes",
     * and then save attributes from fields on UI to
     * gallery object.
     * @param e
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (((JButton)e.getSource()).getName().equals("saveGalleryData")) {
			Component[] components = gui.getComponents();
			
			for (Component component : components) {
				for (String attributeName : gui.getUnchangeableElements()) {
					if (component.getName() != null) {
						if (component.getName().equals(attributeName)) {
							String value = "";
							if (component instanceof JSpinner) {
								value = String.valueOf(((Number)((JSpinner)component).getValue()).intValue());
							} else if (component instanceof JCheckBox) {
								value = String.valueOf(((JCheckBox)component).isSelected());
							}
							gallery.setGalleryAttribute(component.getName(), value);
						}
					}
				}
			}
			
			for (int row = 0; row < gui.getTblGalleryAttributes().getRowCount(); row++) {
				gallery.setGalleryAttribute((String) gui.getTblGalleryAttributes().getValueAt(row, 0), (String) gui.getTblGalleryAttributes().getValueAt(row, 1));
			}
			galleryDataChanged();
		}		
	}

    /**
     * This method add controller for preview panel to list.
     * @param pagePreviewController
     */
	public void addGalleryDataChangeListener(GalleryChangeListener pagePreviewController){
		this.listeners.add(pagePreviewController);
	}

    /**
     * This method attach galleryDataChanged function to controller, so when
     * user change some gallery attribute, that function is called.
     */
	public void galleryDataChanged(){
		for(GalleryChangeListener listener : listeners){
			listener.galleryDataChanged();
		}
	}
}
