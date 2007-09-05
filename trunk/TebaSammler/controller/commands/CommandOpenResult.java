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

import gui.GUI;

import java.io.File;

import controller.DocumentOperations;

/**This command opens a saved document.
 * 
 * @author Antje Huber
 *
 */
public class CommandOpenResult extends Command {
    
    private File openFile;
    private DocumentOperations resultOperations;

    public CommandOpenResult(
            DocumentOperations resultOperations, File openFile) {        
        this.openFile = openFile;
        this.resultOperations = resultOperations;
    }
    
    public void execute() {
        try {
            controller.commitCommand(new CommandResetChosenTextModules());
            resultOperations.open(openFile);
            controller.getUI().changeStatus(GUI.Status.OPEN);
            controller.setFile(openFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}