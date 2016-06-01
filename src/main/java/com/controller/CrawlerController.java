package com.controller;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.model.LinkModel;

/**
 * Example program to list links from a URL.
 */
public class CrawlerController {
    public static void main(String[] args) throws IOException {
        String url = "http://www.babytree.com/";
        List<LinkModel> linkModels = getLinks(url,"宝宝");
        print("size=[%s]", linkModels.size());
    }
    private static List<LinkModel>  getLinks(String url,String keyWord){
    	List<LinkModel> results = Lists.newArrayList();
    	print("Fetching %s...", url);
		try{
		        Document doc = Jsoup.connect(url).get();
		        Elements links = doc.select("a[href]");
		        print("\nLinks: (%d)", links.size());
		        for (Element link : links) {
		            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		            if(link.text().contains("")){
		            	LinkModel model = new LinkModel();
			            model.setFromUrl(url);
			            model.setTitle(trim(link.text(), 35));
			            model.setUrl(link.attr("abs:href"));
			            results.add(model);
		            }
		            
		        }
		        
		        Elements media = doc.select("[src]");
		        Elements imports = doc.select("link[href]");
		
		        /*print("\nMedia: (%d)", media.size());
		        for (Element src : media) {
		            if (src.tagName().equals("img"))
		                print(" * %s: <%s> %sx%s (%s)",
		                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
		                        trim(src.attr("alt"), 20));
		            else
		                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		        }
		
		        print("\nImports: (%d)", imports.size());
		        for (Element link : imports) {
		            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
		        }*/
		
		        
		}catch(Exception e){
			print("fail to crawler from url:%s", url);
		}
    	return results;
    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}