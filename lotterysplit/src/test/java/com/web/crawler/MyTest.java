package com.web.crawler;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class MyTest extends TestCase {
	String emailPatternStr = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
	
	Pattern emailPattern = Pattern.compile(emailPatternStr);
	
	Pattern urlPattern = Pattern.compile("(http://www\\.|www\\.)[a-zA-Z0-9_.+-]+\\.[a-zA-Z0-9-.]+");
	
	String phoneNumberPatternStr = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";  
	
	Pattern phoneNumber = Pattern.compile(phoneNumberPatternStr);

	private String[] names = new String[] {"Tony Ezenna Personal Email"};
	
	private final static String[] ignore = { "http://www.googletagservices.com", "http://www.yoursite.com", "http://www.yellowpages.com",
			"http://www.paypalobjects.com", "http://www.googleapis.com",
			"http://www.googleadservices.com", "www.youtube.com",
			"http://www.youtube-nocookie.com", "www.yellowpages.com",
			"http://maps.google.com", "http://www.google.com",
			"http://corporate.yp.com/", "www.googleadservices.com",
			"http://adsolutions.yp.com/advertise-with-us",
			"http://jobs.yp.com", "http://engineering.yp.com",
			"http://corporate.yp.com/about/privacy-policy",
			"http://adsolutions.yp.com/", "http://www.yp.com",
			"http://www.anywho.com/reversephonelookup", "http://m.yp.com",
			"http://corporate.yp.com/", "http://adsolutions.yp.com",
			"www.godaddy.com", "http://www.anywho.com/", "http://www.att.com/",
			"www.tumblr.com", "http://www.wireless.att.com/home/",
			"http://facebook.com/yp", "www.googletagmanager.com",
			"http://twitter.com/YP", "http://google.com/+yp",
			"http://www.facebook.com", "www.facebook.com",
			"http://www.twitter.com", "www.linkedin.com", "www.googleapis.com",
			"www.google.co.uk", "www.google-analytics.com",
			"http://www.linkedin.com", "www.amazon.com",
			"http://www.amazon.com", "http://www.flickr.com",
			"http://www.yahoo.com", "http://www.youtube.com",
			"http://www.google-analytics.com", "www.twitter.com",
			"www.youtube.com", "www.amazon.com", "www.amazonservices.com",
			"www.bing.com", "www.msn.com", "www.skype.com", "www.google.com",
			"www.googletagservices.com", "www.paypal.com",
			"www.paypalobjects.com", "www.paypal-search.com" };
	

	/**
	 * name = search string
	 * locations = cities
	 * 
	 * @param start
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//@RequestMapping(value = "/google.htm", method = RequestMethod.GET)
	@Test
	public void testSearch() throws IOException {
		for(String name: names){
			searchGoogleWithQueryString(name);
		}
	}

	private int searchGoogleWithQueryString(String query) {
		int count = 0;
		long start = System.currentTimeMillis();

		try {
			String request = "http://www.google.com/search?q=" + query + "&num=20";

			Document doc = Jsoup
					.connect(request)
					.userAgent(
					  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").
					 ignoreHttpErrors(false)
					.timeout(5000).get();
			
			// get all links
			Elements links = doc.select("h3.r a");
			for (Element link : links) {
				String temp = link.attr("href");	
				if(temp.startsWith("/url?q=")){
	                // start looking for the email
					String url = temp.replace("/url?q=", "");
					String[] urls = url.split("&sa=");
					System.out.println("Sending request..." + urls[0]);
					findContactInfo(urls[0]);
				}else{
					System.out.println("Sending request..." + temp);
					findContactInfo(temp);
				}
			}
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			//e1.printStackTrace();
		}

		System.out.println("time: " + (System.currentTimeMillis() - start));
		return count;
	}
		
	private void findContactInfo(String link) throws IOException{
		try {
			Document alink = Jsoup.connect(link).ignoreHttpErrors(false).get();
			Elements replyPage = alink.select("body"); // a with href
			
			System.out.println("index " + replyPage.html().indexOf("email"));
	
			// find all emails in home page
			Matcher m = emailPattern.matcher(replyPage.html().toLowerCase());
			while(m.find()){
				System.out.println(m.group(0));
			}
			m = phoneNumber.matcher(replyPage.html().toLowerCase());
			while(m.find()){
				System.out.println(m.group(0));
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
