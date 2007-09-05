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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Node;

/**Iterator for reading xml files with DOM.
 * 
 * @author Antje Huber
 *
 */
public class DOMIterator implements Iterable<Node>, Iterator<Node> {
    
    private String over;    
    private Node nextNode;
    
    public DOMIterator(Node parent, String over) {
        this.over = over;        
        nextNode = skipUntil(parent.getFirstChild(), over);        
    }
    
    public Iterator<Node> iterator() {
        return this;
    }
    
    public boolean hasNext() {
        return nextNode != null;
    }
    
    public Node next() throws NoSuchElementException {
        Node node;
        
        if (nextNode == null) {
            throw new NoSuchElementException();
        }
        
        node = nextNode;        
        nextNode = skipUntil(nextNode.getNextSibling(), over);        
        return node;
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private Node skipUntil(Node node, String nodeName) {
        if (nodeName == null) {
            return skipUntil(node);
        }
        
        while (node != null && !node.getNodeName().equals(nodeName)) {
            node = node.getNextSibling();
        }
        
        return node;
    }
    
    private Node skipUntil(Node node) {
        while (node != null && node.getNodeName().startsWith("#")) {
            node = node.getNextSibling();
        }
        
        return node;
    }
}
