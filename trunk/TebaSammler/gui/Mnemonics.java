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

import java.util.HashSet;

/**This class creates and manages mnemonics.
 * 
 * @author Antje Huber
 *
 */ 
public class Mnemonics {
    
    private HashSet<Character> mnemonics;
    
    public Mnemonics() {
        this.mnemonics = new HashSet<Character>();
    }

    /**Returns an int for a mnemonic.
     * It is online usable for latin letters.
     *  
     * @param title
     * @return intMnemonic
     */
    public int getMnemonic(String title) {
        
        for (int i = 0; i < title.length(); i++) {
            try {
                char c = Character.toUpperCase(title.charAt(i));
                Character character = new Character(c);
                
                if (!mnemonics.contains(character) && validMnemonic(c)) {                
                    mnemonics.add(character);
                    return (int)character;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
    
    private boolean validMnemonic(char character) {  
        return (character >= 'A' && character <= 'Z');
    }
}
