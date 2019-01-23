package base.utils.xmlparser;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Simple xml parser class
 * <br>  Usage:
 * <br> Document doc = DocumentHelper.parseText(msgStr);
 * <br> Element root = doc.getRootElement();
 * <br> XMLParser parser = new XMLParser();
 * <br> parser.resetEles();
 * <br> Element bodyNode = parser.getChildNode(root,"v11:Body");
 * <br> currentRnMsg.getMsgs().add(bodyNode.getText());
 */
public class XMLParser {

    /**
     * private property to hold all xml elements
     */
    private  List<Element> eles;

    /**
     * Construct for xml parser
     */
    public XMLParser() {
        this.eles = new ArrayList<>();
    }

    /**
     * Reset all elements once a parse process is done.
     */
    public void resetEles() {
        this.eles = new ArrayList<>();
    }


    /**
     * Parse the specific xml node and get all xml nodes
     * @param node  The specific xml node, it always should be the root node
     * @param handler  Element parse handler
     *                 <br> See also  {@link base.utils.xmlparser.IElementHandler}
     */
    public void parseNode(Element node, IElementHandler handler) {

        //首先获取当前节点的所有属性节点

        if(handler.HandleElement(node)!=null) {
            eles.add(node);
        }

        //同时迭代当前节点下面的所有子节点
        //使用递归
        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            Element e = iterator.next();
            parseNode(e,handler);
        }
    }


    /**
     * Parse xml root node and get specific node.
     * @param root  The xml root node
     * @param nodeName  The specific node name you want to get
     * @return  The specific node
     */
    public Element getChildNode(Element root, String nodeName) {
        parseNode(root, new IElementHandler() {
            @Override
            public Element HandleElement(Element node) {
                if (node.asXML().startsWith("<"+nodeName+" ")) {
                    System.out.println(node.asXML());
                    return node;
                }
                return null;
            }
        });

        List<Element> eles = this.getEles();
        Element node = eles.get(0);
        return node;
    }


    /**
     * Get all xml node elements in this parse process
     * @return All xml node elements
     */
    public List<Element> getEles() {
        return this.eles;
    }

}
