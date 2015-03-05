package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.GalleryDataPanelController;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GalleryDataPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738314693024227945L;
	
	JSpinner numberPickerTransparencyValue;
	JTable tblGalleryAttributes;
	JSlider transparencySlider;
	ArrayList<String> unchangeableElements;
	GalleryDataPanelController controller;
	
	
	/**
	 * Create the panel.
	 */
	public GalleryDataPanel(GalleryDataPanelController controller) {
		this.controller = controller;
		initialize();
	}
	
	private void initialize() {
		unchangeableElements = new ArrayList<String>();
		super.setMinimumSize(new Dimension(350, 100));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblGallery = new JLabel("Gallery");
		super.add(lblGallery);
		
		JSeparator separator = new JSeparator();
		super.add(separator);
		
		JPanel GalleryAttributesPanel = new JPanel();
		GalleryAttributesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		GalleryAttributesPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		super.add(GalleryAttributesPanel);
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Attribute name");
		model.addColumn("Attribute value");
		
		JSeparator separator_1 = new JSeparator();
		super.add(separator_1);
		
		JLabel lblTransparency = new JLabel("Transparency");
		super.add(lblTransparency);
		
		unchangeableElements.add("transparency");
		
		SpinnerNumberModel spinModel = new SpinnerNumberModel(0.0, 0.0, 256.0, 1.0);
		numberPickerTransparencyValue = new JSpinner(spinModel);
		numberPickerTransparencyValue.addChangeListener(controller);
		numberPickerTransparencyValue.setName("transparency");
		super.add(numberPickerTransparencyValue);
		numberPickerTransparencyValue.setMaximumSize(new Dimension(40, 20));
		
		transparencySlider = new JSlider();
		transparencySlider.setMinorTickSpacing(1);
		transparencySlider.setValue(128);
		transparencySlider.setMaximum(256);
		transparencySlider.addChangeListener(controller);
		super.add(transparencySlider);
		
		JSeparator separator_2 = new JSeparator();
		super.add(separator_2);
		
		JCheckBox chckbxGalleryRepeatOption = new JCheckBox("Repeat");
		chckbxGalleryRepeatOption.setName("repeat");
		super.add(chckbxGalleryRepeatOption);
		
		unchangeableElements.add("repeat");
		
		JCheckBox chckbxShowIndicatorOption = new JCheckBox("Show indicator");
		chckbxShowIndicatorOption.setName("showIndicator");
		super.add(chckbxShowIndicatorOption);
		
		unchangeableElements.add("showIndicator");
		
		JButton btnSaveGalleryData = new JButton("Save gallery data");
		btnSaveGalleryData.setName("saveGalleryData");
		btnSaveGalleryData.addActionListener(controller);
		super.add(btnSaveGalleryData);
		GridBagLayout gbl_GalleryAttributesPanel = new GridBagLayout();
		gbl_GalleryAttributesPanel.columnWidths = new int[]{1, 0, 0};
		gbl_GalleryAttributesPanel.rowHeights = new int[]{1, 0, 0};
		gbl_GalleryAttributesPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_GalleryAttributesPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		GalleryAttributesPanel.setLayout(gbl_GalleryAttributesPanel);
		
		JPanel AttributesPanel = new JPanel();
		GridBagConstraints gbc_AttributesPanel = new GridBagConstraints();
		gbc_AttributesPanel.fill = GridBagConstraints.BOTH;
		gbc_AttributesPanel.gridx = 0;
		gbc_AttributesPanel.gridy = 0;
		GalleryAttributesPanel.add(AttributesPanel, gbc_AttributesPanel);
		AttributesPanel.setLayout(new GridLayout(0, 1, 0, 0));
		tblGalleryAttributes = new JTable(model);
		tblGalleryAttributes.setName("galleryAttributes");
		AttributesPanel.add(tblGalleryAttributes);
		tblGalleryAttributes.setAlignmentY(Component.TOP_ALIGNMENT);
		tblGalleryAttributes.setAlignmentX(Component.LEFT_ALIGNMENT);
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblGallery, GalleryAttributesPanel, tblGalleryAttributes, lblTransparency}));
		
	}
	
	public void addAttributes(String attributeName, String attributeValue) {
		DefaultTableModel dtm = (DefaultTableModel) tblGalleryAttributes.getModel();
		dtm.addRow(new Object[] {attributeName, attributeValue});
	}

	public ArrayList<String> getUnchangeableElements() {
		return unchangeableElements;
	}

	public void setGalleryTransparency(String galleryTransparency) {
		int transparency = (galleryTransparency == "") ? 60 : Integer.parseInt(galleryTransparency);
		
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
