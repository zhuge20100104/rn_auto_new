//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package netsuite.utils;

import com.google.common.base.Preconditions;
import config.utils.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class NodeUtilities {
    public NodeUtilities() {
    }

    public static String getText(Node node) {
        if (node == null) {
            return "";
        } else {
            NodeList childs = node.getChildNodes();
            if (node.getNodeType() == 3) {
                return ((Text)node).getData();
            } else if (node.getNodeType() == 1 && childs != null && childs.getLength() > 0) {
                int size = childs.getLength();
                StringBuilder builder = new StringBuilder();

                for(int i = 0; i < size; ++i) {
                    Node childNode = childs.item(i);
                    if (childNode.getNodeType() == 1) {
                        builder.append(getText(childNode));
                    }

                    if (childNode.getNodeType() == 3) {
                        builder.append(((Text)childNode).getData());
                    }
                }

                String text = StringUtils.decodeHtmlEntity(builder.toString());
                text = StringUtils.replaceGroupValue(text, "&amp;", "&", true);
                return text;
            } else {
                return "";
            }
        }
    }

    public static String getConsolidatedText(Node node) {
        String text = getText(node);
        return StringUtils.consolidateText(text);
    }

    public static String getAttribute(Node node, String attributeName) {
        Preconditions.checkNotNull(attributeName);
        if (node == null) {
            return null;
        } else {
            String attributeValue = ((Element)node).getAttribute(attributeName);
            attributeValue = StringUtils.decodeHtmlEntity(attributeValue);
            return attributeValue;
        }
    }
}
