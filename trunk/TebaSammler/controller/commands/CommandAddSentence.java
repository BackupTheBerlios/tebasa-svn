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
import model.models.ModelTextModule;
import controller.Titles;

/**This command adds a new sentence to the model ChosenTextModules.
 * 
 * @author Antje Huber
 *
 */
public class CommandAddSentence extends Command {
    
    private int index;
    
    public CommandAddSentence(int index) {
        this.index = index;
    }

    public void execute() {        
        ModelTextModule tmModel = (ModelTextModule)controller.getModels()
                .get(Titles.titleTextModuleM);
        ItemTextModule tm =
            (ItemTextModule)tmModel.getModelText().getSelectedItem();      
        
        if (tm != null && tm.toString().length() > 0) {
            controller.getChosenTextModules().addSentence(tm, index);
            
            tmModel.resetTextModuleText();
        }
        else {
            controller.handleException(
                    Titles.noSentenceChosenException, new Exception());
        }
    }
}
