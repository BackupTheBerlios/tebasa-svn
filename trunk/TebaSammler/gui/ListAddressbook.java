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


package gui;

import gui.actionListener.ActionListenerShowAddress;
import gui.listenerNotifier.ListenerKeyEnter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JList;

import model.Models;
import controller.Controller;
import controller.Titles;

/**Sub-class of JList which customize the superclass for the addressbook
 * content.
 * 
 * @author Antje Huber
 *
 */
public class ListAddressbook extends JList {
    
    private static String actionCommand = "OpenAddress";
        
    public ListAddressbook(
            Controller controller, Titles titles, JFrame owner, Models models) {
        
        ActionListenerShowAddress actionListener =
            new ActionListenerShowAddress(
                    controller, titles, owner, this, false);
        
        addKeyListener(new ListenerKeyEnter(actionListener, actionCommand));
        addMouseListener(
                new MouseListenerAddressbook(actionListener, actionCommand));
        
        setModel(models.get(Titles.titleAddressM));
        setEnabled(true);
        setSelectedIndex(0);
        setPreferredSize(new Dimension(250, 300));
    }
    
    
    /**MouseListener for the ListAddressbook.
     * 
     * @author Antje Huber
     *
     */ 
    private class MouseListenerAddressbook implements MouseListener {
        
        private String actionCommand;
        private ActionListenerShowAddress actionListener;
        
        private MouseListenerAddressbook(ActionListenerShowAddress
                actionListener, String actionCommand) {
            this.actionCommand = actionCommand;
            this.actionListener = actionListener;
        }
                
        public void mouseClicked(MouseEvent e) {           
            if (e.getClickCount() == 2) {
                actionListener.actionPerformed(new ActionEvent(
                        this, ActionEvent.ACTION_PERFORMED, actionCommand));

                e.consume();
            }
        }
        
        public void mouseEntered(MouseEvent e) {}
        
        public void mouseExited(MouseEvent e) {}
        
        public void mouseReleased(MouseEvent e) {}
        
        public void mousePressed(MouseEvent e) {}
    }
}