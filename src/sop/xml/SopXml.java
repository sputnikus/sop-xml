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

        XmlSemDiffInterface diff = new XmlSemDiff();
        diff.elementEquals(subor1.doc.getDocumentElement(), subor2.doc.getDocumentElement());
        diff.orderElementEquals(subor1.doc.getDocumentElement(), subor2.doc.getDocumentElement());
    }
  }