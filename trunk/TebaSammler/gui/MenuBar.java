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
import gui.actionListener.ActionListenerSaveAs;
import gui.dialogs.DialogSettings;
import gui.listenerNotifier.ListenerGUIState;
import gui.panels.PanelTargetText;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EtchedBorder;

import model.Models;
import controller.Controller;
import controller.InterfaceController;
import controller.Titles;
import controller.commands.CommandCloseProgram;
import controller.commands.CommandOpenHelp;

/**Sub-class of JMenuBar with the menus and menuItems needed for the program.
 * 
 * @author Antje Huber
 *
 */
public class MenuBar extends JMenuBar implements ListenerGUIState {
    
    private Menu menuFile;
    private Menu menuTools;
    
    private Titles titles;
 
    public MenuBar(Controller controller, Titles titles, Models models,
            PanelTargetText panelTargetText, Mnemonics mnemonics) {
        this.titles = titles;

        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        Mnemonics mnemonicsMenu = new Mnemonics();
        
        menuFile = new Menu(titles.getString(
                Titles.menuTitleFile), mnemonicsMenu, mnemonics);
        
        menuFile.addMenuItem(Titles.menuItemTitleNew,
                new ActionListenerNew(controller, titles, models));
        menuFile.addMenuItem(Titles.menuItemTitleOpen,
                new ActionListenerOpen(controller, titles, panelTargetText));
        menuFile.addMenuItem(Titles.menuItemTitleSave,
                new ActionListenerSave(controller, titles, panelTargetText));
        menuFile.addMenuItem(Titles.menuItemTitleSaveAs,
                new ActionListenerSaveAs(controller, titles, panelTargetText));
        menuFile.addMenuItem(Titles.menuItemTitleEMail,
                new ActionListenerEMail(controller, panelTargetText));
        menuFile.addMenuItem(Titles.menuItemTitlePrint,
                new ActionListenerPrint(controller, panelTargetText));
        menuFile.addMenuItem(Titles.menuItemTitleCloseProgram,
                new ActionListenerCloseProgram(controller));
        
        add(menuFile);
        
        menuTools = new Menu(titles.getString(
                Titles.menuTitleTools), mnemonicsMenu, mnemonics);
           
        menuTools.addMenuItem(Titles.menuItemTitleAddressbook,
                new ActionListenerAddressbook(controller, titles, models));
        menuTools.addMenuItem(Titles.menuItemTitleSettings,
                new ActionListenerSettings(controller, titles, models));
        
        add(menuTools);
        
        Menu menuHelp = new Menu(titles.getString(
                Titles.menuTitleHelp), mnemonicsMenu, mnemonics);
        
        menuHelp.addMenuItem(Titles.menuItemTitleHelp,
                new ActionListenerHelp(controller));
        
        add(menuHelp);
    }
    
    private void setAllMenuFileEnabled(boolean enabled) {
        for (Component c: menuFile.getMenuComponents()) {
            c.setEnabled(enabled);
        }
    }
    
    private void setAllMenuToolsEnabled(boolean enabled) {
        for (Component c: menuTools.getMenuComponents()) {
            c.setEnabled(enabled);
        }
    }
    
    public void guiStatusChanged(GUI.Status status) {
        switch (status) {
        case START:
            setAllMenuToolsEnabled(true);
            setAllMenuFileEnabled(false);
            getMenuItem(menuFile, Titles.menuItemTitleNew).setEnabled(true);
            getMenuItem(menuFile, Titles.menuItemTitleOpen).setEnabled(true);
            getMenuItem(menuFile, Titles.menuItemTitleCloseProgram)
                    .setEnabled(true);
            break;
        default:
            setAllMenuToolsEnabled(true);
            setAllMenuFileEnabled(true);
        }
    }
    
    /**Returns the menuItem in the given menu with the given internalName.
     * 
     * @param menu
     * @param internalName
     * @return menuItem
     */
    private MenuItem getMenuItem(Menu menu, String internalName) {
        for (Component e: menu.getMenuComponents()) {
            MenuItem menuItem = (MenuItem)e;
            
            if (menuItem.getInternalName().equals(internalName)) {
                return menuItem;
            }
        }
        
        return null;
    }
    
    
    /**Sub-class of JMenu with overridden method addMenuItem().
     * 
     * @author Antje Huber
     *
     */
    private class Menu extends JMenu {
        
        private Mnemonics mnemonicsMenu;
        
        private Menu(
                String title, Mnemonics mnemonicsMenu, Mnemonics mnemonics) {
            super(title);
            
            this.mnemonicsMenu = new Mnemonics();
            
            setMnemonic(mnemonics.getMnemonic(title));
        }
                
        private void addMenuItem(String internalName, ActionListener listener) {            
            add(new MenuItem(titles.getString(internalName), internalName,
                    mnemonicsMenu, listener));
        }
    }
    
    
    /**Sub-class of JMenuItem with the additional method getInternalName().
     * 
     * @author Antje Huber
     *
     */
    private class MenuItem extends JMenuItem {
        
        private String internalName; 
        
        private MenuItem(String title, String internalName,
                Mnemonics mnemonics, ActionListener listener) {
            super(title);
            
            this.internalName = internalName;
            
            setMnemonic(mnemonics.getMnemonic(title));
            addActionListener(listener);
        }
        
        private String getInternalName() {
            return internalName;
        }
    }
    
    
    /**ActionListener for closing the program.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerCloseProgram implements ActionListener {
        
        private InterfaceController controller;
        
        private ActionListenerCloseProgram(InterfaceController controller) {
            this.controller = controller;
        }
        
        public void actionPerformed(ActionEvent e) {        
            controller.commitCommand(new CommandCloseProgram());
        }
    }
    
    
    /**ActionListener for calling the DialogSettings.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerSettings implements ActionListener {
        
        private Controller controller;
        private Titles titles;
        private Models models;
        
        private ActionListenerSettings(
                Controller controller, Titles titles, Models models) {
            this.controller = controller;
            this.titles = titles;
            this.models = models;
        }
        
        public void actionPerformed(ActionEvent e) {        
            new DialogSettings(controller, titles, models, controller.getUI());
        }
    }
    
    
    /**ActionListener for calling the FrameAddressbook.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerAddressbook implements ActionListener {
        
        private Controller controller;
        private Titles titles;
        private Models models;
        
        private ActionListenerAddressbook(Controller controller,
                Titles titles, Models models) {
            this.controller = controller;
            this.titles = titles;
            this.models = models;
        }
        
        public void actionPerformed(ActionEvent e) {        
            new FrameAddressbook(controller, titles, models);
        }
    }
    
    
    /**ActionListener for calling the Help.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerHelp implements ActionListener {
        
        private InterfaceController controller;
        
        private ActionListenerHelp(InterfaceController controller) {
            this.controller = controller;
        }
        
        public void actionPerformed(ActionEvent e) {
            controller.commitCommand(new CommandOpenHelp());
        }
    }
}
