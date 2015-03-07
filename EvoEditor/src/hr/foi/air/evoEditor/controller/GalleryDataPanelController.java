package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.gui.GalleryDataPanel;
import hr.foi.air.evoEditor.model.interfaces.IGallery;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GalleryDataPanelController implements ChangeListener, ActionListener {
	
	private IGallery gallery;
	private GalleryDataPanel gui;

	public GalleryDataPanelController(IGallery gallery){
		this.gallery = gallery;
	}

	public void setGui(GalleryDataPanel gui) {
		this.gui = gui;
	}

	public void setInitialData() {
		for (String attributeName : gallery.getGalleryAttributeSet()) {
			if (!gui.getUnchangeableElements().contains(attributeName))
				gui.addAttributes(attributeName, gallery.getGalleryAttribute(attributeName));
		}
		
		gui.setGalleryTransparency(gallery.getGalleryAttribute("transparency"));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		if (e.getSource() instanceof JSpinner) {
			gui.setSlider(((Number)((JSpinner)e.getSource()).getValue()).intValue());
		} else if(e.getSource() instanceof JSlider) {
			gui.setSpinner((int)((JSlider)e.getSource()).getValue());
		}
	}

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
			
		}
		
		for (String attribute : gallery.getGalleryAttributeSet()) {
			System.out.println(attribute + ": " + gallery.getGalleryAttribute(attribute));
		}
	}
}
