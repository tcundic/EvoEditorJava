package hr.foi.air.evoEditor.gui;

import hr.foi.air.evoEditor.controller.PagePreviewController;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PagePreviewPanel extends JPanel{

	/**
	 * AutoGenerated ID
	 */
	private static final long serialVersionUID = 6775088016396378378L;

	PagePreviewController controller;
	
	private JLabel lblPreviewText;

	public PagePreviewPanel(PagePreviewController controller) {
		this.controller = controller;
		initialize();
	}

	private void initialize() {
		lblPreviewText = new JLabel("preview text");
		super.add(lblPreviewText);
	}
	
	public void setLblText(String text){
		this.lblPreviewText.setText(text);
	}	
}