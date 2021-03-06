package com.qiqi.jiaoyou_app.pay.util;
 
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
 
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
 
/**
 * 解析微信XML字符串
 *
 * @author zengwei
 * @email zengwei@xlceb.com
 * @date 2018年2月23日 下午6:25:56
 */
public class XMLUtil {
     
    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map doXMLParse(String strxml) {
         
        Map m = new HashMap();
         
        try {
            strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
 
            if(null == strxml || "".equals(strxml)) {
                return null;
            }
             
            InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if(children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = XMLUtil.getChildrenText(children);
                }
                 
                m.put(k, v);
            }
             
            //关闭流
            in.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return m;
    }
     
    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    @SuppressWarnings("rawtypes")
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
         
        return sb.toString();
    }
     
    /**
     * 微信支付将请求参数转换为xml格式的String
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXmlQuery(SortedMap<String, String> paramMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set set = paramMap.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }
     
    /**
     * 微信支付将请求参数转换为xml格式的String
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXml(SortedMap<String, String> paramMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set set = paramMap.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
     
}