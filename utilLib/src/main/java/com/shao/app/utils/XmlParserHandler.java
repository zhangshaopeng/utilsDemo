package com.shao.app.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Description:Xml解析工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class XmlParserHandler extends DefaultHandler {

    private HashMap<String, List<String>> hashMap = new HashMap<>();
    List<String> cities = new ArrayList<>();
    private String currentTag = null;    //正在解析的元素的标签
    private String currentValue = null; //当前解析的元素的值
    private String nodeName;    //当前解析的结点名称

    public XmlParserHandler() {
        this.nodeName = nodeName;
    }

    public HashMap<String, List<String>> getList() {
        return hashMap;
    }

    public HashMap<String, List<String>> getHashMap(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XmlParserHandler xmlParserHandler = new XmlParserHandler();
        saxParser.parse(inputStream, xmlParserHandler);
        return xmlParserHandler.getHashMap();
    }

    public HashMap<String, List<String>> getHashMap() {
        return hashMap;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTag = qName;
        if (attributes.getValue(0) != null) {
            currentValue = attributes.getValue(0);
        }
        if (qName.equals("province")) {
            cities = new ArrayList<>();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("province")) {
            hashMap.put(currentValue, cities);
            cities = null;
        }
        currentTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentTag != null) {
            String content = new String(ch, start, length);
            if (currentTag.equals("item")) {
                cities.add(content);
            }
        }
    }
}
