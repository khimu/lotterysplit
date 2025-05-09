package com.share.lottery.service;

import java.util.List;

public interface ISpyMemcachedHandler {
	
	public void deleteCache(String key);
	
	public Object getObject(String key);
	
	public void cacheObject(String key, Object obj);
	
	public List<Object> addAnItem(final String key, final Object newItem);

}
