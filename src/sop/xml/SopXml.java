package sop.xml;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Jan Slama 374322
 * @author Martin Putniorz 359735
 * @version 24052012
 */
public class SopXml {
    
    private Document doc;
    
    public static SopXml newInstance(URI uri) throws SAXException,
        ParserConfigurationException, IOException {
        return new SopXml(uri);
    }
    
    public static SopXml newInstance(File file)
            throws SAXException, ParserConfigurationException, IOException {
        return newInstance(file.toURI());
    }
  
    /**
     * Constructor creates new instance of SopXml class reading two documents at
     * given URIs
     * 
     * @param uri1 URI of the first document
     * @param uri2 URI of the second document
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException 
     */
    private SopXml(URI uri) throws SAXException, ParserConfigurationException,
            IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(uri.toString());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SAXException, 
            ParserConfigurationException, TransformerException {
        if (args.length < 1) {
            System.err.println("Firts document expected!");
            return;
        } else if (args.length < 2) {
            System.err.println("Second document expected!");
            return;
        }
        
        File input1 = new File(args[0]);
        File input2 = new File(args[1]);
        SopXml subor1 = newInstance(input1);
        SopXml subor2 = newInstance(input2);

        elementEquals(subor1.doc.getDocumentElement(), subor2.doc.getDocumentElement());
        XmlSemDiffInterface diff = new XmlSemDiff();
        diff.orderCompare(subor1.doc.getDocumentElement(), subor2.doc.getDocumentElement(), true);
    }
    
    /**
     * Compare names of two elements
     * 
     * @param a First element to compare
     * @param b Second element to compare
     * @return True, if names of two elements equals, false otherwise
     */
    public static boolean elementEquals(Element a, Element b) {
        if (a == null) {
            throw new IllegalArgumentException("Element 1");
        }
        if (b == null) {
            throw new IllegalArgumentException("Element 2");
        }

        if (a.getFirstChild() == null) {
            return diferentOrderOfAttributes(a, b);
        } else {
            NodeList listA = a.getChildNodes();
            NodeList listB = b.getChildNodes();
            if (listA.getLength() == listA.getLength()) {
                int length = listA.getLength();
                boolean returnValue = true;
                boolean value = true;
                for (int i = 0; i < length; i++) {
                    if (!"#text".equals(listA.item(i).getNodeName()) 
                            && !"#comment".equals(listA.item(i).getNodeName()) 
                            && !"#text".equals(listB.item(i).getNodeName()) 
                            && !"#comment".equals(listB.item(i).getNodeName())) {
                        value &= listA.item(i).getNodeName().equals(listB.item(i).getNodeName());
                        if (!value) {
                            return false;
                        }
                        returnValue &= elementEquals((Element) listA.item(i), (Element) listB.item(i));
                    }
                }
                return returnValue;
            } else {
                return false;
            }
        }
    }
    
    /**
     * Compare order of attributes in two elements
     * 
     * @param a First element to compare
     * @param b Second element to compare
     * @return True, if order of attributes is equal, false otherwise
     */
    public static boolean diferentOrderOfAttributes(Element a, Element b) {
        NamedNodeMap attributesA = a.getAttributes();
        NamedNodeMap attributesB = b.getAttributes();
        if ((attributesA.getLength()) != (attributesB.getLength())) {
            return false;
        }
        if (!a.getTextContent().equals(b.getTextContent())) {
            return false;
        }
        int j = 0;
        int s = 0;

        for (int i = 0; i < attributesA.getLength(); i++) {
            for (int h = 0; h < attributesB.getLength(); h++) {
                if (attributesA.item(i).equals(attributesB.item(h))) {
                    j++;
                }
                if (j >= 1) {
                    s++;
                }
            }
        }
        return s == attributesA.getLength();
    }
}
