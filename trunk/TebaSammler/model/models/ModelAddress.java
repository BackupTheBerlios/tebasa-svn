/*
 * TebaSa is a software for creating letters in foreign languages
 * on the basis of text modules.
 * 
 * Copyright (C) 2007  Antje Huber
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */


package model.models;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.items.ItemAddress;
import model.listenerNotifier.ListenerAddresseeChanged;
import model.listenerNotifier.ListenerAddresserChanged;
import model.listenerNotifier.NotifierAddresseeChanged;
import model.listenerNotifier.NotifierAddresserChanged;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import controller.DOMIterator;
import controller.Titles;

/**Class for the model containing the addresses.
 * 
 * @author Antje Huber
 *
 */
public class ModelAddress extends Model {
    
    private static ModelAddress aModel;    
           
    private ItemAddress addressee;
    private ItemAddress addresser;
    private NotifierAddresseeChanged addresseeChangedNotfifier;
    private NotifierAddresserChanged addresserChangedNotfifier;
    private File file;
        
    private ModelAddress(File dirUserData) throws Exception {
        file = new File(dirUserData, "addressbook.xml");
        
        loadModel();
        
        addresseeChangedNotfifier = new NotifierAddresseeChanged();
        addresserChangedNotfifier = new NotifierAddresserChanged();
    }
    
    public static ModelAddress getInstance(File dirUserData) throws Exception {        
        if (aModel == null) {
            aModel = new ModelAddress(dirUserData);
        }
        
        return aModel;
    }

    @Override
    public void removeElement(Object anObject) {
        if (anObject instanceof ItemAddress) {
            ItemAddress address = (ItemAddress)anObject;

            Object o = address.getElementsBoolean()
                    .get(Titles.checkBoxTitleIsAddressee);
            if (o != null && (Boolean)o) {
                setAddressee(null);
            }

            o = address.getElementsBoolean()
                    .get(Titles.checkBoxTitleIsAddresser);
            if (o != null && (Boolean)o) {
                setAddresser(null);
            }
        }

        super.removeElement(anObject);
    }
    
    /**This method loads the data from the xml file.
     * 
     * @throws Exception
     */
    private void loadModel() throws Exception {
        try {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);            
            Node node = new DOMIterator(document, "addressbook").next();
            
            for (Node entry: new DOMIterator(node, "address")) {            
                ItemAddress address = new ItemAddress(entry);
                
                addElement(address);
                
                if (address.getElementsBoolean().get(
                        Titles.checkBoxTitleIsAddressee)) {
                    addressee = address;
                }
                else if (address.getElementsBoolean().get(
                        Titles.checkBoxTitleIsAddresser)) {
                    addresser = address;
                }
            }
        } catch (FileNotFoundException fnfe) {
            printModelToFile();
            
        } catch (Exception e) {
            printModelToFile();
            
            throw new Exception(Titles.loadAddressbookException, e);
        }
    }
    
    public void registerForAddresseeChangedNotifier(
            ListenerAddresseeChanged listener) {
        addresseeChangedNotfifier.add(listener);
    }
    
    public void registerForAddresserChangedNotifier(
            ListenerAddresserChanged listener) {
        addresserChangedNotfifier.add(listener);
    }
     
    /**Writes the addressbook back to file.
     * Because of a bug in Java versions 1.5 and 1.6 the entries in the file
     * will not be indented.
     * 
     * @throws Exception
     */
    public void printModelToFile() throws Exception {        
        try {
            DocumentBuilderFactory dmFactory =
                DocumentBuilderFactory.newInstance();        
            dmFactory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder documentBuilder = dmFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            
            Element root = document.createElement("addressbook");
            for (int i = 0; i < getSize(); i++) {
                Element elementAddress = document.createElement("address");
                
                ((ItemAddress)getElementAt(i))
                        .writeAddress(document, elementAddress);                
                root.appendChild(elementAddress);
            }
            document.appendChild(root);
            
            TransformerFactory tFactory =
                TransformerFactory.newInstance();
            tFactory.setAttribute("indent-number", new Integer(4));
            
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            
            transformer.transform(source, result);            
        } catch (Exception e) {
            throw new Exception(Titles.writeAddressbookException, e);
        }
    }
    
    /**Sets the current addressee.
     * 
     * @param addressee
     */
    public void setAddressee(ItemAddress addressee) {
        if (this.addressee != null) {
            this.addressee.getElementsBoolean().put(
                    Titles.checkBoxTitleIsAddressee, false);
        }
        
        if (addressee != null) {
            addressee.getElementsBoolean().put(
                    Titles.checkBoxTitleIsAddressee, true);
        }
        
        this.addressee = addressee;
        addresseeChangedNotfifier.fireStatusChanged();
    }
    
    /**Sets the current addresser.
     * 
     * @param addresser
     */
    public void setAddresser(ItemAddress addresser) {
        if (this.addresser != null) {
            this.addresser.getElementsBoolean().put(
                    Titles.checkBoxTitleIsAddresser, false);
        }
        
        if (addresser != null) {
            addresser.getElementsBoolean().put(
                    Titles.checkBoxTitleIsAddresser, true);
        }
        
        this.addresser = addresser;
        addresserChangedNotfifier.fireStatusChanged();
    }
    
    public ItemAddress getAddressee() {
        return addressee;
    }
    
    public ItemAddress getAddresser() {        
        return addresser;
    }
}
