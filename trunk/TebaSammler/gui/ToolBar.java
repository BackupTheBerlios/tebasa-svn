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

import gui.actionListener.ActionListenerEMail;
import gui.actionListener.ActionListenerNew;
import gui.actionListener.ActionListenerOpen;
import gui.actionListener.ActionListenerPrint;
import gui.actionListener.ActionListenerSave;
import gui.listenerNotifier.ListenerGUIState;
import gui.panels.PanelTargetText;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import model.Models;
import controller.Controller;
import controller.PropertyKeys;
import controller.Titles;

/**Class which creates the tollbar of the program.
 * 
 * @author Antje Huber
 *
 */
public class ToolBar extends JToolBar implements ListenerGUIState {
    
    private JButton buttonNew;
    private JButton buttonOpen;
    
    private Titles titles;
    
    private Properties properties;
    
    public ToolBar(Controller controller, Titles titles, Models models,
            PanelTargetText panelTargetText) {
        this.titles = titles;
        
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        properties = Controller.getProperties();
        
        try {
            buttonNew = addButton(PropertyKeys.dirIconNew,
                    Titles.toolTipTextTitleNew,
                    new ActionListenerNew(controller, titles, models));
            
            buttonOpen = addButton(PropertyKeys.dirIconOpen,
                    Titles.toolTipTextTitleOpen,
                    new ActionListenerOpen(
                            controller, titles, panelTargetText));
        
            addButton(PropertyKeys.dirIconSave, Titles.toolTipTextTitleSave,
                    new ActionListenerSave(
                            controller, titles, panelTargetText));

            addSeparator();

            addButton(PropertyKeys.dirIconPrint, Titles.toolTipTextTitlePrint,
                    new ActionListenerPrint(controller, panelTargetText));

            addButton(PropertyKeys.dirIconEMail, Titles.toolTipTextTitleEMail,
                    new ActionListenerEMail(controller, panelTargetText));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JButton addButton(
            String image, String internalName, ActionListener listener) throws
            Exception {
        JButton button = new Button(
                new ImageIcon(ClassLoader.getSystemResource(
                        properties.getProperty(image))),
                titles.getString(internalName), listener);
        
        add(button);
        return button;
    }
    
    /**Sets all toolbar components enabled or disabled.
     * 
     * @param enabled
     */
    private void setAllEnabled(boolean enabled) {
        for (Component e: getComponents()) {
            e.setEnabled(enabled);
        }
    }
    
    public void guiStatusChanged(GUI.Status status) {
        switch (status) {
        case START:
            setAllEnabled(false);
            buttonNew.setEnabled(true);
            buttonOpen.setEnabled(true);
            break;
        default:
            setAllEnabled(true);
        }
    }
}
