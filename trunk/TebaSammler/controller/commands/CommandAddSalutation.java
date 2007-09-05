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

import model.items.ItemTextModule;

/**This command adds a new salutation to the model ChosenTextModules.
 * 
 * @author Antje Huber
 *
 */
public class CommandAddSalutation extends Command {

    private ItemTextModule tm;
    
    public CommandAddSalutation(ItemTextModule tm) {
        this.tm = tm;
    }
    
    public void execute() {        
        controller.getChosenTextModules().addSalutation(tm);
    }
}
