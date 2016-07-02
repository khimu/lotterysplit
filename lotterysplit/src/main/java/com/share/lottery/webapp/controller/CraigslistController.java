package com.share.lottery.webapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.share.lottery.mongo.service.IEmailService;
import com.share.lottery.util.Craigslist;
import com.share.lottery.util.CraigslistCategories.Category;
import com.share.lottery.util.CraigslistCities;
import com.share.lottery.util.EMailUtil;

@Controller
public class CraigslistController extends BaseController {

		public CraigslistController() {
			super(CraigslistController.class);
		}
		
		private class Gigs {
			public final static String rootCityUrl = "http://{CITY}.craigslist.org/";
			public final static String searchUrl = "/search/cpg?query=";
		}
		
		private String proxyServerUrl = "http://012ooo.proxyserver.asia/"; 

		Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-_.]+\\.[a-zA-Z0-9-.]+");
		Pattern url = Pattern.compile("(http://www\\.|www\\.|http://)[a-zA-Z0-9_.+-/]+\\.[a-zA-Z0-9-.]+");
			
		@Autowired
		private IEmailService emailService;
	
		private final static String[] exclude = {"salary", " intern "};
		
		private final static String[] ignore = {};
		
		/**
		 * name = search string
		 * locations = cities
		 * 
		 * @param start
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping(value = "/craigslist.htm", method = RequestMethod.GET)
		public void searchGigs(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
			
			String category = request.getParameter("category");
			String name = request.getParameter("name");
			String locations = request.getParameter("locations");
			
			if(name == null){
				return;
			}
			
			try {
				name = URLDecoder.decode(name, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			StringBuilder processed = new StringBuilder();
			processed.append(name);
			processed.append("\n");
			
			String[] names = name.split(",");
			if(names.length > 0){
				name = names[0].trim();
			}else{
				names = new String[]{name.trim()};
			}
			
			String suffixUrl = Gigs.searchUrl + name;
			if(StringUtils.trimToNull(category) != null){
				Category cat = Craigslist.getCategory(category);
				if(cat != null){
					suffixUrl = "/search/" + cat.name() + "?query=" + name;
				}
			}

			if(locations != null){
				try {
					locations = URLDecoder.decode(locations, "UTF-8");
					processed.append(locations);
					processed.append("\n");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				String[] locs = locations.split(",");
				
				for(String loc : locs){
					collectRegionalEmail(name, Gigs.rootCityUrl.replace("{CITY}", loc) + suffixUrl, Gigs.rootCityUrl.replace("{CITY}", loc), processed, names);
				}
			}else{
				for(String loc : CraigslistCities.cities){					
					collectRegionalEmail(name, loc + suffixUrl, loc, processed, names);
				}
			}
			
			sendRegionalEmail(processed.toString());

			PrintWriter out = response.getWriter();

			try {
				out.println(processed.toString());
			} catch (Exception e) {
				out.println("('error')");
			} finally {
				out.flush();
				out.close();
			}

		}

		private int collectRegionalEmail(String name, String link, String root, StringBuilder processed, String[] names) {
			int count = 0;
			long start = System.currentTimeMillis();

			try {
				
						
				do {
					System.out.println("Connecting to " + link);
					Document doc = Jsoup.connect(link).ignoreHttpErrors(false).get();

					Elements listings = doc.select("p.row[data-pid]");
					
					for(Element el : listings)
					{
						Element alink = el.select("span.pl a").first();
						
						Matcher find = url.matcher(alink.attr("href"));
						boolean found = false;
						while(find.find()){
							try {
								found = true;
								//System.out.println("url1 " + find.group(0).trim());
								count = findJobs(find.group(0).trim(), root, name, count, processed, names);
							}catch(Exception e){
								// do nothing
							}
						}
						
						if(found == false){
							try {
								URL url = new URL(root);
								String fullURL = String.format("%s://%s%s", url.getProtocol(), url.getHost(), alink.attr("href"));
								//System.out.println("url2 " + fullURL);
								count = findJobs(fullURL, root, name, count, processed, names);
							}catch(Exception e){
								// do nothing
							}
						}
					}
					
					Elements next = doc.select("paginator a");
					if(next != null){
						link = next.attr("href");	
					}else{
						link = null;
					}

				}while(StringUtils.trimToNull(link) != null);
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				//e1.printStackTrace();
			}

			System.out.println("time: " + (System.currentTimeMillis() - start));
			return count;
		}

		private void sendRegionalEmail(String jobListings) {
				try {
					EMailUtil.sendJobListings("craigslist.vm", "kim2kim@gmail.com", message, jobListings, mailEngine);
				} catch (final MailException me) {
					me.printStackTrace();
				}
		}
		
		private int findJobs(String link, String root, String name, int count, StringBuilder processed, String[] names) throws IOException{

			Document alink = Jsoup.connect(link).ignoreHttpErrors(false).get();
			Elements homePage = alink.select("body"); // a with href
			
			if (homePage.size() == 0) {
				return count;
			}
			
			Element pagebody = homePage.iterator().next();
			
			int countmatches = 0;
			
			for(int i = 0; i < names.length; i ++){
				Pattern searchString = Pattern.compile(names[i].trim().toLowerCase());
	
				// find all emails in home page
				Matcher m = searchString.matcher(pagebody.html().toLowerCase());
				while(m.find()){
					countmatches ++;
				}
			}
			
			boolean ignore = false;
			for(int i = 0; i < exclude.length; i ++){
				Pattern searchString = Pattern.compile(exclude[i].toLowerCase());
				
				// find all emails in home page
				Matcher m = searchString.matcher(pagebody.html().toLowerCase());
				while(m.find()){
					ignore = true;
					break;
				}
			}

			if(ignore != true){
				if(countmatches == names.length){
					//System.out.println("Found top match");
					processed.insert(0, link + " \n");
					count ++;
					sendEmailToJob(pagebody, root, link, countmatches);
				}else if(names.length - 1 > 0 && countmatches == names.length - 1){
					//System.out.println("Found medium match");
					int index = processed.toString().indexOf(" ");
					processed.insert(index, link + " \n");
					count ++;
					sendEmailToJob(pagebody, root, link, countmatches);
				}else if(countmatches > 0){
					//System.out.println("Found single match");
					processed.append(link);
					processed.append(" \n");
					count ++;
					sendEmailToJob(pagebody, root, link, countmatches);
				}else{
					System.out.println("Found no match");
				}
				
				
			}

			return count;
		}
		
		private void sendEmailToJob(Element pagebody, String root, String link, int countmatches) throws IOException{

			Element title = pagebody.select("h2.postingtitle").first(); 
			
			Element postingTime = pagebody.select("time").first();
			
			//System.out.println(title.html());
			
			Element replyLink = pagebody.select("span.replylink a").first();
			
			//System.out.println(root + replyLink.attr("href"));
			
			Document alink = Jsoup.connect(root + replyLink.attr("href")).ignoreHttpErrors(false).get();
			Elements replyPage = alink.select("body"); // a with href

			//System.out.println(replyPage.html());
			
			String email = replyPage.select("input.anonemail").attr("value");
			
			System.out.println(email);

			// find all emails in home page
			Matcher m = p.matcher(replyPage.html().toLowerCase());
			while(m.find()){
				try {
					//System.out.println(m.group(0).trim() + " " + title.html());
					EMailUtil.sendJobEmail("jobEmail.vm", email, postingTime.html() + " " + link + " " + countmatches, title.html().replace("<span class=\"star\"></span>", "").trim(), "kim2kim@gmail.com", mailEngine, message);
				} catch (final MailException me) {
					me.printStackTrace();
				}
				
			}
		}
		
		@RequestMapping(value = "/sites.htm", method = RequestMethod.GET)
		public void sites(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
			String rootUrl = "http://www.craigslist.org/about/sites";
			
			StringBuilder processed = new StringBuilder();
			
			try {
				System.out.println("Connecting to " + rootUrl);
				Document doc = Jsoup.connect(rootUrl).ignoreHttpErrors(false).get();
				
				Elements links = doc.select("ul li a");
				
				for(Element el : links)
				{
					System.out.println(el.attr("href"));
					
				}
			}catch(Exception e){
				
			}
			
			PrintWriter out = response.getWriter();

			try {
				out.println(processed.toString());
			} catch (Exception e) {
				out.println("('error')");
			} finally {
				out.flush();
				out.close();
			}

		}


}
