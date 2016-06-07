package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.ChannelDao;
import com.dao.CrawlerSiteDao;
import com.entity.Channel;
import com.entity.CrawlerSite;
import com.google.common.collect.Lists;
import com.model.LinkModel;
import com.util.RequestUtil;

/**
 * Example program to list links from a URL.
 */
@Controller
@RequestMapping("/crawler/")
public class CrawlerController {

	@Autowired
	private CrawlerSiteDao siteDao;
	
	@Autowired
	private ChannelDao channelDao;

	private static final Logger logger = LoggerFactory.getLogger(PaperController.class);
	
	/**
	 * 一键抓取首页
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="index")
	public String index(Model model){
		model.addAttribute("sites", siteDao.findAllSites());
		return "admin/crawler/show";
	}
	/**
	 * 一键抓取
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="queryByCondition")
	public String queryByCondition(Long siteId,
								   String url,
								   HttpServletRequest request,
								   Model model) {
		String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
		List<String> urls = Lists.newArrayList();
		if(StringUtils.isEmpty(url) && siteId != null){
			if(siteId == 0){
				urls= siteDao.findAllUrls();
			}else{
				CrawlerSite site = siteDao.find(siteId);
				if(site != null){
					urls.add(site.getUrl());
				}
			}
		}else{
			urls.add(url);
		}
		List<LinkModel> linkModels =Lists.newArrayList();
		if(!CollectionUtils.isEmpty(urls)){
			linkModels = getLinks(urls,queryTitle);
		}
		List<Channel> channels = channelDao.getRootCategory();
		model.addAttribute("channels",channels);
		model.addAttribute("sites", siteDao.findAllSites());
        model.addAttribute("links", linkModels);
        model.addAttribute("queryTitle", queryTitle);
        model.addAttribute("url", url);
		return "admin/crawler/show";
		
	}
	
	/**
	 * 站点管理列表
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="getSitesList")
	public String getSitesList(Model model){
		model.addAttribute("sites", siteDao.findAllSites());
		return "admin/crawler/sites";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="toAddSite")
	public String toAddSite(Model model){
		return "admin/crawler/addsite";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="addSite")
	public String addSite(Model model,HttpServletRequest request){
		CrawlerSite site = paserSite(null,request);
		siteDao.save(site);
		return "redirect:getSitesList";
	}

	@RequestMapping(method=RequestMethod.GET,value="toEditSite")
	public String toEditSite(@RequestParam("siteId") Long siteId,Model model){
		CrawlerSite site = siteDao.find(siteId);
		model.addAttribute("siteId", siteId);
		model.addAttribute("title", site.getTitle());
		model.addAttribute("url", site.getUrl());
		return "admin/crawler/editsite";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="editSite")
	public String editSite(Model model,HttpServletRequest request){
		Long siteId = RequestUtil.longvalue(request,"siteId");
		CrawlerSite site = paserSite(siteId,request);
		siteDao.save(site);
		return "redirect:getSitesList";
	}
	@RequestMapping(method=RequestMethod.GET,value="deleteSite") 
	public String deleteSite(@RequestParam("siteId") Long siteId) {
		
		siteDao.deleteSite(siteId);
		return "redirect:getSitesList";	
		
	}
	private CrawlerSite paserSite(Long siteId,HttpServletRequest request) {
		CrawlerSite site = null;
		if(siteId != null){
			site = siteDao.find(siteId);
		}
		if(site == null){
			site = new CrawlerSite();
		}
		site.setTitle(RequestUtil.stringvalue(request,"title"));
		site.setUrl(RequestUtil.stringvalue(request,"url"));
		
		return site;
	}
	@RequestMapping(method=RequestMethod.GET,value="left")
	public String left(){
		return "admin/crawler/left";
	}
	@RequestMapping(method=RequestMethod.GET,value="main")
	public String main(){
		return "admin/crawler/main";
	}
	
    
    private static List<LinkModel>  getLinks(List<String> urls,String keyWord){
    	List<LinkModel> results = Lists.newArrayList();
    	for(String url:urls){
    		print("Fetching %s...", url);
    		try{
    		        Document doc = Jsoup.connect(url).get();
    		        Elements links = doc.select("a[href]");
    		        print("\nLinks: (%d)", links.size());
    		        long order = 1l;
    		        for (Element link : links) {
    		            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
    		            if(StringUtils.isEmpty(keyWord) || link.text().contains(keyWord)){
    		            	//title和链接都不能为空
    		            	if(!StringUtils.isEmpty(link.text()) && !StringUtils.isEmpty(link.attr("abs:href"))){
	    		            	LinkModel model = new LinkModel();
	    		            	model.setOrder(order++);
	    			            model.setFromUrl(url);
	    			            model.setTitle(trim(link.text(), 40));
	    			            model.setUrl(link.attr("abs:href"));
	    			            model.setTrimUrl(trim(link.attr("abs:href"),70));
	    			            results.add(model);
    		            	}
    		            }
    		            
    		        }
    		        
    		        /*Elements media = doc.select("[src]");
    		        Elements imports = doc.select("link[href]");
    		
    		        print("\nMedia: (%d)", media.size());
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