package sop.xml;

import org.w3c.dom.Element;

/**
 * XML semantic comaparator class
 * 
 * @author Martin Putniorz
 * @version 24052012
 */
public class XmlSemDiff implements XmlSemDiffInterface{
    
    public XmlSemDiff() {
        
    }
    
    @Override
    public boolean whitespaceCompare(Element elm1, Element elm2, boolean 
            trimOrReplace) throws NullPointerException {
        
        return false;
    }

}
