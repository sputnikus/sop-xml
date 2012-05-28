/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sop.xml;

import org.w3c.dom.Element;

/**
 * XML semantic comaparator interface
 * 
 * @author Martin Putniorz
 * @version 24052012
 */
public interface XmlSemDiffInterface {
    
    /**
     * Compare content of two elements in terms of excess whitespaces
     * 
     * @param elm1 First element to compare
     * @param elm2 Second element to compare
     * @param trimOrReplace Compare only for leading and trailing whitespace or 
     *      every whitespace in the string
     * @return True if element content is similar despite of whitespaces, false otherwise
     */
    boolean whitespaceCompare(Element elm1, Element elm2, boolean trimOrReplace);
    
    /**
     * Compare names of elements of two documents in terms of their order
     * 
     * @param rootEl1 First element to compare
     * @param rootEl2 Second element to compare
     * @return True if elements have same names and same order or document is similar despite of 
     *      different order, false otherwise
     */
    boolean orderElementEquals(Element a, Element b);
    
    /**
     * Compare names of elements of two documents
     * 
     * @param rootEl1 First element to compare
     * @param rootEl2 Second element to compare
     * @return True if elements have same names, false otherwise
     */
    boolean elementEquals(Element a, Element b);
    
    /**
     * Print set of differencies between two xml documents
     */
    void differencies();  
}
