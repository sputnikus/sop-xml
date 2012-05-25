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
     * Compare elements of two documents in terms of their order
     * 
     * @param rootEl1 First root element of document to be compared
     * @param rootEl2 Second root element of document to be compared
     * @param similarity Compare if it is similar or equal
     * @return True if elements have same order or document is similar despite of 
     *      different order, false otherwise
     */
    boolean orderCompare(Element rootEl1, Element rootEl2, boolean similarity);
}
