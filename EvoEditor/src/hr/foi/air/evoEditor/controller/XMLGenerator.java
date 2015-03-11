package hr.foi.air.evoEditor.controller;

import hr.foi.air.evoEditor.model.EvoAttribute;
import hr.foi.air.evoEditor.model.interfaces.IGallery;
import hr.foi.air.evoEditor.model.interfaces.IPage;
import hr.foi.air.evoEditor.model.interfaces.IPageResource;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class XMLGenerator {
	
	private static final String GALLERY_TAG_NAME = "gallery";
	private static final String PAGE_TAG_NAME = "page";
    public static final String ROOT_NODE = "documentation";
    public static final String DTD_FILE = "documentation.dtd";
    private static final String DEFAULT_INDENT = "4";

    //region PRIVATE FIELDS
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;
    private Element documentationElement;
    private String file;
    private String indent;
    private IGallery gallery;
    private File xmlFile;
    private ArrayList<String> resources;
    //endregion

    //region CONSTRUCTORS
    public XMLGenerator() {
        try {
            // create empty XML object
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            // XML tree
            // create root element and insert it in document
            documentationElement = document.createElement(ROOT_NODE);
            document.appendChild(documentationElement);
            setIndent(DEFAULT_INDENT);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region GETTERS & SETTERS
    public void setFile(String file) {
        this.file = file;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }
    
    //endregion

    //region PRIVATE METHODS
    private void appendGallery(IGallery gallery) {    	
    	this.gallery = gallery;
        Element galleryElement = document.createElement(GALLERY_TAG_NAME);
        
        for(EvoAttribute attribute : gallery.getGalleryAttributeSet()){
        	String galleryAttributeValue = attribute.getAttributeValue();
        	if(!galleryAttributeValue.isEmpty()){
        		galleryElement.setAttribute(attribute.getAttributeName(), galleryAttributeValue);
        	}
        }      
        
        documentationElement.appendChild(galleryElement);

        resources = new ArrayList<String>();

        appendPages(gallery.getChildPageList(gallery.getID()), galleryElement);
    }

    // append every page/subpage node to gallery node
    private void appendPages(ArrayList<IPage> pages, Element parentElement) {
        // iterate over every page in List
        for (IPage page : pages) {
            // create element document for page and set attributes
            Element pageElement = document.createElement(PAGE_TAG_NAME);
            
            Set<EvoAttribute> pageAttributeSet = page.getPageAttributeSet();
            for(EvoAttribute pageAttribute : pageAttributeSet){
            	if(pageAttribute.isUsed()){
            		String pageAttributeName = pageAttribute.getAttributeName();
            		String pageAttributeValue = pageAttribute.getAttributeValue();
            		//Empty attributes are printed
            		pageElement.setAttribute(pageAttributeName, pageAttributeValue);
            	}            	
            }
            
            for(IPageResource pageResource : page.getPageResources()){
            	if(pageResource.isUsed()){
            		Element element = document.createElement(pageResource.getName());

                    String pathIdentifier = pageResource.getExternalFileLocationAttributeName();

            		Set<EvoAttribute> resourceAttributeSet = pageResource.getAttributeSet();
            		for(EvoAttribute resourceAttribute : resourceAttributeSet){
            			String pageAttributeName = resourceAttribute.getAttributeName();
                		String pageAttributeValue = resourceAttribute.getAttributeValue();
                		if(resourceAttribute.isUsed()){
	            			if(pathIdentifier.equalsIgnoreCase(pageAttributeName)){
                                //Empty attributes are NOT printed
                                if(!pageAttributeValue.isEmpty()){
                                    resources.add(pageAttributeValue);
                                    File f = new File(pageAttributeValue);
                                    pageAttributeValue = "resources" + File.separator + f.getName();
                                }
	            			}
                            element.setAttribute(pageAttributeName, pageAttributeValue);
                		}
            		}
            		if(pageResource.canHaveContent()){
            			element.appendChild(document.createTextNode(pageResource.getContent()));
            		}
            		pageElement.appendChild(element);
            	}
            }

            // if page has subpages iterate through every one of them
            ArrayList<IPage> childPages = gallery.getChildPageList(page.getId());
            if (childPages.size() != 0) {
                appendPages(childPages, pageElement);
            }
            // if page don't have subpages append page node to parent node
            parentElement.appendChild(pageElement);
        }
    }

    private void zipGallery() throws IOException {
        byte[] buffer = new byte[1024];

        FileOutputStream fos;
        ZipOutputStream zos = null;

        try {
            fos = new FileOutputStream(file + ".zip");
            zos = new ZipOutputStream(fos);

            /**
             * Put xml file in zip.
             */
            ZipEntry ze = new ZipEntry(xmlFile.getName());
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(xmlFile.getAbsolutePath());

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();

            /**
             * Put dtd file in zip.
             */
            ze = new ZipEntry(DTD_FILE);
            zos.putNextEntry(ze);
            in = new FileInputStream(new File("Resources" + File.separator + DTD_FILE));

            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();

            /**
             * Put each resource file in zip.
             */
            for (String resource : resources) {
                ze = new ZipEntry("resources" + File.separator + FilenameUtils.getName(resource));
                zos.putNextEntry(ze);
                in = new FileInputStream(resource);

                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
                zos.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            zos.close();
        }

        xmlFile.delete();
    }
    //endregion

    //region PUBLIC METHODS
    public void generateXmlFile(IGallery gallery) {
        // create gallery node and page nodes for every page, subpage
        // and append that nodes to document object
        appendGallery(gallery);

        /*
        *****
        choose directory and file name
        *****
         */
        try {
            document.setXmlStandalone(true);
            xmlFile = new File(file + ".xml");
            xmlFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(xmlFile);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource dSource = new DOMSource(document);
            StreamResult sr = new StreamResult(outputStream);

            // set node indentation in xml
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", indent);

            // add doctype to xml document
            DOMImplementation domImpl = document.getImplementation();
            DocumentType docType = domImpl.createDocumentType("doctype", ROOT_NODE, DTD_FILE);
            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());
            t.transform(dSource, sr);

            outputStream.flush();
            outputStream.close();

            zipGallery();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    //endregion
}

