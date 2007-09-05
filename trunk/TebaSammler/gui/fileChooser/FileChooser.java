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


package gui.fileChooser;


import javax.swing.JFileChooser;

import controller.Controller;

/**Sub-class from JFileChooser with some own settings.
 * 
 * @author Antje Huber
 *
 */
public class FileChooser extends JFileChooser {
    
    public FileChooser(Controller controller, String title) {                
        setCurrentDirectory(controller.getDirDocuments());        
        setDialogTitle(title);        
        setFileFilter(new FileFilterXML());
    }
}
