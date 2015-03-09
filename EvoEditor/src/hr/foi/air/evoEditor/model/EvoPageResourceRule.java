package hr.foi.air.evoEditor.model;

import hr.foi.air.evoEditor.model.interfaces.IPageResource;
import hr.foi.air.evoEditor.model.interfaces.IPageResourceRule;

import java.util.ArrayList;

/**
 * Defines the rules on which page resources can be used by a single page. This {@literal <}br/{@literal >}
 * Only one resource can be used at the time.
 * 
 * @author Tadija
 *
 */
public class EvoPageResourceRule implements IPageResourceRule{
	
	/**
	 * Only one resource can be visible at the time.
	 * 
	 * @param pageResourceList List of all page resources
	 * @param pageResourceName The name of the visible resource
	 */
	@Override
	public void setResourceAsUsable(ArrayList<IPageResource> pageResourceList, String pageResourceName){
		for(IPageResource pageResource : pageResourceList){
			if(pageResource.getName().compareTo(pageResourceName) == 0){
				pageResource.setUsed(true);
			}else{
				pageResource.setUsed(false);
			}
		}
	}

}
