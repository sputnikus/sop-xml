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
    
    public XmlSemDiffTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        dFact = DocumentBuilderFactory.newInstance();
        build = dFact.newDocumentBuilder();
        //doc = build.newDocument();
        doc = build.parse("people.xml");
        doc1 = build.parse("people.xml");
        doc2 = build.parse("people1.xml");
        
        leadingWhitespace = doc.createElement("leadingWhitespace");
        trailingWhitespace = doc.createElement("trailingWhitespace");
        leadingAndTrailingWhitespace = doc.createElement("leadingAndTrailingWhitespace");
        innerWhitespace = doc.createElement("innerWhitespace");
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
        
        assertTrue(diff.elementEquals(doc.getDocumentElement(), doc1.getDocumentElement()));
        assertFalse(diff.elementEquals(doc.getDocumentElement(), doc2.getDocumentElement()));
    }

}
