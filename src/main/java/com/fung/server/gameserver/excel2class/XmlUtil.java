package com.fung.server.gameserver.excel2class;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * @author skytrc@163.com
 * @date 2020/5/25 10:54
 */
@Deprecated
@Component
public class XmlUtil {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    /**
     * 创建文档，用于存储DOM，最后转化为XML
     * @return Document
     */
    public Document createDocument() throws ParserConfigurationException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        // 设置XML声明中standalone为yes，即没有dtd和schema作为该XML的说明文档，且不显示该属性
        document.setXmlStandalone(true);
        return document;
    }

    /**
     * 创建<list></list>节点
     * @return list Element
     */
    public Element createList(Document document) {
        return document.createElement("list");
    }

    /**
     * 创建<model></model>节点
     * @return model Element
     */
    public Element createModel(Document document) {
        return document.createElement("model");
    }

    /**
     * 创建<tag></tag>节点
     * @param tag 标签名
     * @param value 值
     * @return tag Element
     */
    public Element createElement(Document document, String tag, String value) {
        Element element = document.createElement(tag);
        element.setTextContent(value);
        return element;
    }

    /**
     * 将document转化为XML
     */
    public void document2Xml(Document document, String documentName) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        // 节点换行
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(System.getProperty("user.dir") + "\\src\\main\\resources\\config\\" + documentName + ".xml"));
    }
}
