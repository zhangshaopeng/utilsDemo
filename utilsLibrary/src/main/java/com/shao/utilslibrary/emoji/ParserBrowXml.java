package com.shao.utilslibrary.emoji;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:表情解析
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/6/27
 */
public class ParserBrowXml {
    public static List<Emoji> getInfo(InputStream inputStream) {

        XmlPullParser parser = Xml.newPullParser();
        int eventType = 0;
        List<Emoji> emojis = null;
        Emoji emoji = null;
        try {
            parser.setInput(inputStream, "UTF-8");
            eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:

                        emojis = new ArrayList<Emoji>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("brow".equals(parser.getName())) {
                            emoji = new Emoji();

                        } else if ("code".equals(parser.getName())) {
                            emoji.setCode(parser.nextText());
                        } else if ("name".equals(parser.getName())) {
                            emoji.setName(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("brow".equals(parser.getName())) {
                            emojis.add(emoji);
                            emoji = null;
                        }
                        break;

                    default:
                        break;
                }

                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emojis;
    }
}
