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

import gui.dialogs.DialogError;
import gui.listenerNotifier.NotifierGUIState;
import gui.listenerNotifier.NotifierTextEditable;
import gui.panels.PanelDictionary;
import gui.panels.PanelSourceText;
import gui.panels.PanelTargetText;
import gui.panels.PanelTextModules;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import model.ChosenTextModules;
import model.Models;
import controller.Controller;
import controller.Titles;

/**Main GUI class. This class creates all components directly lying on the
 * main frame. It can have differents status, the layout of the frame depends
 * on these.
 * 
 * @author Antje Huber
 *
 */
public class GUI extends JFrame {

    private IGUI gui;
    
    public static enum Status {NEW, OPEN, START};
    public static String title;
    
    private NotifierGUIState stateNotifier;
    private NotifierTextEditable editableNotifier;
    private JPanel panel;
    private Mnemonics mnemonics;
    
    private MenuBar menuBar;
    private ToolBar toolBar;
    private PanelTextModules panelTextModules;
    private PanelSourceText panelSourceText;
    private PanelDictionary panelDictionary;
    private PanelTargetText panelTargetText;
    
    private Controller controller;
    private Titles titles;
    private Models models;
    private DefaultComboBoxModel ctmModel;
    
    public GUI(Controller controller, Titles titles, Models models,
            ChosenTextModules ctmModel, Status status) {
        this.controller = controller;
        this.titles = titles;
        this.models = models;
        this.ctmModel = ctmModel;
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            controller.handleException(Titles.setLookAndFeelException, e);
        }
        
        mnemonics = new Mnemonics();
        
        stateNotifier = new NotifierGUIState();
        
        editableNotifier = new NotifierTextEditable();
                
        panelTargetText = new PanelTargetText(controller, titles, models,
                ctmModel, editableNotifier, mnemonics);
        stateNotifier.add(panelTargetText);
        
        panelTextModules = new PanelTextModules(controller, titles, models,
                (DefaultComboBoxModel)ctmModel, editableNotifier, mnemonics);
                
        menuBar =
            new MenuBar(controller, titles, models, panelTargetText, mnemonics);
        stateNotifier.add(menuBar);
        setJMenuBar(menuBar);
        
        toolBar = new ToolBar(controller, titles, models, panelTargetText);
        stateNotifier.add(toolBar);
        add(toolBar, BorderLayout.PAGE_START);
        
        panel = new JPanel(new BorderLayout());
        add(panel);
        
        changeStatus(status);
        
        setPreferredSize(new Dimension(1024,768));
        setTitle(title = titles.getString(Titles.frameTitleGUI));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    /**Method for changing the status of the gui.
     * 
     * @param newStatus
     */
    public void changeStatus(Status newStatus) {
        if (newStatus.equals(Status.NEW) || newStatus.equals(Status.OPEN)) {            
            reset();
            
            if (panelDictionary == null) {
                panelDictionary = new PanelDictionary(
                        controller, titles, models, mnemonics);
            }
            
            if (newStatus.equals(Status.NEW)) {
                gui = new GUInew();
            }
            else if (newStatus.equals(Status.OPEN)) {
                gui = new GUIopen();
            }
        }
        else {
            gui = new GUIstart();
        }
        
        stateNotifier.fireStatusChanged(newStatus);
        pack();
    }
    
    /**Returns the status.
     * 
     * @return status
     */
    public Status getStatus() {
        return gui.getStatus();
    }
    
    /**Creates an object of the class DialogError.
     * 
     * @param text
     */
    public void printException(String text) {
        new DialogError(titles.getString(text),
                titles.getString(Titles.dialogTitleException), this);
    }
    
    private void reset() {
        panel.removeAll();
        mnemonics = new Mnemonics();
        controller.setFile(null);
        editableNotifier.fireEditableChanged(false);
    }
    
    
    /**Inner class for the status start. In this status there are no other
     * components apart from the toolbar and menubar.
     * 
     * @author Antje Huber
     *
     */
    private class GUIstart implements IGUI {
        
        public Status getStatus() {
            return Status.START;
        }
    }
    
    /**Inner class for the status new. In this status there are all possible
     * components on the frame.
     * 
     * @author Antje Huber
     *
     */
    private class GUInew implements IGUI {
        
        private GUInew() {            
            
            if (panelSourceText == null) {
                panelSourceText = new PanelSourceText(controller, titles,
                        ctmModel, editableNotifier, mnemonics);
            }
                
            panel.add(new SplitPaneHorizontal(
                    new SplitPaneVertical(panelTextModules, panelSourceText),
                    new SplitPaneVertical(panelDictionary, panelTargetText)),
                    BorderLayout.CENTER);
        }
        
        public Status getStatus() {
            return Status.NEW;
        }
    }
    
    
    /**Inner class for the status open. In this status there are the components
     * panelTextModules and panelDictionary on the frame.
     * 
     * @author Antje Huber
     *
     */
    private class GUIopen implements IGUI {
        
        private GUIopen() {
            panel.add(new SplitPaneVertical(panelDictionary, panelTargetText),
                    BorderLayout.CENTER);
        }
        
        public Status getStatus() {
            return Status.OPEN;
        }
    }
   
    
    /**Interface for the different status.
     * 
     * @author Antje Huber
     *
     */
    private interface IGUI {
        public Status getStatus();
    }
    
    
    /**Sub-class of JSplitPane with some extensions for a horizontal pane.
     * 
     * @author Antje Huber
     *
     */
    private class SplitPaneHorizontal extends JSplitPane {
        
        private SplitPaneHorizontal(
                Component component1, Component component2) {
            super(JSplitPane.HORIZONTAL_SPLIT, component1, component2);
            
            setOneTouchExpandable(true);
            setResizeWeight(0.3);
        }
    }
    
    
    /**Sub-class of JSplitPane with some extensions for a vertical pane.
     * 
     * @author Antje Huber
     *
     */
    private class SplitPaneVertical extends JSplitPane {
        
        private SplitPaneVertical(
                Component component1, Component component2) {
            super(JSplitPane.VERTICAL_SPLIT, component1, component2);
            
            setOneTouchExpandable(true);
            setResizeWeight(0);
        }
    }
}
