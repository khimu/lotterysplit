package com.lottery.split;

import java.io.IOException;

import junit.framework.TestCase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class LottoGameTest extends TestCase {

	@Test
	public void testParse(){
		try {
			Document doc = Jsoup.connect("http://www.powerball.com/megamillions/mm_numbers.asp").get();
			Elements titles = doc.select("b");
			Elements winning = doc.select("strong");
			System.out.println(winning.get(0).html().replaceAll("&nbsp;&nbsp;\\$", "").trim());
			
			
			String drawDate = titles.get(2).html();
			System.out.println(drawDate);
			
			StringBuilder builder = new StringBuilder();
			
			for(int i = 3; i < 9; i ++){
				builder.append(titles.get(i).child(0).html());
				builder.append(" ");
			}
			
			System.out.println(builder.toString());
	 
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/*
	public void testParseMega(){
		try {
			Document doc = Jsoup.connect("http://www.powerball.com/powerball/pb_numbers.asp").get();
			Elements titles = doc.select("b");
			Elements winning = doc.select("strong");
			
			System.out.println(winning.get(0));
			
			String drawDate = titles.get(3).html();
			System.out.println(drawDate);
			
			StringBuilder builder = new StringBuilder();
			
			for(int i = 4; i < 10; i ++){
				builder.append(titles.get(i).child(0).html());
				builder.append(" ");
			}
			
			System.out.println(builder.toString());
	 
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	*/
}
