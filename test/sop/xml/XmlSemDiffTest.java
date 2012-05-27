package sop.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * XmlSemDiff test class
 *  
 * @author Martin Putniorz
 * @version 24052012
 */
public class XmlSemDiffTest {
    
    private static DocumentBuilderFactory dFact;
    private static DocumentBuilder build;
    private static Document doc;
    private static Element leadingWhitespace;
    private static Element trailingWhitespace;
    private static Element leadingAndTrailingWhitespace;
    private static Element innerWhitespace;
    
    private static Document doc1;
    private static Document doc2;
    private static Element rootElement;
    private static Element rootElement1;
    private static Element rootElement2;
    private static Element trailingWhitespace1;
    private static Element trailingWhitespace2;
    private static Element leadingAndTrailingWhitespace1;
     private static Element innerWhitespace2;
    
    public XmlSemDiffTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        dFact = DocumentBuilderFactory.newInstance();
        build = dFact.newDocumentBuilder();
        doc = build.newDocument();
        leadingWhitespace = doc.createElement("leadingWhitespace");
        trailingWhitespace = doc.createElement("trailingWhitespace");
        leadingAndTrailingWhitespace = doc.createElement("leadingAndTrailingWhitespace");
        innerWhitespace = doc.createElement("innerWhitespace");
        
        rootElement = doc.createElement("rootElement");
        doc.appendChild(rootElement);
        rootElement.appendChild(leadingWhitespace);
        rootElement.appendChild(trailingWhitespace);
        trailingWhitespace.appendChild(leadingAndTrailingWhitespace);
        leadingAndTrailingWhitespace.appendChild(innerWhitespace);
        
        doc1 = build.newDocument();
        rootElement1 = doc1.createElement("rootElement");
        doc1.appendChild(rootElement1);
        rootElement1.appendChild(doc1.createElement("leadingWhitespace"));
        trailingWhitespace1 = doc1.createElement("trailingWhitespace");
        rootElement1.appendChild(trailingWhitespace1);
        leadingAndTrailingWhitespace1 = doc1.createElement("leadingAndTrailingWhitespace");
        trailingWhitespace1.appendChild(leadingAndTrailingWhitespace1);
        leadingAndTrailingWhitespace1.appendChild(doc1.createElement("innerWhitespace"));
        
        doc2 = build.newDocument();
        rootElement2 = doc2.createElement("rootElement");
        doc2.appendChild(rootElement2);
        rootElement2.appendChild(doc2.createElement("leadingWhitespace"));
        trailingWhitespace2 = doc2.createElement("trailingWhitespace");
        rootElement2.appendChild(trailingWhitespace2);
        innerWhitespace2 = doc2.createElement("innerWhitespace");
        trailingWhitespace2.appendChild(innerWhitespace2);
        innerWhitespace2.appendChild(doc2.createElement("leadingAndTrailingWhitespace"));
    }

    @Before
    public void setUp() throws SAXException, ParserConfigurationException {
        leadingWhitespace.setTextContent("      test");
        trailingWhitespace.setTextContent("test         ");
        leadingAndTrailingWhitespace.setTextContent("  test     ");
        innerWhitespace.setTextContent("te st");
    }

    @Test
    public void testLeadAndTrailWhitespaceCompare() {
        XmlSemDiffInterface diff = new XmlSemDiff();
        
        assertTrue(diff.whitespaceCompare(leadingWhitespace, trailingWhitespace, false));
        assertTrue(diff.whitespaceCompare(leadingWhitespace, leadingAndTrailingWhitespace, false));
        assertTrue(diff.whitespaceCompare(trailingWhitespace, leadingAndTrailingWhitespace, false));
        assertFalse(diff.whitespaceCompare(innerWhitespace, leadingAndTrailingWhitespace, false));
    }
    
    @Test
    public void testInnerWhitespaceCompare() {
        XmlSemDiffInterface diff = new XmlSemDiff();
        
        assertTrue(diff.whitespaceCompare(innerWhitespace, leadingWhitespace, true));
        assertTrue(diff.whitespaceCompare(innerWhitespace, trailingWhitespace, true));
        assertTrue(diff.whitespaceCompare(innerWhitespace, leadingAndTrailingWhitespace, true));
    }
    
    @Test
    public void testOrderElementEquals() {
        XmlSemDiffInterface diff = new XmlSemDiff();
        
        assertTrue(diff.orderElementEquals(doc.getDocumentElement(), doc1.getDocumentElement()));
        assertFalse(diff.orderElementEquals(doc.getDocumentElement(), doc2.getDocumentElement()));
    }
    
    @Test
    public void testElementEquals() {
        XmlSemDiffInterface diff = new XmlSemDiff();
        
        //assertTrue(diff.elementEquals(doc.getDocumentElement(), doc1.getDocumentElement()));
        assertFalse(diff.elementEquals(doc.getDocumentElement(), doc2.getDocumentElement()));
    }

}
