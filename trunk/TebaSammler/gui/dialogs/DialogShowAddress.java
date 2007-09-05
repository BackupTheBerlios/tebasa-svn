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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import model.items.Item;
import model.items.ItemAddress;
import model.models.Model;
import model.models.ModelAddressAddress;
import model.models.ModelAddressCountry;
import controller.Controller;
import controller.InterfaceController;
import controller.Titles;
import controller.commands.CommandSaveAddress;
import controller.commands.CommandSaveNewAddress;

/**Class creating a dialog which shows a chosen address.
 * 
 * @author Antje Huber
 *
 */
public class DialogShowAddress extends JDialog {
    
    private JPanel panelName;
    private JPanel panelAddress;
    private JPanel panelBank;
    private JPanel panelContact;
    private Titles titles;
    private GridBagConstraints gbcTextField;
    private GridBagConstraints gbcLabel;
      
    public DialogShowAddress(Controller controller, Titles titles, Frame owner, 
            ItemAddress address, boolean newAddress) {
        super(owner, titles.getString(Titles.dialogTitleAddress), true);
        
        this.titles = titles;
        
        try {
            Mnemonics mnemonics = new Mnemonics();        
            Border border =
                BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
            
            Point point = new Point();
            owner.getLocation(point);
            
            setLocation(new Point(point.x + 162, point.y + 84));
            setPreferredSize(new Dimension(700, 600));
            setLayout(new GridBagLayout());
            
            panelName = new JPanel(new GridBagLayout());
            panelName.setBorder(border);
            
            panelAddress = new JPanel(new GridBagLayout());
            panelAddress.setBorder(border);
            
            panelBank = new JPanel(new GridBagLayout());
            panelBank.setBorder(border);
            
            panelContact = new JPanel(new GridBagLayout());
            panelContact.setBorder(border);
            
            JPanel panelCheckBoxes = new JPanel(new GridBagLayout());
            panelCheckBoxes.setBorder(border);
            
            JPanel panelButtons = new JPanel(new GridBagLayout());
            panelButtons.setBorder(border);
            
            gbcLabel = new GridBagConstraints();
            gbcLabel.insets.set(15, 5, 15, 5);
            gbcLabel.weightx = 1;
            gbcLabel.fill = GridBagConstraints.HORIZONTAL;
            gbcLabel.anchor = GridBagConstraints.WEST;        
            gbcLabel.gridwidth = 1;
            
            gbcTextField = new GridBagConstraints();
            gbcTextField.insets.set(15, 5, 15, 5);
            gbcTextField.weightx = 3.0;
            gbcTextField.fill = GridBagConstraints.HORIZONTAL;
            gbcTextField.anchor = GridBagConstraints.EAST;
            gbcTextField.gridwidth = GridBagConstraints.REMAINDER;
            
            GridBagConstraints gbcButtons = new GridBagConstraints();
            gbcButtons.insets.set(15, 60, 15, 60);
            gbcButtons.weightx = 0.1;
            gbcButtons.fill = GridBagConstraints.HORIZONTAL;
            gbcButtons.anchor = GridBagConstraints.CENTER;
            gbcButtons.gridwidth = 1;
            
            GridBagConstraints gbcWest = new GridBagConstraints();
            gbcWest.weightx = 0.1;
            gbcWest.weighty = 0.1;
            gbcWest.fill = GridBagConstraints.BOTH;
            gbcWest.anchor = GridBagConstraints.WEST;
            gbcWest.gridwidth = GridBagConstraints.RELATIVE;
            
            GridBagConstraints gbcEast = new GridBagConstraints();
            gbcEast.weightx = 0.1;
            gbcEast.weighty = 0.1;
            gbcEast.fill = GridBagConstraints.BOTH;
            gbcEast.anchor = GridBagConstraints.EAST;
            gbcEast.gridwidth = GridBagConstraints.REMAINDER;
            
            HashMap<String, JComponent> fields =
                new LinkedHashMap<String, JComponent>();
            HashMap<String, JCheckBox> checkBoxes =
                new LinkedHashMap<String, JCheckBox>();
            
            //Liste mit Feldern erstellen
            for (Map.Entry<String, String> eS:
                    address.getElementsString().entrySet()) {            
                String key = eS.getKey();
                
                if (key.equals(Titles.titleAddressA)) {
                    fields.put(key, new ComboBox(
                            new ModelAddressAddress(titles, eS.getValue())));
                }
                else if (key.equals(Titles.titleCountryA)) {
                    JComboBox comboBox = new ComboBox(
                            new ModelAddressCountry(titles, eS.getValue()));
                    
                    comboBox.setEditable(true);
                    fields.put(key, comboBox);
                }
                else {
                    fields.put(eS.getKey(), new TextField(eS.getValue()));
                }
            }
    
            //Liste mit CheckBoxen erstellen
            for (Map.Entry<String, Boolean> eB:
                    address.getElementsBoolean().entrySet()) {
                checkBoxes.put(eB.getKey(), new JCheckBox("", eB.getValue()));
            }
            
            //Textfelder auf Panel aufsetzen
            for (Map.Entry<String, JComponent> tf: fields.entrySet()) {
                add(tf);
            }
            
            //CheckBoxen auf Panel aufsetzen        
            for (Map.Entry<String, JCheckBox> cb: checkBoxes.entrySet()) {            
                panelCheckBoxes.add(
                        new JLabel(titles.getString(cb.getKey())),gbcLabel);
                panelCheckBoxes.add(cb.getValue(), gbcLabel);
            }
            
            //Buttons erstellen und hinzufügen
            JButton buttonSave = new Button(titles.getString(
                    Titles.buttonTitleSaveAddress), mnemonics,
                    new ActionListenerSave(controller, address, newAddress,
                            fields, checkBoxes));
            getRootPane().setDefaultButton(buttonSave);        
            panelButtons.add(buttonSave, gbcButtons);
            
            JButton buttonCancel = new Button(titles.getString(
                    Titles.buttonTitleCancelAddress), mnemonics,
                    new ActionListenerClose(this));
            panelButtons.add(buttonCancel, gbcButtons);
            
            //Panels hinzufügen
            add(panelName, gbcWest);
            add(panelAddress, gbcEast);
            add(panelContact, gbcWest);
            add(panelBank, gbcEast);
            gbcEast.anchor = GridBagConstraints.CENTER;
            add(panelCheckBoxes, gbcEast);
            add(panelButtons, gbcEast);
            
            pack();
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void add(Map.Entry<String, JComponent> entry) {
        String key = entry.getKey();
        JPanel panel = new JPanel();
        
        if (key.equals(Titles.titleCompanyA) ||
                key.equals(Titles.titleAddressA) ||
                key.equals(Titles.titleSurnameA) ||
                key.equals(Titles.titleForenameA)) {
            panel = panelName;
        }
        else if (key.equals(Titles.titleStreetA) ||
                key.equals(Titles.titleStreetNumberA) ||
                key.equals(Titles.titlePostalCodeA) ||
                key.equals(Titles.titleCityA) ||
                key.equals(Titles.titleCountryA)) {
            panel = panelAddress;
        }
        else if (key.equals(Titles.titleTelefoneNumberA) ||
                key.equals(Titles.titleMobileNumberA) ||
                key.equals(Titles.titleFaxNumberA) ||
                key.equals(Titles.titleEmailAddressA)) {
            panel = panelContact;
        }
        else if (key.equals(Titles.titleBankA) ||
                key.equals(Titles.titleBankCodeA) ||
                key.equals(Titles.titleAccountNumberA)) {
            panel = panelBank;
        }

        panel.add(new JLabel(titles.getString(key)), gbcLabel);
        panel.add(entry.getValue(), gbcTextField);
    }
    
    
    /**ActionListener for saving the edided or new address.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerSave implements ActionListener {
        
        private InterfaceController controller;
        private ItemAddress address;
        private boolean newAddress;
        private HashMap<String, JComponent> fields;
        private HashMap<String, JCheckBox> checkBoxes;
        
        private ActionListenerSave(InterfaceController controller,
                ItemAddress address, boolean newAddress,
                HashMap<String, JComponent> fields,
                HashMap<String, JCheckBox> checkBoxes) {
            this.controller = controller;
            this.address = address;
            this.newAddress = newAddress;
            this.fields = fields;
            this.checkBoxes = checkBoxes;
        }
                
        public void actionPerformed(ActionEvent event) {
            try {           
                Map<String, String> textFieldEntries =
                    new LinkedHashMap<String, String>();
                Map<String, Boolean> checkBoxEntries =
                    new LinkedHashMap<String, Boolean>();
                
                for (Map.Entry<String, JComponent> e: fields.entrySet()) {
                    textFieldEntries.put(
                            e.getKey(), ((Component)e.getValue()).getContent());
                }
                
                for (Map.Entry<String, JCheckBox> e: checkBoxes.entrySet()) {
                    checkBoxEntries.put(e.getKey(), e.getValue().isSelected());
                }
                    
                if (newAddress) {
                    controller.commitCommand(new CommandSaveNewAddress(
                            address, textFieldEntries, checkBoxEntries));
                }
                else {
                    controller.commitCommand(new CommandSaveAddress(
                            address, textFieldEntries, checkBoxEntries));
                }                   
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            dispose();
        }
    }
    
    
    /**Inner sub-class of JTextField with extra method getContent() implemented
     * from inner interface Component.
     * 
     * @author Antje Huber
     *
     */
    private class TextField extends JTextField implements Component {
        
        public TextField(String text) {
            setText(text);
        }
        
        public String getContent() {
            return getText();
        }
    }
    
    /**Inner sub-class of JComboBox with extra method getContent() implemented
     * from inner interface Component.
     * 
     * @author Antje Huber
     *
     */
    private class ComboBox extends JComboBox implements Component {
        
        public ComboBox(Model model) {
            setModel(model);
        }
         
        public String getContent() {
            Object item = getSelectedItem();
            try {
                return ((Item)item).getInternalName();
            } catch (Exception e) {
                return item.toString();
            }
        }
    }
    
    /**Interface Component providing method getContent().
     * 
     * @author Antje Huber
     *
     */
    private interface Component {
        public String getContent();
    }
}