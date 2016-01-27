package net.justudio.sanguoyy.sanguoyy.util;

import net.justudio.sanguoyy.sanguoyy.model.Detail;
import net.justudio.sanguoyy.sanguoyy.model.HuiMu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 目录页
 */
public class JsoupUtil {
    private static final String GUO_XUE_SG="http://www.guoxue.com/minqingstory/sgyy/";


    public static List<HuiMu> getHuiMuList(String str){
        List<HuiMu> list=new ArrayList<HuiMu>();
        Document doc= Jsoup.parse(str);
        Element table = doc.select("table").get(1);
        table.select("script").remove();
        Elements rows=table.select("tr");
        for (int i=2;i<rows.size();i++){
            Element row = rows.get(i);
            Elements cols = row.select("td");
            String link=GUO_XUE_SG+cols.select("a").get(0).attr("href");
            String name=cols.get(0).text();

            HuiMu huiMu=new HuiMu();
            huiMu.setName(name);
            huiMu.setLink(link);
            list.add(huiMu);
        }
        return list;

    }

    /**
     * 内容页
     */

    public static List<Detail> getDetailList(String str){
        List<Detail> list = new ArrayList<Detail>();

            Document doc= Jsoup.parse(str);

            Element mainT=doc.select("table").get(1);
            Element content=mainT.select("tr").get(0);

            Detail detail=new Detail();
            detail.setContent(ToDBC(content.outerHtml()));

            list.add(detail);



        return list;

    }

    public static String ToDBC(String input) {

        char[] c = input.toCharArray();
        for (int i=0;i<c.length;i++){
            if(c[i]==12288){
                c[i] =(char) 32;
                continue;
            }
            if(c[i]>65280&c[i]<65375)
                c[i]=(char)(c[i]-65248);
        }


        return new String(c);
    }
}
