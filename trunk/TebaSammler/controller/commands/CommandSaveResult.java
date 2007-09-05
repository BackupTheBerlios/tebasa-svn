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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import controller.DocumentOperations;
import controller.Titles;

/**This command saves a created document and sets a new frame title.
 *  
 * @author Antje Huber
 * 
 */
public class CommandSaveResult extends Command {
    
    private DocumentOperations resultOperations;
    private File saveFile;
    
    public CommandSaveResult(
            DocumentOperations resultOperations, File saveFile) {
        this.resultOperations = resultOperations;
        this.saveFile = saveFile;
    }
    
    public void execute() {
        try {
            resultOperations.save(new FileOutputStream(saveFile));
            controller.setFile(saveFile);
        } catch (FileNotFoundException e) {
            controller.handleException(Titles.saveTextException, e);
        }
    }
}
