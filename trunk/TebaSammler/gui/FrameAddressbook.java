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


import gui.actionListener.ActionListenerClose;
import gui.actionListener.ActionListenerShowAddress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Models;
import model.items.ItemAddress;
import controller.Controller;
import controller.InterfaceController;
import controller.Titles;
import controller.commands.CommandRemoveAddress;

/**This class creates a frame for the addressbook.
 * 
 * @author Antje Huber
 *
 */
public class FrameAddressbook extends JFrame {
    
    private Mnemonics mnemonics;
    private GridBagConstraints gbc;
    private JPanel panelButtons;
    
    private Titles titles;
        
    public FrameAddressbook(Controller controller, Titles titles,
            Models models) {
        this.titles = titles;
        
        setTitle(titles.getString(Titles.frameTitleAddress));
        setPreferredSize(new Dimension(450, 300));
        setLocation(new Point(281, 234));
        setLayout(new BorderLayout());

        mnemonics = new Mnemonics();
        
        JPanel panelList = new JPanel();
        panelButtons = new JPanel(new GridBagLayout());        
        
        ListAddressbook listAddressbook =
            new ListAddressbook(controller, titles, this, models);
        panelList.add(new JScrollPane(listAddressbook));

        gbc = new GridBagConstraints();
        gbc.insets.set(25, 10, 25, 10);        
        gbc.anchor = GridBagConstraints.WEST;
        
        try {
            addButton(Titles.buttonTitleShowAddress,
                    new ActionListenerShowAddress(controller, titles, this,
                            listAddressbook, false), true);
            
            gbc.anchor = GridBagConstraints.CENTER;
            addButton(Titles.buttonTitleNewAddress,
                    new ActionListenerShowAddress(
                            controller, titles, this, true),
                    false);
            
            addButton(Titles.buttonTitleRemoveAddress,
                    new ActionListenerRemove(controller, listAddressbook),
                    false);
            
            gbc.anchor = GridBagConstraints.EAST;
            addButton(Titles.buttonTitleCloseAddressbook,
                    new ActionListenerClose(this), false);
            
            add(panelList, BorderLayout.NORTH);
            add(panelButtons, BorderLayout.SOUTH);
            
            pack();
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addButton(String internalName, ActionListener listener,
            boolean defaultButton) {
        JButton button = new Button(
                titles.getString(internalName), mnemonics, listener);
        
        panelButtons.add(button, gbc);
        
        if (defaultButton) {
            getRootPane().setDefaultButton(button);
        }
    }
    
    
    /**ActionListener for removing an address.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerRemove implements ActionListener {
        
        private InterfaceController controller;
        private ListAddressbook list;
        
        private ActionListenerRemove(InterfaceController controller,
                ListAddressbook list) {
            this.controller = controller;
            this.list = list;
        }
        
        public void actionPerformed(ActionEvent e) {            
            controller.commitCommand(new CommandRemoveAddress(
                    (ItemAddress)list.getSelectedValue()));
        }
    }
}
