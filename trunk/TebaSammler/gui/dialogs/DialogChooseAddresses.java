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


package gui.dialogs;

import gui.Button;
import gui.Mnemonics;
import gui.actionListener.ActionListenerClose;
import gui.actionListener.ActionListenerShowAddress;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import model.Models;
import model.items.ItemAddress;
import model.listenerNotifier.ListenerAddresseeChanged;
import model.listenerNotifier.ListenerAddresserChanged;
import model.models.ModelAddress;
import controller.Controller;
import controller.InterfaceController;
import controller.Titles;
import controller.commands.CommandSetAddressee;
import controller.commands.CommandSetAddresser;

/**This class creates a dialog for choosing addressee and addresser.
 * 
 * @author anni
 *
 */
public class DialogChooseAddresses extends JDialog implements
        ListenerAddresseeChanged, ListenerAddresserChanged {
    
    private Mnemonics mnemonics;
    private Vector<Object> addresses;
    private String titleChoose;
    private String titleShow;
    private String titleOK;
    private ModelAddress aModel;
    private GridBagConstraints gbcCB;
    private GridBagConstraints gbcLabel;
    private JPanel panelAddressee;
    private JPanel panelAddresser;
    private JPanel panelButton;
    private JComboBox cbAddresser;
    private JComboBox cbAddressee;
    
    private Controller controller;
    private Titles titles;    
    
    public DialogChooseAddresses(Controller controller, Titles titles,
            Models models, Frame owner) {
        super(owner, titles.getString(Titles.dialogTitleChooseAddresses), true);
        
        this.controller = controller;
        this.titles = titles;
        
        Point point = new Point();
        owner.getLocation(point);
        setLocation(new Point(point.x + 312, point.y + 259));
        setSize(new Dimension(400, 250));
        setLayout(new GridBagLayout());
                
        try {
            mnemonics = new Mnemonics();
                
            aModel = (ModelAddress)models.get(Titles.titleAddressM);
            
            addresses = aModel.getAllItems();
            
            titleChoose = titles.getString(Titles.labelTitleChooseAddresses);
            titleShow = titles.getString(Titles.labelTitleShowAddresses);
            titleOK = titles.getString(Titles.buttonTitleOpenAddress);
            
            gbcCB = new GridBagConstraints();
            gbcCB.insets.set(0, 10, 20, 10);
            gbcCB.anchor = GridBagConstraints.CENTER;
            gbcCB.weightx = 0.1;
            gbcCB.fill = GridBagConstraints.HORIZONTAL;
            gbcCB.gridwidth = GridBagConstraints.REMAINDER;
            
            gbcLabel = new GridBagConstraints();
            gbcLabel.insets.set(10, 20, 5, 10);
            gbcLabel.anchor = GridBagConstraints.WEST;
            gbcLabel.weightx = 0.1;
            gbcLabel.fill = GridBagConstraints.HORIZONTAL;
            gbcLabel.gridwidth = GridBagConstraints.REMAINDER;
                    
            panelAddressee = new JPanel(new GridBagLayout());
            panelAddressee.setBorder(
                    controller.getTitledBorder(Titles.borderTitleAddresseeA));
            fillPanelAddressee();
            
            panelAddresser = new JPanel(new GridBagLayout());
            panelAddresser.setBorder(
                    controller.getTitledBorder(Titles.borderTitleAddresserA));
            fillPanelAddresser();
            
            panelButton = new JPanel(new GridBagLayout());
            panelButton.setBorder(
                    BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            fillPanelButton();
            
            GridBagConstraints gbc = new GridBagConstraints();
            
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0.1;
            gbc.weighty = 0.1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridwidth = 1;
            add(panelAddressee, gbc);
            
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.EAST;
            add(panelAddresser, gbc);
            
            gbc.anchor = GridBagConstraints.CENTER;
            add(panelButton, gbc);
            
            aModel.registerForAddresseeChangedNotifier(this);
            aModel.registerForAddresserChangedNotifier(this);
            
            pack();
            setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**This method fills the panel panelAddressee with components.*/
    private void fillPanelAddressee() {
        panelAddressee.add(new JLabel(titleChoose), gbcLabel);
        
        cbAddressee = new JComboBox(addresses);
        ActionListener listenerShowAddressee = new ActionListenerShowAddress(
                controller, titles, controller.getUI(), cbAddressee, false);
        JButton buttonOpenAddressee =
            new Button(titleOK, mnemonics, listenerShowAddressee);
        
        cbAddressee.setSelectedItem(aModel.getAddressee());
        cbAddressee.addItemListener(
                new ItemListenerAddressee(controller, cbAddressee));
        panelAddressee.add(cbAddressee, gbcCB);
        panelAddressee.add(new JLabel(titleShow), gbcLabel);
        panelAddressee.add(buttonOpenAddressee, gbcCB);
    }
    
    /**This method fills the panel panelAddresser with components.*/
    private void fillPanelAddresser() {
        panelAddresser.add(new JLabel(titleChoose), gbcLabel);
        
        cbAddresser = new JComboBox(addresses);
        ActionListener listenerShowAddresser = new ActionListenerShowAddress(
                controller, titles, controller.getUI(), cbAddresser, false);
        JButton buttonOpenAddresser =
            new Button(titleOK, mnemonics, listenerShowAddresser);
        
        cbAddresser.setSelectedItem(aModel.getAddresser());
        cbAddresser.addItemListener(
                new ItemListenerAddresser(controller, cbAddresser));
        panelAddresser.add(cbAddresser, gbcCB);
        panelAddresser.add(new JLabel(titleShow), gbcLabel);
        panelAddresser.add(buttonOpenAddresser, gbcCB);
    }
    
    /**This method fills the panel panelButton with content.*/
    private void fillPanelButton() {
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets.set(10, 10, 10, 10);
        gbcButton.anchor = GridBagConstraints.CENTER;

        JButton buttonClose = new Button(
                titles.getString(Titles.buttonTitleCloseDialog), mnemonics,
                new ActionListenerClose(this));
        panelButton.add(buttonClose, gbcButton);        
    }
    
    public void addresserChanged() {
        cbAddresser.setSelectedItem(aModel.getAddresser());
    }
    
    public void addresseeChanged() {
        cbAddressee.setSelectedItem(aModel.getAddressee());
    }
    
    
    /**Inner class for a listener which observes the combobox addresser.
     * 
     * @author Antje Huber
     *
     */
    private class ItemListenerAddresser implements ItemListener {
        
        private InterfaceController controller;
        private JComboBox comboBox;
        
        private ItemListenerAddresser(InterfaceController controller,
                JComboBox comboBox) {        
            this.controller = controller;
            this.comboBox = comboBox;
        }
        
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                controller.commitCommand(new CommandSetAddresser(
                        (ItemAddress)comboBox.getSelectedItem()));
            }
        }
    }
    
    /**Inner class for a listener which observes the combobox addressee.
     * 
     * @author Antje Huber
     *
     */
    private class ItemListenerAddressee implements ItemListener {
        
        private InterfaceController controller;
        private JComboBox comboBox;
        
        private ItemListenerAddressee(InterfaceController controller,
                JComboBox comboBox) {        
            this.controller = controller;
            this.comboBox = comboBox;
        }
        
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                controller.commitCommand(new CommandSetAddressee(
                        (ItemAddress)comboBox.getSelectedItem()));
            }
        }
    }
}
 