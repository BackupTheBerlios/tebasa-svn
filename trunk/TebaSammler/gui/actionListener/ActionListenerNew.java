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

import gui.dialogs.DialogNew;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Models;
import controller.Controller;
import controller.Titles;

/**ActionListener for creating a new document.
 * 
 * @author Antje Huber
 *
 */
public class ActionListenerNew implements ActionListener {
    
    private Controller controller;
    private Titles titles;
    private Models models;
    
    public ActionListenerNew(
            Controller controller, Titles titles, Models models) {        
        this.controller = controller;
        this.titles = titles;
        this.models = models;
    }    
    
    public void actionPerformed(ActionEvent e) {
        new DialogNew(controller, titles, models, controller.getUI());
    }
}
