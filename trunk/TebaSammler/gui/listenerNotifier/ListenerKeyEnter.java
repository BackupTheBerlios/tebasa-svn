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


package gui.listenerNotifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**KeyListener for Enter.
 * 
 * @author Antje Huber
 *
 */
public class ListenerKeyEnter implements KeyListener {
    
    private ActionListener actionListener;
    private String actionCommand;
    
    public ListenerKeyEnter(
            ActionListener actionListener, String actionCommand) {
        this.actionCommand = actionCommand;
        this.actionListener = actionListener;
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER ||
                e.getKeyCode() == KeyEvent.VK_SPACE) {
            actionListener.actionPerformed(new ActionEvent(
                    this, ActionEvent.ACTION_PERFORMED, actionCommand));
            
            e.consume();
        }
    }
    
    public void keyReleased(KeyEvent e) {}
    
    public void keyTyped(KeyEvent e) {}
}
