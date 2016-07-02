package com.share.lottery.mongo.manager;


import org.springframework.beans.factory.FactoryBean;

import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * Factory bean used to communicate with the mongodb
 * 
 * @author Yasir Siraj
 *
 */

public class CounterDBFactoryBean implements FactoryBean<DB> {
	
	private Mongo mongo;
	private String name;

	@Override
	public DB getObject() throws Exception {
		return mongo.getDB(name);
	}
	@Override 
	public Class<?> getObjectType() {
		return DB.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	/**
	 * 
	 * @param mongo
	 */
	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
