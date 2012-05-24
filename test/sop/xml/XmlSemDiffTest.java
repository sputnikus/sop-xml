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
    
    public XmlSemDiffTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        dFact = DocumentBuilderFactory.newInstance();
        build = dFact.newDocumentBuilder();
        doc = build.newDocument();
        leadingWhitespace = doc.createElement("leadingWhitespace");
        trailingWhitespace = doc.createElement("trailingWhitespace");
        leadingAndTrailingWhitespace = doc.createElement("leadingWhitespace");
        innerWhitespace = doc.createElement("leadingWhitespace");
    }

    @Before
    public void setUp() throws SAXException, ParserConfigurationException {
        leadingWhitespace.appendChild(doc.createTextNode("      test"));
        trailingWhitespace.appendChild(doc.createTextNode("test         "));
        leadingAndTrailingWhitespace.appendChild(doc.createTextNode("  test     "));
        innerWhitespace.appendChild(doc.createTextNode("te st"));
    }

    @Test
    public void testLeadAndTrailWhitespaceCompare() {
        XmlSemDiffInterface diff = new XmlSemDiff();
        
        assertTrue(diff.whitespaceCompare(leadingWhitespace, trailingWhitespace, false));
        assertTrue(diff.whitespaceCompare(leadingWhitespace, leadingAndTrailingWhitespace, false));
        assertTrue(diff.whitespaceCompare(trailingWhitespace, leadingAndTrailingWhitespace, false));
        assertFalse(diff.whitespaceCompare(innerWhitespace, leadingAndTrailingWhitespace, false));
    }
    
    public void testInnerWhitespaceCompare() {
        XmlSemDiffInterface diff = new XmlSemDiff();
        
        assertTrue(diff.whitespaceCompare(innerWhitespace, leadingWhitespace, true));
        assertTrue(diff.whitespaceCompare(innerWhitespace, trailingWhitespace, true));
        assertTrue(diff.whitespaceCompare(innerWhitespace, leadingAndTrailingWhitespace, true));
    }

}
