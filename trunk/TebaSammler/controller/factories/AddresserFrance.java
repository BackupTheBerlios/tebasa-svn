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

/**This class creates a displayed form of a french addresser address.
 * 
 * @author Antje Huber
 *
 */
public class AddresserFrance extends Address {
    
    public AddresserFrance(ItemAddress addresser, Titles titlesAddresser,
            String lineSeparator) {
        super(addresser, titlesAddresser, lineSeparator);
    }
        
    public String buildAddress() {
        s = new StringBuffer();
        
        if (!company.equals("")) {
            s.append(map.get(Titles.titleCompanyA));
            s.append(lineSeparator);
        }
        s.append(getForename());
        s.append(" ");
        s.append(getSurname());
        s.append(lineSeparator);
        s.append(getStreetNumber());
        s.append(" ");
        s.append(getStreet());
        s.append(lineSeparator);
        s.append(getPostalCode());
        s.append(" ");
        s.append(getCity());
        s.append(lineSeparator);
        s.append(getCountry());
        
        return s.toString();   
    }
}
