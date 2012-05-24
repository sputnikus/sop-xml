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
        
        if(elm1 == null || elm2 == null) {
            throw new NullPointerException("Null parameter");
        }
        
        String content1 = elm1.getTextContent();
        String content2 = elm2.getTextContent();
        
        if(!trimOrReplace) {
            // Only triming
            content1 = content1.trim();
            content2 = content2.trim();
            return content1.equals(content2);
        }
        
        // Full whitespace replacement
        content1 = content1.replaceAll("\\s", "");
        content2 = content2.replaceAll("\\s", "");
        return content1.equals(content2);
    }

}
