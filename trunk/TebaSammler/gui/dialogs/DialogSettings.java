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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Models;
import model.items.Item;
import controller.Controller;
import controller.InterfaceController;
import controller.PropertyKeys;
import controller.Titles;
import controller.commands.CommandSaveSettings;

/**This class creates a dialog which contains all possible setting options.
 * 
 * @author Antje Huber
 *
 */
public class DialogSettings extends JDialog {
    
    private Properties properties;
    private JPanel panelComboBoxes;
    private GridBagConstraints gbcComboBox;
    private GridBagConstraints gbcLabel;
    
    private final Models models;
    private final Titles titles;
    
    public DialogSettings(final Controller controller, final Titles titles,
            final Models models, Frame owner) {
        super(owner, titles.getString(Titles.dialogTitleDefaultSettings), true);
        
        this.models = models;
        this.titles = titles;
        
        try {        
            Mnemonics mnemonics = new Mnemonics();
    
            properties = controller.getPropertiesUserData();
            
            Point point = new Point();
            owner.getLocation(point);
            
            setLocation(new Point(point.x + 206, point.y + 159));
            setPreferredSize(new Dimension(600, 450));
            setLayout(new BorderLayout());
            
            gbcLabel = new GridBagConstraints();
            gbcLabel.insets.set(10, 25, 10, 5);
            gbcLabel.anchor = GridBagConstraints.WEST;
            gbcLabel.weightx = 0.1;
            gbcLabel.fill = GridBagConstraints.HORIZONTAL;
            gbcLabel.gridwidth = 1;
            
            GridBagConstraints gbcTextFields = new GridBagConstraints();
            gbcTextFields = new GridBagConstraints();
            gbcTextFields.insets.set(10, 0, 10, 5);
            gbcTextFields.anchor = GridBagConstraints.EAST;
            gbcTextFields.weightx = 0.1;
            gbcTextFields.fill = GridBagConstraints.BOTH;
            
            GridBagConstraints gbcButtonChoose = new GridBagConstraints();
            gbcButtonChoose = new GridBagConstraints();
            gbcButtonChoose.insets.set(10, 0, 10, 25);
            gbcButtonChoose.anchor = GridBagConstraints.EAST;
            gbcButtonChoose.gridwidth = GridBagConstraints.REMAINDER;
            
            gbcComboBox = new GridBagConstraints();
            gbcComboBox.insets.set(10, 0, 10, 25);
            gbcComboBox.anchor = GridBagConstraints.EAST;
            gbcComboBox.weightx = 0.1;
            gbcComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbcComboBox.gridwidth = GridBagConstraints.REMAINDER;
    
            GridBagConstraints gbcButton = new GridBagConstraints();
            gbcButton.insets.set(10,30,10,30);
            gbcButton.anchor = GridBagConstraints.CENTER;
                    
            panelComboBoxes = new JPanel(new GridBagLayout());
            JPanel panelButton = new JPanel(new GridBagLayout());
                        
            addLabel(Titles.titleCollectingAreaM);
            
            JComboBox cbCollectingArea = addComboBox(
                    Titles.titleCollectingAreaM,
                    PropertyKeys.defaultCollectingArea);
            
            addLabel(Titles.titleDocumentStyleM);
            
            JComboBox cbTextStyle = addComboBox(Titles.titleDocumentStyleM,
                    PropertyKeys.defaultDocumentStyle);
            
            addLabel(Titles.titleLanguageM);
            
            JComboBox cbLanguagePair = addComboBox(
                    Titles.titleLanguageM, PropertyKeys.defaultLanguagePair);
            
            addLabel(Titles.titleDictionaryM);
             
            JComboBox cbDictionary = addComboBox(
                    Titles.titleDictionaryM, PropertyKeys.defaultDictionary);
            
            addLabel(Titles.titleLocaleM);
            
            JComboBox cbLocale =
                addComboBox(Titles.titleLocaleM, PropertyKeys.defaultLocale);
            
            addLabel(Titles.labelTitleChangeBrowser);
            
            final JTextField tfChangeBrowser =
                new JTextField(controller.getPropertiesUserData().getProperty(
                        PropertyKeys.defaultBrowser));
            panelComboBoxes.add(tfChangeBrowser, gbcTextFields);
            
            JButton buttonChangeBrowser =
                new Button("...", mnemonics, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle(
                            titles.getString(Titles.dialogTitleChooseProgram));
                    
                    if (fileChooser.showOpenDialog(DialogSettings.this) ==
                            JFileChooser.APPROVE_OPTION) {
                        tfChangeBrowser.setText(
                                fileChooser.getSelectedFile().toString());
                    }
                }
            });
            panelComboBoxes.add(buttonChangeBrowser, gbcButtonChoose);
            
            addLabel(Titles.labelTitleChangeEMailClient);
            
            final JTextField tfChangeEMailClient =
                new JTextField(controller.getPropertiesUserData().getProperty(
                        PropertyKeys.defaultEMailClient));
            panelComboBoxes.add(tfChangeEMailClient, gbcTextFields);
            
            JButton buttonChangeEMailClient =
                new Button("...", mnemonics, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle(
                            titles.getString(Titles.dialogTitleChooseProgram));
                    
                    if (fileChooser.showOpenDialog(DialogSettings.this) ==
                            JFileChooser.APPROVE_OPTION) {
                        tfChangeEMailClient .setText(
                                fileChooser.getSelectedFile().toString());
                    }
                }
            });
            panelComboBoxes.add(buttonChangeEMailClient, gbcButtonChoose);
            
            JButton buttonOK = new Button(
                    titles.getString(Titles.buttonTitleOkSettings), mnemonics,
                    new ActionListenerApply(controller, cbCollectingArea,
                            cbLanguagePair, cbTextStyle, cbDictionary,
                            cbLocale, tfChangeBrowser, tfChangeEMailClient));
            getRootPane().setDefaultButton(buttonOK);
            panelButton.add(buttonOK, gbcButton);
            
            JButton buttonCancel = new Button(titles.getString(
                    Titles.buttonTitleCancelSettings), mnemonics,
                    new ActionListenerClose(this));
            panelButton.add(buttonCancel, gbcButton);
    
            add(panelComboBoxes, BorderLayout.CENTER);
            add(panelButton, BorderLayout.PAGE_END);
            
            pack();
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JComboBox addComboBox(String internalName, String defaultItem) {
        JComboBox cb = new JComboBox(models.get(internalName).getAllItems());
        
        cb.setSelectedItem(
                new Item((String)properties.get(defaultItem), titles));
        panelComboBoxes.add(cb, gbcComboBox);
        return cb;
    }
    
    private void addLabel(String internalName) {
        panelComboBoxes.add(
                new JLabel(titles.getString(internalName)), gbcLabel);
    }
    
    /**ActionListener for applying the choices.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerApply implements ActionListener {

        private InterfaceController controller;
        private JComboBox cbCollectingArea;
        private JComboBox cbLanguagePair;
        private JComboBox cbTextStyle;
        private JComboBox cbDictionary;
        private JComboBox cbLocale;
        private JTextField tfChangeBrowser;
        private JTextField tfChangeEMailClient;
        
        private ActionListenerApply(InterfaceController controller,
                JComboBox cbCollectingArea, JComboBox cbLanguagePair,
                JComboBox cbTextStyle, JComboBox cbDictionary,
                JComboBox cbLocale, JTextField tfChangeBrowser,
                JTextField tfChangeEMailClient) {
            this.cbCollectingArea = cbCollectingArea;
            this.cbLanguagePair = cbLanguagePair;
            this.cbTextStyle = cbTextStyle;
            this.cbDictionary = cbDictionary;
            this.cbLocale = cbLocale;
            this.controller = controller;
            this.tfChangeBrowser = tfChangeBrowser;
            this.tfChangeEMailClient = tfChangeEMailClient;
        }
        
        public void actionPerformed(ActionEvent e) {             
            Map<String, String> map = new HashMap<String, String>();
            
            map.put(PropertyKeys.defaultCollectingArea, ((Item)cbCollectingArea
                    .getSelectedItem()).getInternalName());            
            map.put(PropertyKeys.defaultLanguagePair,
                    ((Item)cbLanguagePair.getSelectedItem()).getInternalName());            
            map.put(PropertyKeys.defaultDocumentStyle,
                    ((Item)cbTextStyle.getSelectedItem()).getInternalName());            
            map.put(PropertyKeys.defaultDictionary,
                    ((Item)cbDictionary.getSelectedItem()).getInternalName());            
            map.put(PropertyKeys.defaultLocale,
                    ((Item)cbLocale.getSelectedItem()).getInternalName());
            map.put(PropertyKeys.defaultBrowser, tfChangeBrowser.getText());
            map.put(PropertyKeys.defaultEMailClient,
                    tfChangeEMailClient.getText());
            
            controller.commitCommand(new CommandSaveSettings(map));
            
            dispose();
        }
    }
}
