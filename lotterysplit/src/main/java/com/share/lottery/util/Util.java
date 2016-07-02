package com.share.lottery.util;

public class Util {

	public static boolean isNumeric(String str)
	{
	  return str.matches("\\d+(\\s\\d+)?");  //match a number with optional '-' and decimal.
	}
}
