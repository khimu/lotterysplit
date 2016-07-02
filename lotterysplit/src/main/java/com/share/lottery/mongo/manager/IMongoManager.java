package com.share.lottery.mongo.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public interface IMongoManager {
	
	public DBCursor findInArray(String fieldName, Object value);
	
	public void searchAndUpdateField(String key, Object value, String updateField, Object updateValue);
	
	public void searchAndUpdateList(String key, Object value, String updateField, Object updateValue);
	
	public DBCursor findInListTypeString(String fieldName, List<String> values);
	
	public DBCursor findInListTypeLong(String fieldName, List<Long> values);

	public void insert(String json);
	
	public void insert(DBObject dbObject);
	
	public List<String> readData(String key, Object value);	
	
	public List<String> readData(Map<String, Object> params);
	
	public DBCursor readDataDbCursor(String key, Object value);
	
	public void update(String json, String key, Object value);
	
	public void update(DBObject newObject, String key, Object value);
	
	public void remove(String json);
	
	public void remove(DBObject dbObject);
	
	public void removeAllDocuments();
	
	public void printAllDocuments();
	
	public List<String> readSublist(DBObject object, int start, int limit);
	
	public DBCollection getCollection();
}
