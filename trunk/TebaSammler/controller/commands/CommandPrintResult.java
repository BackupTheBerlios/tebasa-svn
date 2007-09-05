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

import controller.DocumentOperations;
import controller.Titles;

public class CommandPrintResult extends Command {
    
    private DocumentOperations resultOperations;
    
    public CommandPrintResult(DocumentOperations resultOperations) {        
        this.resultOperations = resultOperations;
    }
    
    public void execute() {
        try {
            resultOperations.print();
        }
        catch (Exception e) {
            controller.handleException(Titles.printTextException, e);
        }
    }
}
