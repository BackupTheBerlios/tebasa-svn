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


package controller.commands;

import java.util.Map;

import model.items.ItemAddress;
import model.models.ModelAddress;
import controller.Titles;

/**This command saves a new address in the addressbook. 
 *  
 * @author Antje Huber
 * 
 */
public class CommandSaveNewAddress extends CommandSaveAddress {
    
    public CommandSaveNewAddress(ItemAddress address,
            Map<String,  String> stringEntries,
            Map<String, Boolean> booleanEntries) {
        super(address, stringEntries, booleanEntries);
    }
    
    public void execute() {        
        if (address != null) {
            ((ModelAddress)controller.getModels()
                    .get(Titles.titleAddressM)).addElement(address);
        }
        
        super.execute();
    }
}
