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


package model.items;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import controller.DOMIterator;
import controller.Titles;

/**Class for Adresses.
 * The different parts of an address will be inserted in a map with Strings and
 * in a map with booleans.
 * @author Antje Huber
 *
 */
public class ItemAddress {

    private Map<String, String> elementsString;
    private Map<String, Boolean> elementsBoolean;
    
    public ItemAddress(
            String company, String address, String surname,
            String forename, String street, String streetNumber,
            String postalCode, String city, String country,
            String telefoneNumber, String mobileNumber, String faxNumber,
            String emailAddress, String bank, String bankCode,
            String accountNumber, boolean isAddressee, boolean isAddresser) {
        
        elementsString = new LinkedHashMap<String, String>();
        elementsBoolean = new LinkedHashMap<String, Boolean>();
        
        elementsString.put(Titles.titleCompanyA, company);
        elementsString.put(Titles.titleAddressA, address);
        elementsString.put(Titles.titleSurnameA, surname);
        elementsString.put(Titles.titleForenameA, forename);
        elementsString.put(Titles.titleStreetA, street);
        elementsString.put(Titles.titleStreetNumberA, streetNumber);
        elementsString.put(Titles.titlePostalCodeA, postalCode);
        elementsString.put(Titles.titleCityA, city);
        elementsString.put(Titles.titleCountryA, country);
        elementsString.put(Titles.titleTelefoneNumberA, telefoneNumber);
        elementsString.put(Titles.titleMobileNumberA, mobileNumber);
        elementsString.put(Titles.titleFaxNumberA, faxNumber);
        elementsString.put(Titles.titleEmailAddressA, emailAddress);
        elementsString.put(Titles.titleBankA, bank);
        elementsString.put(Titles.titleBankCodeA, bankCode);
        elementsString.put(Titles.titleAccountNumberA, accountNumber);
        
        elementsBoolean.put(Titles.checkBoxTitleIsAddressee, isAddressee);
        elementsBoolean.put(Titles.checkBoxTitleIsAddresser, isAddresser);
    }
    
    public ItemAddress() {
        
        elementsString = new LinkedHashMap<String, String>();
        elementsBoolean = new LinkedHashMap<String, Boolean>();
        
        elementsString.put(Titles.titleCompanyA, "");
        elementsString.put(Titles.titleAddressA, "");
        elementsString.put(Titles.titleSurnameA, "");
        elementsString.put(Titles.titleForenameA, "");
        elementsString.put(Titles.titleStreetA, "");
        elementsString.put(Titles.titleStreetNumberA, "");
        elementsString.put(Titles.titlePostalCodeA, "");
        elementsString.put(Titles.titleCityA, "");
        elementsString.put(Titles.titleCountryA, "");
        elementsString.put(Titles.titleTelefoneNumberA, "");
        elementsString.put(Titles.titleMobileNumberA, "");
        elementsString.put(Titles.titleEmailAddressA, "");
        elementsString.put(Titles.titleBankA, "");
        elementsString.put(Titles.titleBankCodeA, "");
        elementsString.put(Titles.titleAccountNumberA, "");
        elementsBoolean.put(Titles.checkBoxTitleIsAddressee, false);
        elementsBoolean.put(Titles.checkBoxTitleIsAddresser, false);
    }
    
    public ItemAddress(Node node) {
        this();
        
        parseAddress(node);
    }
    
    public Map<String, Boolean> getElementsBoolean() {
        return elementsBoolean;
    }
    
    public Map<String, String> getElementsString() {
        return elementsString;
    }
    
    public String getName() {
        return elementsString.get(Titles.titleForenameA) + " "
                + elementsString.get(Titles.titleSurnameA);
    }
    
    public String toString() {
        return elementsString.get(Titles.titleSurnameA)
                + ", " + elementsString.get(Titles.titleForenameA);
    }
    
    /**Method for writing an address to a xml file.
     * 
     * @param document
     * @param elementAddress
     */
    public void writeAddress(Document document, Element elementAddress) {        
        for (Map.Entry<String, String> e: elementsString.entrySet()) {
            Element element = document.createElement(e.getKey());
            
            element.appendChild(document.createTextNode(e.getValue()));
            elementAddress.appendChild(element);
        }
        
        for (Map.Entry<String, Boolean> e: elementsBoolean.entrySet()) {
            Element element = document.createElement(e.getKey());
            
            element.appendChild(                    
                    document.createTextNode(e.getValue().toString()));
            elementAddress.appendChild(element);           
        }
    }
    
    /**Method for reading an address from a xml file.
     * 
     * @param node
     */
    private void parseAddress(Node node) {
        for (Node attrNode: new DOMIterator(node, null)) {
            if (attrNode.getNodeName().startsWith("check")) {
                elementsBoolean.put(attrNode.getNodeName(),
                        new Boolean(attrNode.getTextContent()));
            }
            else {
                elementsString.put(
                        attrNode.getNodeName(), attrNode.getTextContent());
            }
        }
    }
}
