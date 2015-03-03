package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.EvoEditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class GalleryDataPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738314693024227945L;
	
	JTextField textFieldTransparencyValue;
	JTable tblGalleryAttributes;
	JSlider transparencySlider;
	
	EvoEditor evoEditor;
	
	
	/**
	 * Create the panel.
	 */
	public GalleryDataPanel(EvoEditor evoEditor) {
		this.evoEditor = evoEditor;
		initialize();
	}
	
	private void initialize() {
		super.setMinimumSize(new Dimension(350, 100));
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblGallery = new JLabel("Gallery");
		super.add(lblGallery);
		
		JSeparator separator = new JSeparator();
		super.add(separator);
		
		JPanel GalleryAttributesPanel = new JPanel();
		super.add(GalleryAttributesPanel);
		GalleryAttributesPanel.setLayout(new BorderLayout(0, 0));
		
		tblGalleryAttributes = new JTable();
		GalleryAttributesPanel.add(tblGalleryAttributes, BorderLayout.CENTER);
		
		JSeparator separator_1 = new JSeparator();
		super.add(separator_1);
		
		JLabel lblTransparency = new JLabel("Transparency");
		super.add(lblTransparency);
		
		textFieldTransparencyValue = new JTextField();
		super.add(textFieldTransparencyValue);
		textFieldTransparencyValue.setColumns(3);
		textFieldTransparencyValue.setMaximumSize(new Dimension(40, 20));
		
		transparencySlider = new JSlider();
		transparencySlider.setMinorTickSpacing(1);
		transparencySlider.setValue(128);
		transparencySlider.setMaximum(256);
		super.add(transparencySlider);
		
		JSeparator separator_2 = new JSeparator();
		super.add(separator_2);
		
		JCheckBox chckbxGalleryRepeatOption = new JCheckBox("Repeat");
		super.add(chckbxGalleryRepeatOption);
		
		JCheckBox chckbxShowIndicatorOption = new JCheckBox("Show indicator");
		super.add(chckbxShowIndicatorOption);
		
		JButton btnSaveGalleryData = new JButton("Save gallery data");
		super.add(btnSaveGalleryData);
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblGallery, GalleryAttributesPanel, tblGalleryAttributes, lblTransparency}));
	}

}
