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
import gui.dialogs.DialogEditableInformation;
import gui.listenerNotifier.ListenerSettingChanged;
import gui.listenerNotifier.ListenerTextEditable;
import gui.listenerNotifier.NotifierTextEditable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import model.Models;
import model.items.Item;
import model.items.ItemTextModule;
import model.models.Model;
import model.models.ModelDocumentStyle;
import model.models.ModelTextModule;
import controller.Controller;
import controller.InterfaceController;
import controller.Titles;
import controller.commands.Command;
import controller.commands.CommandAddClosing;
import controller.commands.CommandAddSalutation;
import controller.commands.CommandAddSentence;
import controller.commands.CommandAddSubject;
import controller.commands.CommandRemoveClosing;
import controller.commands.CommandRemoveSalutation;
import controller.commands.CommandRemoveSubject;
import controller.commands.CommandRemoveTextModule;

/**Class for the panel which contains the comboBoxes for the text modules.
 * 
 * @author Antje Huber
 *
 */
public class PanelTextModules extends JPanel implements
        ListenerTextEditable {
    
    private Controller controller;
    private Titles titles;    
    private DefaultComboBoxModel ctmModel;
    private NotifierTextEditable editableNotifier;
    private Mnemonics mnemonics;
    
    private ModelTextModule tmModel;
    private ModelDocumentStyle tsModel;
    private boolean targetTextEditable;
    private JButton buttonAppend;
    private GridBagConstraints gbcPanels;
    private GridBagConstraints gbc;
    
    private JPanel panelSubjectSalutationClosing;
    private JPanel panelContent;
    private JPanel panelSubject;
    private JPanel panelSalutation;
    private JPanel panelClosing;
    
    public PanelTextModules(Controller controller, Titles titles, Models models,
            DefaultComboBoxModel ctmModel,
            NotifierTextEditable editableNotifier, Mnemonics mnemonics) {
        
        this.mnemonics = mnemonics;
        this.editableNotifier = editableNotifier;
        this.titles = titles;
        this.controller = controller;
        this.ctmModel = ctmModel;
        
        setPreferredSize(new Dimension(500, 400));
        setLayout(new GridBagLayout());
        
        targetTextEditable = false;
        
        try {
            tmModel = (ModelTextModule)models.get(Titles.titleTextModuleM);
            tsModel =
                (ModelDocumentStyle)models.get((Titles.titleDocumentStyleM));
            
            addPanels();
        
            models.get(Titles.titleDocumentStyleM).addListDataListener(
                    new ListenerSettingChanged() {                    
                        public void changed() {
                            addPanels();
                        }
                    });
        
            editableNotifier.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**Adds the individual panels on the main panel.*/
    private void addPanels() {
        if (gbc == null) {
            gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = .1;
            gbc.weighty = .1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        }
        
        removeAll();
        
        add(getPanelSubjectSalutationClosing(), gbc);
        add(getPanelContent(), gbc);
    }
    
    /**Creates a new panel for subject, salutation and closing and returns it.
     * 
     * @return panel
     */
    private JPanel getPanelSubjectSalutationClosing() {
        if (panelSubjectSalutationClosing == null) {
            panelSubjectSalutationClosing = new JPanel(new GridBagLayout());
        }
        else {
            panelSubjectSalutationClosing.removeAll();
        }
        
        panelSubjectSalutationClosing.add(getPanelSubject(), gbc);

        if (((Item)tsModel.getSelectedItem()).getInternalName()
                .equals(Titles.titleDocumentStyleAd)) {
            panelSubjectSalutationClosing.setBorder(
                    controller.getTitledBorder(Titles.borderTitleTMHeaderAd));
        }
        else {
            panelSubjectSalutationClosing.setBorder(
                    controller.getTitledBorder(
                            Titles.borderTitleTMHeaderLetterEMail));
            panelSubjectSalutationClosing.add(getPanelSalutation(), gbc);
            panelSubjectSalutationClosing.add(getPanelClosing(), gbc);
        }
        
        return panelSubjectSalutationClosing;       
    }
    
    /**Creates a new panel for the document content and returns it.
     * 
     * @return panel
     */
    private JPanel getPanelContent() {
        if (panelContent == null) {
            panelContent = new JPanel(new GridBagLayout());
            
            panelContent.setBorder(
                    controller.getTitledBorder(Titles.borderTitleTMContent));
            fillPanelContent();
        }
        
        return panelContent;
    }
    
    /**Creates a new panel for the subject returns it.
     * 
     * @return panel
     */
    private JPanel getPanelSubject() {
        if (panelSubject == null) {
            panelSubject = new JPanel(new GridBagLayout());
            
            fillPanel(panelSubject, tmModel.getModelSubject(),
                    new ItemListenerSubject(controller));
        }
        
        return panelSubject;
    }

    /**Creates a new panel for the salutation and returns it.
     * 
     * @return panel
     */
    private JPanel getPanelSalutation() {
        if (panelSalutation == null) {
            panelSalutation = new JPanel(new GridBagLayout());
            
            fillPanel(panelSalutation, tmModel.getModelSalutation(),
                    new ItemListenerSalutation(controller));
        }
        
        return panelSalutation;
    }

    /**Creates a new panel for the closing and returns it.
     * 
     * @return panel
     */
    private JPanel getPanelClosing() {
        if (panelClosing == null) {
            panelClosing = new JPanel(new GridBagLayout());
            
            fillPanel(panelClosing, tmModel.getModelClosing(),
                    new ItemListenerClosing(controller));
        }
        
        return panelClosing;
    }
    
    /**Fills a panel with components.
     * 
     * @param panel
     * @param model
     * @param listener
     */
    private void fillPanel(JPanel panel, Model model, ItemListener listener) {
        if (gbcPanels == null) {
            gbcPanels = new GridBagConstraints();            
            gbcPanels.insets.set(5, 20, 5, 20);
            gbcPanels.gridwidth = 1;
            gbcPanels.weightx = .1;
            gbcPanels.weighty = .1;
            gbcPanels.anchor = GridBagConstraints.WEST;
            gbcPanels.fill = GridBagConstraints.HORIZONTAL;
        }
        
        JComboBox cb = new JComboBox(model);        
        cb.addItemListener(listener);
        panel.add(cb, gbcPanels);
    }
    
    /**Fills the panelContent with components.*/
    private void fillPanelContent() {
        GridBagConstraints gbcTM = new GridBagConstraints();        
        gbcTM.insets.set(3, 20, 5, 20);
        gbcTM.anchor = GridBagConstraints.WEST;
        gbcTM.gridwidth = GridBagConstraints.REMAINDER;
        gbcTM.weightx = .1;
        gbcTM.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gbcCTM = new GridBagConstraints();        
        gbcCTM.insets.set(3, 20, 5, 20);
        gbcCTM.anchor = GridBagConstraints.WEST;
        gbcCTM.gridwidth = GridBagConstraints.RELATIVE;
        gbcCTM.weightx = .99;
        gbcCTM.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gbcLabel = new GridBagConstraints();        
        gbcLabel.insets.set(10, 20, 0, 20);
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.gridwidth = GridBagConstraints.REMAINDER;
        gbcLabel.weightx = .1;
        gbcLabel.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gbcButton = new GridBagConstraints();        
        gbcButton.insets.set(3, 0, 5, 20);
        gbcButton.anchor = GridBagConstraints.EAST;
        gbcButton.gridwidth = GridBagConstraints.REMAINDER;
        gbcButton.weightx = .01;
        gbcButton.fill = GridBagConstraints.HORIZONTAL;
        
        try {            
            ActionListener listener = new ActionListenerAppendRemove(
                    controller, ctmModel, editableNotifier);
            
            panelContent.add(new JLabel(titles.getString(
                    Titles.labelTitleChooseCategory)), gbcLabel);
            
            panelContent.add(new JComboBox(tmModel.getModelCategory()), gbcTM);
                 
            panelContent.add(new JLabel(
                    titles.getString(Titles.labelTitleChooseTM)), gbcLabel);
            
            JComboBox cbTextModules = new JComboBox(tmModel.getModelText());
            cbTextModules.setRenderer(new Renderer(cbTextModules));
            panelContent.add(cbTextModules, gbcTM);
            
            panelContent.add(new JLabel(
                    titles.getString(Titles.labelTitleAppendTM)), gbcLabel);
            
            panelContent.add(new JComboBox(ctmModel), gbcCTM);
            
            buttonAppend = new Button(
                    titles.getString(Titles.buttonTitleAppendTM), mnemonics,
                    listener);
            panelContent.add(buttonAppend, gbcButton);
            
            panelContent.add(new JLabel(
                    titles.getString(Titles.labelTitleRemoveTM)), gbcLabel);
            
            panelContent.add(new JComboBox(ctmModel), gbcCTM);
            
            JButton buttonRemove = new Button(
                    titles.getString(Titles.buttonTitleRemoveTM), mnemonics,
                    listener);
            panelContent.add(buttonRemove, gbcButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void editableChanged(boolean editable) {
        targetTextEditable = editable;
    }
    
    public boolean getTargetTextEditable() {
        return targetTextEditable;
    }
    
    private JButton getButtonAppend() {
        return buttonAppend;
    }
    
    
    /**Renderer for ComboBox items.
     * In the comboBox for content are JTextAreas used as Items to create 
     * comboBox items which can have more than one row.
     * 
     * @author Antje Huber
     *
     */
    private class Renderer extends JTextArea implements ListCellRenderer {
        
        private ListCellRenderer defaultRenderer;
        private JComboBox comboBox;
        
        public Renderer(JComboBox comboBox) {
            this.comboBox = comboBox;
            
            defaultRenderer = new DefaultListCellRenderer();
                        
            setEditable(false);
            setLineWrap(true);
            setWrapStyleWord(true);
            
            Component c = defaultRenderer.getListCellRendererComponent(
                    new JList(), "", -1, false, false);
            setFont(c.getFont());
        }
        
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            Component c = defaultRenderer.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            if (index < 0) {
                return c;
            }
            
            setText(value != null ? value.toString() : "");
            setForeground(c.getForeground());
            setBackground(c.getBackground());
            
            setSize(new Dimension(comboBox.getWidth() - 20, Integer.MAX_VALUE));

            return this;
        }
    }
    
    
    /**ItemListener for the ComboBox which contains the subjects.
     * 
     * @author Antje Huber
     *
     */
    private class ItemListenerSubject implements ItemListener {
        
        private InterfaceController controller;
        
        private ItemListenerSubject(InterfaceController controller) {
            this.controller = controller;
        }
        
        public void itemStateChanged(ItemEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            int selectedIndex = cb.getSelectedIndex();
            
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (selectedIndex == 0) {
                    controller.commitCommand(new CommandRemoveSubject());
                }
                else {
                    controller.commitCommand(new CommandAddSubject(
                            (ItemTextModule)cb.getSelectedItem()));
                }
            }
        }
    }
    
    
    /**ItemListener for the ComboBox which contains the salutations.
     * 
     * @author Antje Huber
     *
     */
    private class ItemListenerSalutation implements ItemListener {
        
        private InterfaceController controller;
        
        private ItemListenerSalutation(InterfaceController controller) {
            this.controller = controller;
        }
        
        public void itemStateChanged(ItemEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            int selectedIndex = cb.getSelectedIndex();
            
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (selectedIndex == 0) {
                    controller.commitCommand(new CommandRemoveSalutation());
                }
                else {
                    controller.commitCommand(new CommandAddSalutation(
                            (ItemTextModule)cb.getSelectedItem()));
                }
            }
        }
    }
    
    
    /**ItemListener for the ComboBox which contains the closing.
     * 
     * @author Antje Huber
     *
     */
    private class ItemListenerClosing implements ItemListener {
        
        private InterfaceController controller;
        
        private ItemListenerClosing(InterfaceController controller) {
            this.controller = controller;
        }
        
        public void itemStateChanged(ItemEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            int selectedIndex = cb.getSelectedIndex();
            
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (selectedIndex == 0) {                  
                    controller.commitCommand(new CommandRemoveClosing());
                }
                else {
                    controller.commitCommand(new CommandAddClosing(
                            (ItemTextModule)cb.getSelectedItem()));
                }
            }
        }
    }
    
    
    /**ActionListener for appeding or removing text modules from choise.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerAppendRemove implements ActionListener {
        
        private Controller controller;
        private DefaultComboBoxModel ctmModel;
        private NotifierTextEditable editableNotifier;
        
        private ActionListenerAppendRemove(Controller controller,
                DefaultComboBoxModel ctmModel,
                NotifierTextEditable editableNotifier) {
            this.controller = controller;
            this.ctmModel = ctmModel;
            this.editableNotifier = editableNotifier;
        }
        
        public void actionPerformed(ActionEvent e) {
            Command command;
            
            if (e.getSource().equals(getButtonAppend())) {
                command = new CommandAddSentence(ctmModel.getIndexOf(
                        (ItemTextModule)ctmModel.getSelectedItem()) + 1);
            }
            else {
                command = new CommandRemoveTextModule();
            }
            
            if (getTargetTextEditable()) {
                new DialogEditableInformation(
                        controller, titles, command, editableNotifier);
            }
            else {
                controller.commitCommand(command);
            }
        }
    }
}
