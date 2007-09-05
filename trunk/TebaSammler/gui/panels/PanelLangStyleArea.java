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


package gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Models;
import model.items.Item;
import controller.Controller;
import controller.PropertyKeys;
import controller.Titles;

/**Class creating a panel which is used by class DialogNew. So it contains
 * components for the document options.
 * 
 * @author Antje Huber
 *
 */
public class PanelLangStyleArea extends JPanel {
    
    private JComboBox comboBoxL;
    private JComboBox comboBoxTS;
    private JComboBox comboBoxCA;
    
    private Properties properties;
    private GridBagConstraints gbcComboBox;
    private GridBagConstraints gbcLabel;
    
    private Titles titles;
    private Models models;
            
    public PanelLangStyleArea(
            Controller controller, Titles titles, Models models) {
        this.titles = titles;
        this.models = models;       
        
        gbcLabel = new GridBagConstraints();
        gbcComboBox = new GridBagConstraints();
        properties = controller.getPropertiesUserData();
        comboBoxL = new JComboBox();
        comboBoxTS = new JComboBox();
        comboBoxCA = new JComboBox();
        
        gbcLabel.insets.set(25, 10, 25, 10);
        gbcLabel.gridwidth = 1;
        gbcLabel.anchor = GridBagConstraints.WEST;
        
        gbcComboBox.insets.set(25, 10, 25, 10);        
        gbcComboBox.gridwidth = GridBagConstraints.REMAINDER;
        gbcComboBox.anchor = GridBagConstraints.EAST;
        
        try {
            setLayout(new GridBagLayout());
            
            addLabel(Titles.titleLanguageM);
            comboBoxL = addComboBox(
                    Titles.titleLanguageM, PropertyKeys.defaultLanguagePair);
            addLabel(Titles.titleDocumentStyleM);
            comboBoxTS = addComboBox(Titles.titleDocumentStyleM,
                    PropertyKeys.defaultDocumentStyle);
            addLabel(Titles.titleCollectingAreaM);
            comboBoxCA = addComboBox(Titles.titleCollectingAreaM,
                    PropertyKeys.defaultCollectingArea);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JComboBox addComboBox(String internalName, String defaultItem) {
        JComboBox cb = new JComboBox(models.get(internalName).getAllItems());
        
        cb.setSelectedItem(
                new Item((String)properties.get(defaultItem), titles));
        add(cb, gbcComboBox);
        return cb;
    }
    
    private void addLabel(String internalName) {
     add(new JLabel(titles.getString(internalName)), gbcLabel);
    }
    
    public Item getSelectedTextStyle() {
        return (Item)comboBoxTS.getSelectedItem();
    }
    
    public Item getSelectedLanguage() {
        return (Item)comboBoxL.getSelectedItem();
    }
    
    public Item getSelectedCollectingArea() {
        return (Item)comboBoxCA.getSelectedItem();
    }
}
