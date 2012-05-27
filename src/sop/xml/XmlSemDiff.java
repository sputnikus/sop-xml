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
            NodeList listA = a.getChildNodes();
            NodeList listB = b.getChildNodes();

            if (listA.getLength() == listA.getLength()) {
                boolean returnValue = true;

                for (int i = 0; i < listA.getLength(); i++) {
                    if (!"#text".equals(listA.item(i).getNodeName())
                            && !"#comment".equals(listA.item(i).getNodeName())
                            && !"#text".equals(listB.item(i).getNodeName())
                            && !"#comment".equals(listB.item(i).getNodeName())) {
                        if (!listA.item(i).getNodeName().equals(listB.item(i).getNodeName())) {
                            return false;
                        }
                        returnValue &= orderElementEquals((Element) listA.item(i), (Element) listB.item(i));
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
            NodeList listA = a.getChildNodes();
            NodeList listB = b.getChildNodes();

            if (listA.getLength() == listA.getLength()) {
                int countOfBool = 0;
                for (int i = 0; i < listA.getLength(); i++) {
                    for (int j = 0; j < listA.getLength(); j++) {
                        if (!"#text".equals(listA.item(i).getNodeName())
                                && !"#text".equals(listB.item(j).getNodeName())
                                && !"#comment".equals(listA.item(i).getNodeName())
                                && !"#comment".equals(listB.item(j).getNodeName())) {
                            //System.out.println(listA.item(i).getNodeName() + " ," + listB.item(j).getNodeName());
                            if (listA.item(i).getNodeName().equals(listB.item(j).getNodeName())) {
                                //System.out.println(listA.item(i).getNodeName() + " ," + listB.item(j).getNodeName());
                                countOfBool++;
                                if (listB.item(j).getChildNodes().getLength() == 0) {
                                    //System.out.println(listA.item(i).getNodeName() + " ," + listB.item(j).getNodeName());
                                    Element grannyNode1 = (Element) listA.item(i).getParentNode().getParentNode();
                                    Element grannyNode2 = (Element) listB.item(j).getParentNode().getParentNode();
                                    Element e = (Element) listB.item(j);
                                    if (diferentOrderOfAttributes((Element) listA.item(i), (Element) listB.item(j))) {
                                        e.getParentNode().removeChild(e);
                                        elementEquals(grannyNode1, grannyNode2);
                                        break;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    elementEquals((Element) listA.item(i), (Element) listB.item(j));
                                }
                            }
                        }
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
}
