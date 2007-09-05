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
import gui.listenerNotifier.NotifierTextEditable;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import controller.Controller;
import controller.InterfaceController;
import controller.Titles;
import controller.commands.CommandAddSentence;
import controller.commands.CommandResetChosenTextModules;

/**Class which creates the panel for the source text.
 * 
 * @author Antje Huber
 *
 */
public class PanelSourceText extends JPanel {
        
    public PanelSourceText(Controller controller, Titles titles,
            DefaultComboBoxModel ctmModel,
            NotifierTextEditable editableNotifier, Mnemonics mnemonics) {        
        try {
            setBorder(controller.getTitledBorder(Titles.borderTitleSourceText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setMinimumSize(new Dimension(300, 400));
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbcLabels = new GridBagConstraints();
        gbcLabels.anchor = GridBagConstraints.CENTER;
        gbcLabels.gridwidth = GridBagConstraints.REMAINDER;
        gbcLabels.fill = GridBagConstraints.BOTH;
        gbcLabels.weightx = .9;
        gbcLabels.weighty = .9;
        
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.anchor = GridBagConstraints.CENTER;
        gbcButton.gridwidth = GridBagConstraints.REMAINDER;
        gbcButton.fill = GridBagConstraints.HORIZONTAL;
        gbcButton.weightx = .1;

        JPanel panelLabels = new JPanel(new GridBagLayout());
        JPanel panelButton = new JPanel();
        panelButton.setBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        JButton buttonReset = new Button(
                titles.getString(Titles.buttonTitleResetTMs), mnemonics,
                new ActionListenerReset(controller, editableNotifier));
        panelButton.add(buttonReset);
        
        add(new JScrollPane(panelLabels), gbcLabels);
        add(panelButton, gbcButton);
            
        try {    
            ctmModel.addListDataListener(new ListDataListenerPanel(
                    controller, ctmModel, panelLabels));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**ListDataListener which controlls the text components on the panel. This
     * are the items of model ChosenTextModules.
     * 
     * @author Antje Huber
     *
     */
    private class ListDataListenerPanel implements ListDataListener {
        
        private DefaultComboBoxModel ctmModel;
        private Controller controller;
        private JPanel panelLabels;
        
        private ListDataListenerPanel(Controller controller,
                DefaultComboBoxModel ctmModel, JPanel panelLabels) {
            this.ctmModel = ctmModel;
            this.controller = controller;
            this.panelLabels = panelLabels;
        }
        
        /**If an item is added on the model a new label which contains the
         * content of the item will be added on the panel. 
         */
        public void intervalAdded(final ListDataEvent e) {
            int index = e.getIndex0();
            JLabel label = new JLabel(ctmModel.getElementAt(index).toString());
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets.set(3, 10, 3, 10);
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 0.1;
            
            int componentCount = panelLabels.getComponentCount();
            if (index >= componentCount) {
                if (componentCount > 0) {
                    ((GridBagLayout)panelLabels.getLayout()).setConstraints(
                            panelLabels.getComponent(componentCount - 1), gbc);
                }
                
                gbc.weighty = 0.1;
            }
            
            label.addMouseListener(
                    new MouseAdapterPanel(controller, panelLabels));
            label.setBorder(BorderFactory.createEtchedBorder(
                    EtchedBorder.LOWERED));                    
            panelLabels.add(label, gbc, index);

            panelLabels.validate();
        }
        
        /**If an item is removed from the model the according label will be
         * removed of the panel. 
         */
        public void intervalRemoved(final ListDataEvent e) {
            boolean lastComponent =
                e.getIndex1() == panelLabels.getComponentCount() - 1;
            
            for (int i = e.getIndex1(); i >= e.getIndex0(); i--) {
                panelLabels.remove(i);
            }
            
            int componentCount = panelLabels.getComponentCount();
            
            if (lastComponent && componentCount != 0) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets.set(3, 10, 3, 10);
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.weightx = 0.1;
                gbc.weighty = 0.1;
                ((GridBagLayout)panelLabels.getLayout()).setConstraints(
                        panelLabels.getComponent(componentCount - 1), gbc);
            }
                
           panelLabels.validate();
           panelLabels.repaint();
        }
        
        public void contentsChanged(ListDataEvent e) {}
    }
    
    /**MouseAdapter which observes mouse pressed actions on the source text.
     * 
     * @author Antje Huber
     *
     */
    private class MouseAdapterPanel extends MouseAdapter {
        
        private Controller controller;
        private JPanel panelLabels;
        
        private MouseAdapterPanel(Controller controller, JPanel panelLabels) {
            this.controller = controller;
            this.panelLabels = panelLabels;
        }

        public void mousePressed(MouseEvent me) {            
            try {
                JLabel tf = (JLabel)me.getSource();
                int i = 0;

                //Muss gefunden werden, da tf von Panel ausgelesen.
                for (; true; i++) {
                    if (tf.equals(panelLabels.getComponent(i))) {
                        break;
                    }
                }
                
                controller.commitCommand(new CommandAddSentence(i + 1));
            } catch(Exception e) {
                controller.handleException(Titles.noLabelChosenException, e);
            }
        }
    }
    
    
    /**This ActionListener is for reseting all choosed text modules.
     * 
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerReset implements ActionListener {
        
        private InterfaceController controller;
        private NotifierTextEditable editableNotifier;
        
        private ActionListenerReset(InterfaceController controller,
                NotifierTextEditable editableNotifier) {
            this.controller = controller;
            this.editableNotifier = editableNotifier;
        }
        
        public void actionPerformed(ActionEvent e) {
            editableNotifier.fireEditableChanged(false);
            controller.commitCommand(new CommandResetChosenTextModules());
        }
    }
}
