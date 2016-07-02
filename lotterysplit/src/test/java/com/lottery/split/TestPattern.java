package com.lottery.split;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;

public class TestPattern extends TestCase {

	@Test
	public void testMatch(){
		Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-_]+\\.[a-zA-Z0-9-.]+");
		String email = "kkk@art.com store@google.com, lord@g.com oll@dodod.com?xxx x_xx@g.com x-xx@x-xx_.com";
		Matcher m = p.matcher(email);
		while (m.find()) {
			System.out.println(m.group(0));
			System.out.println(m.groupCount());
		}
		
		
		String[] tmp = "olda@gogo.com ebergo@slls.com?".split("\\?");
		System.out.println("tmp " + tmp[0]);
	}
}
