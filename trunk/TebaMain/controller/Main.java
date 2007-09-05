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


package controller;

import javax.swing.JOptionPane;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String version = System.getProperty("java.version");
        String message =
            "Java Runtime Environment is too old, at least version 5 is needed.";
        String title = "Error";
        
        if (version.startsWith("1.3") || version.startsWith("1.4")) {
            JOptionPane.showMessageDialog(
                    null, message, title, JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        else {
            Controller.getInstance();
        }
    }
}
