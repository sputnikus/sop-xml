import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Main
 * 
 * @author Jan Slama 374322
 * @version 23.5.2012
 */
public class Main
{
    private Document doc1;
    private Document doc2;
    
    public static Main newInstance(URI uri1, URI uri2) throws SAXException,
        ParserConfigurationException, IOException {
        return new Main(uri1,uri2);
    }
  
    public static Node[] convertToArray(NodeList list)
    {
        int length = list.getLength();
        Node[] copy = new Node[length];
      
        for (int n = 0; n < length; ++n)
        copy[n] = list.item(n);
          
        return copy;
    }

    
    private Main(URI uri1,URI uri2) throws SAXException, ParserConfigurationException,
            IOException {
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder1 = factory1.newDocumentBuilder();
        DocumentBuilder builder2 = factory2.newDocumentBuilder();
        
        doc1 = builder1.parse(uri1.toString());
        doc2 = builder2.parse(uri2.toString()); 
        
        Element rootElement1 = doc1.getDocumentElement();
        Element rootElement2 = doc2.getDocumentElement();
        
        //vysledná pole child nodes kořenových elementů
        Node[] list1 = rootElement1.getChildNodes().convertToArray();
        Node[] list2 = rootElement2.getChildNodes().convertToArray();
    }
}
