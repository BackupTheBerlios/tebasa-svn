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

import model.ChosenTextModules;
import model.items.ItemTextModule;
import model.models.ModelTextModule;
import controller.Titles;

/**This command removes a chosen textModule from model ChosenTextModules.
 * This can be a subject, a salutation, a closing or a sentence.
 *  
 * @author Antje Huber
 * 
 */
public class CommandRemoveTextModule extends Command {

    public void execute() {
        ChosenTextModules ctmModel = controller.getChosenTextModules();
        ItemTextModule tm = (ItemTextModule)ctmModel.getSelectedItem();
        
        if (tm.getInternalName().equals(Titles.titleTextModuleSubject)) {
            ctmModel.removeSubject();
            ((ModelTextModule)controller.getModels()
                    .get(Titles.titleTextModuleM)).resetTextModuleSubject();
        }
        else if (tm.getInternalName()
                .equals(Titles.titleTextModuleSalutation)) {
            ctmModel.removeSalutation();
            ((ModelTextModule)controller.getModels()
                    .get(Titles.titleTextModuleM)).resetTextModuleSalutation();
        }
        else if (tm.getInternalName().equals(Titles.titleTextModuleClosing)) {
            ctmModel.removeClosing();
            ((ModelTextModule)controller.getModels()
                    .get(Titles.titleTextModuleM)).resetTextModuleClosing();
        }
        else if (tm.getInternalName().equals(Titles.titleTextModuleText))
            ctmModel.removeSentence();
    }
}
