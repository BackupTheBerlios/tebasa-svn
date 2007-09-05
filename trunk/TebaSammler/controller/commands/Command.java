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

import controller.Controller;

/**Abstract class for Commands.
 * 
 * @author Antje Huber
 *
 */
public abstract class Command {
    
    protected Controller controller;
    
    /**This method will contain the specialized code of the command. 
     * It should be implemented by sub-classes.
     */
    public void execute() {}
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
}