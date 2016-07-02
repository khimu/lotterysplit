package com.share.lottery.util;

import com.share.lottery.util.CraigslistCategories.Category;

public final class Craigslist {

	public final static Category getCategory(String category){
		for(Category cat: Category.values()){
			if(cat.name().equals(category)){
				return cat;
			}
		}
		return null;
	}
}
