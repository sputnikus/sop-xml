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
    private static final String CLI_HELP = 
              "SopXml - Semantic XML comparator\n"
            + "Usage: java -jar sop-xml.jar [OPTIONS] FIRST_XML SECOND_XML\n"
            + "Options:\n"
            + "\t-h       : This help message\n"
            + "\t-v       : Verbose output\n"
            + "\t-o       : Sets element order ignoring flag\n"
            + "\t-w {1,2} : Whitespace ignoring flag (1 - triming, 2 - full replace)\n";
    
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
        boolean verbosity = false;
        boolean elementOrder = false;
        int whitespace = 0;
        String file1 = null;
        String file2 = null;
        int argsLength = args.length;
        if (argsLength < 2) {
            System.out.println(CLI_HELP);
            System.exit(0);
        }
        
        for(int i = 0; i < argsLength; i++) {
            if (args[i].equals("-h")) {
                System.out.println(CLI_HELP);
                return;
            }
            else if (args[i].equals("-v")) {
                verbosity = true;
            }
            else if (args[i].equals("-o")) {
                elementOrder = true;
            }
            else if (args[i].equals("-w")) {
                i++;
                try {
                    whitespace = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    System.err.println("Whitespace option must be an integer!");
                    System.out.println(CLI_HELP);
                    System.exit(1);
                }
                if (whitespace < 0 || whitespace > 2) {
                    System.err.println("Whitespace option must be in <0,2>!");
                    System.out.println(CLI_HELP);
                    System.exit(1);
                }
            }
            else {
                if (file1 == null) file1 = args[i];
                else file2 = args[i];
            }
        }
        
        if (file1 == null || file2 == null) {
            System.err.println("You must pass both files!");
            System.out.println(CLI_HELP);
            System.exit(1);
        }
        
        File input1 = new File(file1);
        File input2 = new File(file2);
        SopXml subor1 = newInstance(input1);
        SopXml subor2 = newInstance(input2);

        XmlSemDiffInterface diff = new XmlSemDiff(whitespace, verbosity);
        diff.elementEquals(subor1.doc.getDocumentElement(), subor2.doc.getDocumentElement());
        diff.orderElementEquals(subor1.doc.getDocumentElement(), subor2.doc.getDocumentElement());
        diff.differencies();
    }
  }
