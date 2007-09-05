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


package controller.commands;

import gui.GUI;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

import model.models.ModelTextModule;

import controller.Titles;

/**This command saves the settings. 
 *  
 * @author Antje Huber
 * 
 */
public class CommandSaveSettings extends Command {
    
    Map<String, String> map;
       
    public CommandSaveSettings(Map<String, String> map) {        
        this.map = map;
    }
    
    /**This methods saves the settings. Because of a possible changed program
     * language all listeners will be removed from models, the titles will be
     * reloaded and a new object of class GUI will be created. 
     */
    public void execute() {
        Properties propertiesUserData = controller.getPropertiesUserData();        
        Titles titles = controller.getTitles();
        GUI gui = controller.getUI();
        
        if (map != null) {
            propertiesUserData.putAll(map);        
            try {
                propertiesUserData.storeToXML(new FileOutputStream(
                        controller.getDirPropertiesUserData()), "");
            } catch (Exception e) {
                controller.handleException(
                        Titles.writeDefaultSettingsException, e);
            }
        }
        
        controller.getModels().removeListeners();
        ((ModelTextModule)controller.getModels().get(Titles.titleTextModuleM))
                .addListeners();
        
        titles.loadLocale(controller.loadLocale());
        
        gui.dispose();
        controller.setUI(new GUI(controller, titles, controller.getModels(),
                controller.getChosenTextModules(), gui.getStatus()));
    }
}
