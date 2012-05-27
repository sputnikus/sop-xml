package sop.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * XML semantic comaparator class
 *
 * @author Martin Putniorz
 * @version 24052012
 */
public class XmlSemDiff implements XmlSemDiffInterface {

    public XmlSemDiff() {
    }

    @Override
    public boolean whitespaceCompare(Element elm1, Element elm2, boolean trimOrReplace) throws NullPointerException {

        if (elm1 == null || elm2 == null) {
            throw new NullPointerException("Null parameter");
        }

        String content1 = elm1.getTextContent();
        String content2 = elm2.getTextContent();

        if (!trimOrReplace) {
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

    @Override
    public boolean orderElementEquals(Element a, Element b) {
        if (a == null) {
            throw new IllegalArgumentException("Element 1");
        }
        if (b == null) {
            throw new IllegalArgumentException("Element 2");
        }

        if (a.getFirstChild() == null) {
            return diferentOrderOfAttributes(a, b);
        } else {
            NodeList listA = justChildNodes(a);
            NodeList listB = justChildNodes(b);

            if (listA.getLength() == listA.getLength()) {
                boolean returnValue = true;

                for (int i = 0; i < listA.getLength(); i++) {
                    if (!listA.item(i).getNodeName().equals(listB.item(i).getNodeName())) {
                        return false;
                    }
                    returnValue &= orderElementEquals((Element) listA.item(i), (Element) listB.item(i));
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

    @Override
    public boolean elementEquals(Element a, Element b) {
        if (a == null) {
            throw new IllegalArgumentException("Element 1");
        }
        if (b == null) {
            throw new IllegalArgumentException("Element 2");
        }

        if (a.getFirstChild() == null) {
            return diferentOrderOfAttributes(a, b);
        } else {
            NodeList listA = justChildNodes(a);
            NodeList listB = justChildNodes(b);

            if (listA.getLength() == listA.getLength()) {
                int countOfBool = 0;
                Element grannyNode1 = null;
                Element grannyNode2 = null;
                for (int i = 0; i < listA.getLength(); i++) {
                    for (int j = 0; j < listA.getLength(); j++) {
                        //System.out.println(listA.item(i).getNodeName() + " ," + listB.item(j).getNodeName());
                        if (listA.item(i).getNodeName().equals(listB.item(j).getNodeName())) {
                            //System.out.println(listA.item(i).getNodeName() + " ," + listB.item(j).getNodeName());
                            countOfBool++;
                            //System.out.println(justChildNodes((Element) listB.item(j)).getLength());

                            /*
                            for(int m=0;m<justChildNodes((Element)listB.item(j)).getLength();m++){
                                System.out.println(justChildNodes((Element)listB.item(j)).item(m).getNodeName());
                            }
                             */

                            if (justChildNodes((Element) listB.item(j)).getLength() == 0) {
                                //System.out.println(listA.item(i).getNodeName() + " ," + listB.item(j).getNodeName());
                                //Element grannyNode1 = null;
                                //Element grannyNode2 = null;
                                Element rootEl = (Element) listB.item(j).getOwnerDocument().getDocumentElement();
                                for (int n = 0; n < justChildNodes(rootEl).getLength(); n++) {
                                    if (justChildNodes(rootEl).item(n).getNodeName().equals(listB.item(j).getNodeName())) {
                                        grannyNode1 = (Element) listA.item(i).getParentNode();
                                        grannyNode2 = (Element) listB.item(j).getParentNode();
                                       // System.out.println(grannyNode1.getNodeName());
                                    } else {
                                        grannyNode1 = (Element) listA.item(i).getParentNode().getParentNode();
                                        grannyNode2 = (Element) listB.item(j).getParentNode().getParentNode();
                                        //System.out.println(grannyNode1.getNodeName());
                                    }
                                }
                                Element e = (Element) listB.item(j);
                                if (diferentOrderOfAttributes((Element) listA.item(i), (Element) listB.item(j))) {
                                    e.getParentNode().removeChild(e);
                                    //elementEquals(grannyNode1, grannyNode2);
                                   //break;
                                } else {
                                    return false;
                                }
                            } else {
                                //System.out.println(listA.item(i) + " ," + listB.item(j));
                                if (listA.item(i) != null && listB.item(j) != null) {
                                    elementEquals((Element) listA.item(i), (Element) listB.item(j));
                                }
                            }
                        }
                        if(grannyNode1!=null && grannyNode2!=null){
                        elementEquals(grannyNode1, grannyNode2);}
                    }
                }
                if (countOfBool == listA.getLength()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Return NodeList with elements children
     *
     * @param a First element to compare
     * @return NodeList without #text and #comment node
     */
    public static NodeList justChildNodes(Element e) {
        NodeList nl = e.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if ("#text".equals(nl.item(i).getNodeName())
                    || "#comment".equals(nl.item(i).getNodeName())) {
                nl.item(i).getParentNode().removeChild(nl.item(i));
            }
        }
        return nl;
    }
}
