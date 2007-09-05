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
 * form of the addressee.
 * 
 * @author Antje Huber
 *
 */
public class FactoryAddressee {
    
    public static FactoryAddressee factoryAddressee;
    
    private String lineSeparator;
    
    private FactoryAddressee(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }
    
    public static FactoryAddressee getInstance(String lineSeparator) {
        if (factoryAddressee == null) {
            factoryAddressee = new FactoryAddressee(lineSeparator);
        }
        
        return factoryAddressee;
    }
    
    public Address getAddressee(ItemAddress addressee, Titles titlesAddressee,
            Titles titlesAddresser) {
        if (addressee != null) {
            String countryAddress =
                addressee.getElementsString().get(Titles.titleCountryA);
            
            if (countryAddress.equals(Titles.titleCountryGermany)) {
                return new AddresseeGermany(addressee, titlesAddressee,
                        titlesAddresser, lineSeparator);
            }
            else if (countryAddress.equals(Titles.titleCountryFrance)) {
                return new AddresseeFrance(addressee, titlesAddressee,
                        titlesAddresser, lineSeparator);
            }
            else {
                return new AddresseeGreatBritain(addressee, titlesAddressee,
                        titlesAddresser, lineSeparator);
            }
        }
        else return null;
    }
}
