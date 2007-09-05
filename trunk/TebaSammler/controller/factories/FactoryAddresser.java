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

import model.items.ItemAddress;
import controller.Titles;

/**This class defines which address object will be used for the displayed
 * form of the addresser.
 * 
 * @author Antje Huber
 *
 */
public class FactoryAddresser {
    
    public static FactoryAddresser factoryAddresser;
    
    private String lineSeparator;
    
    private FactoryAddresser(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }
    
    public static FactoryAddresser getInstance(String lineSeparator) {
        if (factoryAddresser == null) {
            factoryAddresser = new FactoryAddresser(lineSeparator);
        }
        
        return factoryAddresser;
    }
    
    public Address getAddresser(ItemAddress addresser, Titles titlesAddresser) {
        if (addresser != null) {
            String countryAddress =
                addresser.getElementsString().get(Titles.titleCountryA);
            
            if (countryAddress.equals(Titles.titleCountryGermany)) {
                return new AddresserGermany(
                        addresser, titlesAddresser, lineSeparator);
                
            }
            else if (countryAddress.equals(Titles.titleCountryFrance)) {
                return new AddresserFrance(
                        addresser, titlesAddresser, lineSeparator);
            }
            else {
                return new AddresserGreatBritain(
                        addresser, titlesAddresser, lineSeparator);
            }
        }
        else return null;
    }
}
