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


package gui.printables;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.JComponent;

/**Printable for printing an advertisement or en e-mail.
 * 
 * @author Antje Huber
 *
 */
public class PrintableTargetTextAdEMail implements Printable {
    
    private JComponent subject;
    private JComponent text;
    
    public PrintableTargetTextAdEMail(JComponent subject, JComponent text) {
        this.subject = subject;
        this.text = text;
    }
        
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {                
        if (pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }
        
        g.translate((int)pageFormat.getImageableX(),
                (int)pageFormat.getImageableY());
        subject.print(g);
        
        g.translate(0, 57);
        text.print(g);
        
        return Printable.PAGE_EXISTS; 
    }
}
