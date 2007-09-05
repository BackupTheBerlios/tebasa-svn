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

import gui.dialogs.DialogShowAddress;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JList;

import model.items.ItemAddress;
import controller.Controller;
import controller.Titles;

/**ActionListener for showing an address in a separate dialog.
 * 
 * @author Antje Huber
 *
 */
public class ActionListenerShowAddress implements ActionListener {

    private Controller controller;
    private Titles titles;
    private Frame owner;
    private boolean newAddress;
    
    private JComboBox cbAddress;
    private JList listAddressbook;

    public ActionListenerShowAddress(Controller controller, Titles titles,
            Frame owner, boolean newAddress) {
        this.controller = controller;
        this.titles = titles;
        this.owner = owner;
        this.newAddress = newAddress;
    }

    public ActionListenerShowAddress(Controller controller, Titles titles,
            Frame owner, JComboBox cbAddress, boolean newAddress) {
        this.controller = controller;
        this.titles = titles;
        this.owner = owner;
        this.newAddress = newAddress;
        this.cbAddress = cbAddress;
    }

    public ActionListenerShowAddress(Controller controller, Titles titles,
            Frame owner, JList listAddressbook, boolean newAddress) {
        this.controller = controller;
        this.titles = titles;
        this.owner = owner;
        this.newAddress = newAddress;
        this.listAddressbook = listAddressbook;
    }
    
    public void actionPerformed(ActionEvent e) {
        ItemAddress address = null;
        
        if (cbAddress != null) {
            address = (ItemAddress)cbAddress.getSelectedItem();
        }
        else if (listAddressbook != null) {
            address = (ItemAddress)listAddressbook.getSelectedValue();
        }
        else {
            address = new ItemAddress();
        }
        
        new DialogShowAddress(controller, titles, owner, address, newAddress);
    }
}
