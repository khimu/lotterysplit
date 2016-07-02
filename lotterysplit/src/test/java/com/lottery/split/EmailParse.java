package com.lottery.split;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.share.lottery.model.EmailBO;
import com.share.lottery.mongo.service.IEmailService;

@ContextConfiguration( 
        locations = {"classpath:**/applicationContext-resources.xml",
                "classpath:**/applicationContext-dao.xml",
                "classpath:**/applicationContext-service.xml",
                "classpath:/test-mongoContext.xml"})
public class EmailParse extends AbstractTransactionalJUnit4SpringContextTests {
	Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-_]+\\.[a-zA-Z0-9-.]+");
	Pattern visits = Pattern.compile("visitors\\s*[0-9]+");
	Pattern url = Pattern.compile("(http://www\\.|www\\.)[a-zA-Z0-9_.+-]+\\.[a-zA-Z0-9-.]+");
	
	//Set<String> processed = new HashSet<String>();
	private StringBuilder processed = new StringBuilder();
	String type = "ny";
	int count = 0;
	static Set<String> set = new HashSet<String>();
	
	@Autowired
	private IEmailService emailService;
	
	private final static String[] ignore = { "http://www.yellowpages.com", "http://www.paypalobjects.com", "http://www.googleapis.com", "http://www.googleadservices.com", "www.youtube.com", "http://www.youtube-nocookie.com", 
		"www.yellowpages.com", "http://maps.google.com", "www.googleadservices.com",
			"http://www.google.com", "http://corporate.yp.com/",
			"http://adsolutions.yp.com/advertise-with-us",
			"http://jobs.yp.com", "http://engineering.yp.com",
			"http://corporate.yp.com/about/privacy-policy",
			"http://adsolutions.yp.com/", "http://www.yp.com",
			"http://www.anywho.com/reversephonelookup", "http://m.yp.com",
			"http://corporate.yp.com/", "http://adsolutions.yp.com", "www.godaddy.com",
			"http://www.anywho.com/", "http://www.att.com/", "www.tumblr.com",
			"http://www.wireless.att.com/home/", "http://facebook.com/yp", "www.googletagmanager.com",
			"http://twitter.com/YP", "http://google.com/+yp", "http://www.facebook.com", "www.facebook.com", 
			"http://www.twitter.com", "www.linkedin.com", "www.googleapis.com", "www.google.co.uk", "www.google-analytics.com",
			"http://www.linkedin.com", "www.amazon.com", "http://www.amazon.com", "http://www.flickr.com", "http://www.yahoo.com",
			"http://www.youtube.com", "http://www.google-analytics.com", "www.twitter.com", "www.youtube.com",
			"www.amazon.com", "www.amazonservices.com", "www.bing.com", "www.msn.com", "www.skype.com",
			"www.google.com", "www.googletagservices.com", "www.paypal.com", "www.paypalobjects.com", "www.paypal-search.com"};

	
	static {
		for(String link : ignore){
			set.add(link);
		}
	}
	
	@Test
	public void testEmail(){
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				long start = System.currentTimeMillis();
		
				for(int i = 0; i < 10; i ++){
					try {
						Document doc = Jsoup.connect("http://www.yellowpages.com/los-angeles-ca/smith?g=Los+Angeles%2C+CA&page=" + i).get();
						Elements results = doc.select("body"); // a with href
						Element href = results.iterator().next();
						Matcher page = url.matcher(href.html());
						while(page.find()){
							String link = page.group(0).trim();
							getEmails(link);
						}		
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				processed = new StringBuilder();

				System.out.println("time: " + (System.currentTimeMillis() - start));
		
				try {
					EmailBO ny = emailService.getEmails(type, 0);
					System.out.println("before: " + type + ": " + ny.getEmails().size());
		
					/*
					Set<String> uniqueEmails = new HashSet<String>(ny.getEmails());
					ny.getEmails().clear();
					ny.getEmails().addAll(uniqueEmails);
		
					emailService.replace(type, ny);
					System.out.println("after: " + type + ": " + ny.getEmails().size());
					
					ny = emailService.getEmails(type);
					System.out.println("after: " + type + ": " + ny.getEmails().size());
					*/
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("failed " + type);
				}
			}
		});
		
		t.start();
		
		while(t.isAlive());
	}
	
	private void getEmails(String link) throws IOException{
		if(set.contains(link)){
			return;
		}
		
		if(link.startsWith("http://") == false){
			link = "http://" + link;
		}

		if(processed.toString().contains( link.substring(0, link.lastIndexOf(".") ))){
			return;
		}
		
		processed.append(link);
		
		System.out.println(link);
		
		String parent = link;
		Document alink = Jsoup.connect(link).get();
		Elements homePage = alink.select("body"); // a with href
		
		if (homePage.size() == 0) {
			return;
		}
		
		Element pagebody = homePage.iterator().next();

		// find all emails in home page
		Matcher m = p.matcher(pagebody.html());
		while(m.find()){
			String contactInfo = m.group(0);
			//System.out.println(contactInfo);
			emailService.save(type, contactInfo);
		}
		
		// lots of links of a businesses page
		
		// find all links in page
		Matcher page = url.matcher(pagebody.html());
		while(page.find()){
			String subpagehref=page.group(0);
			if(subpagehref.contains(link) == false){
				if(subpagehref.contains("http://www.") == false && subpagehref.contains("www.") == false)
					subpagehref = parent +"/"+ subpagehref;
				
			}
			getEmails(subpagehref);
		}
	}

}
