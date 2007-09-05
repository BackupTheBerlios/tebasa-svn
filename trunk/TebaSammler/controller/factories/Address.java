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


package controller.factories;

import java.util.Map;

import model.items.ItemAddress;
import controller.Titles;

/**This is an abstract class for displayed forms of addresses. It provides
 * methods for sub-classes which return Strings with address parts.
 * 
 * @author Antje Huber
 *
 */
public abstract class Address {
    protected Map map;
    protected StringBuffer s;
    protected String company;
    protected Titles titlesAddresser;
    protected Titles titlesAddressee;
    protected String lineSeparator;
    
    protected Address(ItemAddress address, Titles titlesAddresser,
            String lineSeparator) {
        this.titlesAddresser = titlesAddresser;
        this.lineSeparator = lineSeparator;
        
        if (lineSeparator == null) {
            lineSeparator = "\n";
        }
        
        map = address.getElementsString();
        company = getCompany();
    }
    
    protected Address(ItemAddress address, Titles titlesAddressee,
            Titles titlesAddresser, String lineSeparator) {
        this.titlesAddresser = titlesAddresser;
        this.titlesAddressee = titlesAddressee;
        this.lineSeparator = lineSeparator;
        
        if (lineSeparator == null) {
            lineSeparator = "\n";
        }
        
        map = address.getElementsString();
        company = getCompany();
    }
    
    public String buildAddress() {
        return "";
    }
    
    protected String getAddress() {
        try {
            return titlesAddressee.getString(
                    map.get(Titles.titleAddressA).toString());
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getForename() {
        try {
            return map.get(Titles.titleForenameA).toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getSurname() {
        try {
            return map.get(Titles.titleSurnameA).toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getCompany() {
        try {
            return map.get(Titles.titleCompanyA).toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getStreetNumber() {
        try {
            return map.get(Titles.titleStreetNumberA).toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getStreet() {
        try {
            return map.get(Titles.titleStreetA).toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getPostalCode() {
        try {
            return map.get(Titles.titlePostalCodeA).toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getCity() {
        try {
            return map.get(Titles.titleCityA).toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getCountry() {
        try {
            return titlesAddresser.getString(
                    map.get(Titles.titleCountryA).toString()).toUpperCase(); 
        } catch (Exception e) {
            return "";
        }
    }
}
