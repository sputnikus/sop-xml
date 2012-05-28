package sop.xml;

import org.w3c.dom.*;

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
    public boolean whitespaceCompare(Element elm1, Element elm2, boolean trimOrReplace) {

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
            throw new NullPointerException("Element 1");
        }
        if (b == null) {
            throw new NullPointerException("Element 2");
        }

        if (a.getFirstChild() == null) {
            return diferentOrderOfAttributes(a, b);
        } else {
            NodeList listA = justChildNodes(a);
            NodeList listB = justChildNodes(b);

            if (listA.getLength() == listB.getLength()) {
                for (int i = 0; i < listA.getLength(); i++) {
                    if (listA.item(i).getNodeName().equals(listB.item(i).getNodeName())) {
                        orderElementEquals((Element) listA.item(i), (Element) listB.item(i));
                    } else {
                        return false;
                    }
                }
                return true;
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
    private boolean diferentOrderOfAttributes(Element a, Element b) {
        NamedNodeMap attributesA = a.getAttributes();
        NamedNodeMap attributesB = b.getAttributes();

        if ((attributesA.getLength()) != (attributesB.getLength())) {
            return false;
        }
        /*
        if (!a.getTextContent().equals(b.getTextContent())) { 
            return false;
        }*/
        int j = 0;
        int s = 0;

        for (int i = 0; i < attributesA.getLength(); i++) {
            for (int h = 0; h < attributesB.getLength(); h++) {
                if (attributesA.item(i).getNodeName().equals(attributesB.item(h).getNodeName())
                        && attributesA.item(i).getNodeValue().equals(attributesB.item(h).getNodeValue())) {
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
            throw new NullPointerException("Element 1");
        }
        if (b == null) {
            throw new NullPointerException("Element 2");
        }

        if (a.getFirstChild() == null) {
            return diferentOrderOfAttributes(a, b);
        } else {
            NodeList listA = justChildNodes(a);
            NodeList listB = justChildNodes(b);

            if (listA.getLength() == listA.getLength()) {
                int countOfBool = 0;

                for (int i = 0; i < listA.getLength(); i++) {
                    for (int j = 0; j < listB.getLength(); j++) {
                        if (listA.item(i).getNodeName().equals(listB.item(j).getNodeName())
                                && diferentOrderOfAttributes((Element) listA.item(i), (Element) listB.item(j))) {
                            countOfBool++;

                            //System.out.println(justChildNodes((Element) listB.item(j)).getLength());
                            //removing child without children
                            if (justChildNodes((Element) listB.item(j)).getLength() == 0) {

                                //System.out.println(listA.item(i).getNodeName() + " ," + listB.item(j).getNodeName());
                                Element grannyNode1 = null;
                                Element grannyNode2 = null;
                                Element rootEl = (Element) listB.item(j).getOwnerDocument().getDocumentElement();

                                //setting to grannyNode parent or grandparent node
                                for (int n = 0; n < justChildNodes(rootEl).getLength(); n++) {
                                    if (justChildNodes(rootEl).item(n).getNodeName().equals(listB.item(j).getNodeName())) {
                                        grannyNode1 = (Element) listA.item(i).getParentNode();
                                        grannyNode2 = (Element) listB.item(j).getParentNode();
                                    } else {
                                        grannyNode1 = (Element) listA.item(i).getParentNode().getParentNode();
                                        grannyNode2 = (Element) listB.item(j).getParentNode().getParentNode();
                                    }
                                }
                                Element e = (Element) listB.item(j);

                                e.getParentNode().removeChild(e);
                                //after removing child go higher in tree and check children again
                                elementEquals(grannyNode1, grannyNode2);
                                //going further to the tree until find child without children
                            } else {
                                elementEquals((Element) listA.item(i), (Element) listB.item(j));
                            }
                        }
                        break;
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
    private NodeList justChildNodes(Element e) {
        NodeList list = e.getChildNodes();

        for (int i = 0; list.item(i) != null; i++) {
            Node n = list.item(i);
            if (n instanceof Text || n instanceof Comment) {
                e.removeChild(n);
                i--;
            }
        }
        return list;
    }
}
