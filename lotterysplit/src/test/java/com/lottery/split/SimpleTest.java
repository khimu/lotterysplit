package com.lottery.split;

import junit.framework.TestCase;

import org.junit.Test;

public class SimpleTest extends TestCase {

	@Test
	public void testMod(){
		int i = 3;
		int j = 15;
		
		System.out.println("i % j " + (i % j));
		System.out.println("j % i " + (j % i));
	}
}
