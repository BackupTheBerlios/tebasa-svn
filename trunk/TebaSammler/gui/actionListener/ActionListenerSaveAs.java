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

import gui.fileChooser.FileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import controller.Controller;
import controller.DocumentOperations;
import controller.Titles;
import controller.commands.CommandSaveResult;

/**ActionListener for saving a document with a specified file name.
 * 
 * @author Antje Huber
 *
 */
public class ActionListenerSaveAs implements ActionListener {

    private Controller controller;
    private Titles titles;
    private DocumentOperations resultOperations;
    
    public ActionListenerSaveAs(Controller controller, Titles titles,
            DocumentOperations resultOperations) {
        this.controller = controller;
        this.titles = titles;
        this.resultOperations = resultOperations;
    }

    /**This method creates a file chooser and a new command CommandSaveResult.
     *  
     */
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new FileChooser(
                controller, titles.getString(Titles.dialogTitleSave));
        
        if (fc.showSaveDialog(controller.getUI()) ==
                JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String stringFile = file.toString();
            
            if (!stringFile.endsWith(".xml")) {
                file = new File(stringFile + ".xml");
            }
            
            controller.commitCommand(new CommandSaveResult(
                    resultOperations, file));
        }
    }
}
