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

import javax.swing.JFileChooser;

import controller.Controller;
import controller.DocumentOperations;
import controller.Titles;
import controller.commands.CommandOpenResult;

/**ActionListener for opening a document.
 * 
 * @author Antje Huber
 *
 */
public class ActionListenerOpen implements ActionListener {
    
    private Controller controller;
    private DocumentOperations resultOperations;
    private Titles titles;
    
    public ActionListenerOpen(Controller controller, Titles titles,
            DocumentOperations resultOperations) {
        this.controller = controller;
        this.resultOperations = resultOperations;
        this.titles = titles;
    }
    
    public void actionPerformed(ActionEvent e) {
        try {
            JFileChooser fc = new FileChooser(
                    controller, titles.getString(Titles.dialogTitleOpen));
            
            if (fc.showOpenDialog(controller.getUI()) ==
                    JFileChooser.APPROVE_OPTION) {
                controller.commitCommand(new CommandOpenResult(
                        resultOperations, fc.getSelectedFile()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
