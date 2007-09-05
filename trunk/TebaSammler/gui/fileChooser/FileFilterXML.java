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

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**Sub-class from FileFilter which filters xml files for a file chooser.
 * 
 * @author Antje Huber
 *
 */ 
public class FileFilterXML extends FileFilter {
    
    private static final String stringExtension = "xml";

    public boolean accept(File file) {
        try {
            if (file.isDirectory()) {
                return true;
            }
            
            String extension = getExtension(file);
            if (extension != null && extension.equals(stringExtension)) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {            
            e.printStackTrace();
            
            return false;
        }
    }
    
    public String getExtension(File file) {
        String extension = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            extension = s.substring(i + 1).toLowerCase();
        }
        
        return extension;
    }
    
    public String getDescription() { 
        return ".xml";
    }
}
