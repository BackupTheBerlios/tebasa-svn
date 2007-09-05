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

import model.items.ItemAddress;
import model.models.ModelAddress;
import controller.Titles;

/**This command removes an address from addressbook.
 *  
 * @author Antje Huber
 * 
 */
public class CommandRemoveAddress extends Command {
    
    private ItemAddress address;
    
    public CommandRemoveAddress(ItemAddress address) {
        this.address = address;
    }
    
    public void execute() {        
        ModelAddress aModel =
            (ModelAddress)controller.getModels().get(Titles.titleAddressM);
        
        aModel.removeElement(address);
        
        try {
            aModel.printModelToFile();
        } catch (Exception e) {
            controller.handleException(e.toString(), e);
        }
    }
}
