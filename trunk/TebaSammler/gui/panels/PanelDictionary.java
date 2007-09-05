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

import gui.Button;
import gui.Mnemonics;
import gui.listenerNotifier.ListenerKeyEnter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Models;
import model.items.ItemDictionary;
import model.models.ModelDictionary;
import controller.Controller;
import controller.InterfaceController;
import controller.Titles;
import controller.commands.CommandSearchTranslation;

/**This class creates a panel with components which allow the user to insert
 * a word and search for a translation in a declared online-dictionary.
 * 
 * @author Antje Huber
 *
 */
public class PanelDictionary extends JPanel {
       
    public PanelDictionary(Controller controller, Titles titles,
            Models models, Mnemonics mnemonics) {        
        try {
            setLayout(new GridBagLayout());
            setBorder(controller.getTitledBorder(Titles.borderTitleSearch));
            
            ModelDictionary dModel =
                (ModelDictionary)models.get(Titles.titleDictionaryM);
            
            GridBagConstraints gbcTextField = new GridBagConstraints();
            gbcTextField.insets.set(15, 15, 15, 15);
            gbcTextField.fill = GridBagConstraints.BOTH;
            gbcTextField.anchor = GridBagConstraints.WEST;
            gbcTextField.weightx = 3;
            
            GridBagConstraints gbcOthers = new GridBagConstraints();
            gbcOthers.insets.set(15, 15, 15, 15);
            gbcOthers.anchor = GridBagConstraints.EAST;
            gbcOthers.weightx = 1;
            
            JTextField tfSearch = new JTextField();
            tfSearch.setText(titles.getString(Titles.textFieldTitleSearch));        
            ActionListenerSearch actionListenerSearch =
                new ActionListenerSearch(controller, dModel, tfSearch);        
            tfSearch.addKeyListener(
                    new ListenerKeyEnter(actionListenerSearch, ""));
            add(tfSearch, gbcTextField);
            
            add(new JComboBox(dModel), gbcOthers);
            
            JButton buttonSearch = new Button(
                    titles.getString(Titles.buttonTitleSearchWord), mnemonics,
                    actionListenerSearch);
            add(buttonSearch, gbcOthers);
        } catch(Exception e) {
            e.printStackTrace();            
        }
    }
    
    /**ActionListener for searching a translation for a word declared by a
     * user.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerSearch implements ActionListener {
        
        private JTextField tfSearch;
        private ModelDictionary dModel;
        private InterfaceController controller;    
        
        private ActionListenerSearch(InterfaceController controller,
                ModelDictionary dModel, JTextField tfSearch) {
            this.tfSearch = tfSearch;
            this.dModel = dModel;
            this.controller = controller;
        }
        
        public void actionPerformed(ActionEvent e) {
            controller.commitCommand(new CommandSearchTranslation(
                    (ItemDictionary)dModel.getSelectedItem(),
                    tfSearch.getText()));            
        }   
    }
}
