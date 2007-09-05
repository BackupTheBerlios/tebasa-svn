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
import gui.GUI;
import gui.Mnemonics;
import gui.actionListener.ActionListenerClose;
import gui.panels.PanelLangStyleArea;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import model.Models;
import controller.Controller;
import controller.Titles;
import controller.commands.CommandResetChosenTextModules;
import controller.commands.CommandSetItemModelCollectingArea;
import controller.commands.CommandSetItemModelLanguage;
import controller.commands.CommandSetItemModelTextStyle;

/**Class for creating a dialog which offers options for a new document.
 * 
 * @author Antje Huber
 *
 */
public class DialogNew extends JDialog {
    
    public DialogNew(Controller controller, Titles titles,
            Models models, Frame owner) {       
        super(owner, titles.getString(Titles.dialogTitleSettings), true);
        
        try {
            Point point = new Point();
            owner.getLocation(point);
            
            setLocation(new Point(point.x + 337, point.y + 234));
            setLayout(new GridBagLayout());
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 0.1;
            gbc.weighty = 0.1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            
            GridBagConstraints gbcButtons = new GridBagConstraints();
            gbcButtons.anchor = GridBagConstraints.WEST;
            gbcButtons.insets.set(10, 40, 10, 20);
            
            Border border =
                BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
            
            PanelLangStyleArea panel =
                new PanelLangStyleArea(controller, titles, models);
            panel.setBorder(border);
            
            JPanel panelButton = new JPanel(new GridBagLayout());
            panelButton.setBorder(border);
            
            Mnemonics mnemonics = new Mnemonics();
            
            JButton buttonOk = new Button(
                    titles.getString(Titles.buttonTitleOkNew), mnemonics,
                    new ActionListenerOk(controller, this, panel));
            getRootPane().setDefaultButton(buttonOk);
            panelButton.add(buttonOk, gbcButtons);
            
            JButton buttonCancel = new Button(
                    titles.getString(Titles.buttonTitleCancelNew), mnemonics,
                    new ActionListenerClose(this));
            gbcButtons.anchor = GridBagConstraints.EAST;
            gbcButtons.insets.set(10, 20, 10, 40);
            panelButton.add(buttonCancel, gbcButtons);
            
            add(panel, gbc);
            add(panelButton, gbc);
            
            pack();
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**ActionListener for setting the chosen options.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerOk implements ActionListener {

        private Controller controller;
        private DialogNew dialog;
        private PanelLangStyleArea panel;
        
        private ActionListenerOk(Controller controller, DialogNew dialog,
                PanelLangStyleArea panel) {
            
            this.controller = controller;
            this.dialog = dialog;
            this.panel = panel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.commitCommand(new CommandResetChosenTextModules());
            controller.getUI().changeStatus(GUI.Status.NEW);
            
            controller.commitCommand(new CommandSetItemModelLanguage(
                    panel.getSelectedLanguage()));
            controller.commitCommand(new CommandSetItemModelTextStyle(
                    panel.getSelectedTextStyle()));
            controller.commitCommand(new CommandSetItemModelCollectingArea(
                    panel.getSelectedCollectingArea()));
            
            dialog.dispose();
        }
    }
}
