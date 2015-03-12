package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.GalleryDataController;

import javax.swing.*;
import javax.swing.Box.Filler;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Panel with gallery attributes.
 */
public class GalleryDataPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738314693024227945L;
	
	JSpinner numberPickerTransparencyValue;
	JTable tblGalleryAttributes;
	JSlider transparencySlider;
	ArrayList<String> unchangeableElements;
	GalleryDataController controller;
	
	
	/**
	 * Create the panel.
	 */
	public GalleryDataPanel(GalleryDataController controller) {
		this.controller = controller;
		initialize();
	}
	
	private void initialize() {
		unchangeableElements = new ArrayList<String>();
		super.setMinimumSize(new Dimension(350, 100));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Box.Filler spacing = (Filler) Box.createVerticalGlue();
		spacing.changeShape(new Dimension(1, 10), new Dimension(1, 10), spacing.getMaximumSize());
		
		JLabel lblGallery = new JLabel("Gallery");
		lblGallery.setAlignmentX(CENTER_ALIGNMENT);
		super.add(lblGallery);
		super.add(Box.createRigidArea(new Dimension(10,0)));
		
		JSeparator separator = new JSeparator();
		super.add(separator);
		super.add(Box.createRigidArea(new Dimension(10,0)));
		
		JPanel GalleryAttributesPanel = new JPanel();
		GalleryAttributesPanel.setAlignmentX(CENTER_ALIGNMENT);
		super.add(GalleryAttributesPanel);
		super.add(Box.createRigidArea(new Dimension(10,0)));
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Attribute name");
		model.addColumn("Attribute value");
		
		tblGalleryAttributes = new JTable(model);
		tblGalleryAttributes.setName("galleryAttributes");
		super.add(tblGalleryAttributes);
		super.add(spacing);
		
		JSeparator separator_1 = new JSeparator();
		super.add(separator_1);
		super.add(spacing);
		
		JLabel lblTransparency = new JLabel("Transparency");
		lblTransparency.setAlignmentX(CENTER_ALIGNMENT);
		super.add(lblTransparency);		
		unchangeableElements.add("transparency");
		super.add(spacing);
		
		SpinnerNumberModel spinModel = new SpinnerNumberModel(0.0, 0.0, 255.0, 1.0);
		numberPickerTransparencyValue = new JSpinner(spinModel);
		numberPickerTransparencyValue.addChangeListener(controller);
		numberPickerTransparencyValue.setName("transparency");
		numberPickerTransparencyValue.setAlignmentX(CENTER_ALIGNMENT);
		super.add(numberPickerTransparencyValue);
		numberPickerTransparencyValue.setMaximumSize(new Dimension(40, 20));
		super.add(spacing);
		
		transparencySlider = new JSlider();
		transparencySlider.setMinorTickSpacing(1);
		transparencySlider.setValue(128);
		transparencySlider.setMaximum(255);
		transparencySlider.addChangeListener(controller);
		transparencySlider.setAlignmentX(CENTER_ALIGNMENT);
		super.add(transparencySlider);
		super.add(spacing);
		
		JSeparator separator_2 = new JSeparator();
		super.add(separator_2);
		super.add(spacing);
		
		JCheckBox chckbxGalleryRepeatOption = new JCheckBox("Repeat");
		chckbxGalleryRepeatOption.setName("repeat");
		chckbxGalleryRepeatOption.setAlignmentX(CENTER_ALIGNMENT);
		super.add(chckbxGalleryRepeatOption);
		
		unchangeableElements.add("repeat");
		
		JCheckBox chckbxShowIndicatorOption = new JCheckBox("Show indicator");
		chckbxShowIndicatorOption.setName("showIndicator");
		chckbxShowIndicatorOption.setAlignmentX(CENTER_ALIGNMENT);
		super.add(chckbxShowIndicatorOption);
		
		unchangeableElements.add("showIndicator");
		super.add(spacing);
		
		JButton btnSaveGalleryData = new JButton("Save gallery data");
		btnSaveGalleryData.setName("saveGalleryData");
		btnSaveGalleryData.addActionListener(controller);
		btnSaveGalleryData.setAlignmentX(CENTER_ALIGNMENT);
		super.add(btnSaveGalleryData);
		
		Box.Filler glue = (Filler) Box.createVerticalGlue();
	    glue.changeShape(glue.getMinimumSize(), 
	                    new Dimension(0, 270), // make glue greedy
	                    glue.getMaximumSize());
	    super.add(glue);		
	}
	
	public void addAttributes(String attributeName, String attributeValue) {
		DefaultTableModel dtm = (DefaultTableModel) tblGalleryAttributes.getModel();
		dtm.addRow(new Object[] {attributeName, attributeValue});
	}

	public ArrayList<String> getUnchangeableElements() {
		return unchangeableElements;
	}

	public void setGalleryTransparency(String galleryTransparency) {
		int transparency = (galleryTransparency == "") ? 0 : Integer.parseInt(galleryTransparency);
		
		numberPickerTransparencyValue.setValue(transparency);
		transparencySlider.setValue(transparency);
	}
	
	public void setSpinner(int transparency) {
		numberPickerTransparencyValue.setValue(transparency);
	}
	
	public void setSlider(int transparency) {
		transparencySlider.setValue(transparency);
	}

	public JTable getTblGalleryAttributes() {
		return tblGalleryAttributes;
	}
}
