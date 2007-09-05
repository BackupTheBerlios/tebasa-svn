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


package gui.actionListener;

import java.awt.event.ActionEvent;
import java.io.File;

import controller.Controller;
import controller.DocumentOperations;
import controller.Titles;
import controller.commands.CommandSaveResult;

/**ActionListener for saving a document.
 * 
 * @author Antje Huber
 *
 */
public class ActionListenerSave extends ActionListenerSaveAs {

    private Controller controller;
    private DocumentOperations resultOperations;
    
    public ActionListenerSave(Controller controller, Titles titles,
            DocumentOperations resultOperations) {
        super(controller, titles, resultOperations);
        
        this.controller = controller;
        this.resultOperations = resultOperations;
    }
    
    /**Creates a new Command CommandSaveResult.
     * 
     */
    public void actionPerformed(ActionEvent e) {
       try {
            File file = controller.getFile();
            
            if (file == null) {
                super.actionPerformed(e);
            }
            else {
                controller.commitCommand(
                        new CommandSaveResult(resultOperations, file));
            }            
       } catch (Exception exception) {
           exception.printStackTrace();
       }
    }
}
