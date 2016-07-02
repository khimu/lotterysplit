package com.share.lottery.mongo.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class MongoManager implements IMongoManager {

	protected final Log log = LogFactory.getLog(getClass());
	
	protected DB db;
	
	protected String collectionName;
	
	public MongoManager(DB db, String collectionName){
		this.db = db;
		this.db.authenticate("admin", new char[]{'u','e','l','5','6','6','E','n','v','b','b','H'});

		this.collectionName = collectionName;
		
		/*
		if(db.collectionExists(collectionName) == false){
			//BasicDBObject config = new BasicDBObject().append("convertToCapped", collectionName).append("size", 2147483648L);  // 2gb
			BasicDBObject config = new BasicDBObject().append("convertToCapped", collectionName).append("size", 104857600); // 100mb
			db.createCollection(collectionName, config);
		}
		*/
		
		
	}

	public void insert(String json) {
		try {
			DBCollection collection = db.getCollection(collectionName);
			DBObject dbObject = (DBObject) JSON.parse(json);
			collection.insert(dbObject, new WriteConcern());
		} catch (Exception e) {
			log.error("Unable to store " + json + " to " + collectionName);
		}
	}
	
	public void insert(DBObject dbObject) {
		try {
			DBCollection collection = db.getCollection(collectionName);
			collection.insert(dbObject, new WriteConcern());
		} catch (Exception e) {
			log.error("Unable to store " + dbObject.toString() + " to " + collectionName);
		}
	}
	
	public void update(String json, String key, Object value) {
		try {
			DBCollection collection = db.getCollection(collectionName);
			DBObject newObject = (DBObject) JSON.parse(json);
			collection.update(new BasicDBObject().append(key, value), newObject);
		} catch (Exception e) {
			log.error("Unable to connect to Mongodb : " + e.getMessage());
		}
	}
	
	public void update(DBObject newObject, String key, Object value) {
		try {
			DBCollection collection = db.getCollection(collectionName);
			collection.update(new BasicDBObject().append(key, value), newObject);
		} catch (Exception e) {
			log.error("Unable to connect to Mongodb : " + e.getMessage());
		}
	}
	
	public void removeAllDocuments() {
        try {
			DBCollection collection = db.getCollection(collectionName);
			collection.remove(new BasicDBObject());
		} catch (Exception e) {
			log.error("Unable to connect to Mongodb : " + e.getMessage());
		}		
	}

	public void printAllDocuments() {
		try {
			DBCollection collection = db.getCollection(collectionName);
			DBCursor cursor = collection.find();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} catch (Exception e) {
			log.error("Unable to connect to Mongodb : " + e.getMessage());
		}
	}
	
	public void remove(DBObject dbObject) {
		try {
			DBCollection collection = db.getCollection(collectionName);
			collection.remove(dbObject);
        } catch (Exception e) {
			log.error("Unable to connect to Mongodb : " + e.getMessage());
            }
        }

	public void remove(String json) {
		try {
			DBCollection collection = db.getCollection(collectionName);

			DBObject newObject = (DBObject) JSON.parse(json);

			collection.remove(newObject);
        } catch (Exception e) {
			log.error("Unable to connect to Mongodb : " + e.getMessage());
            }
        }

	public List<String> readData(String key, Object value) {
		List<String> jsons = new ArrayList<String>();

		try {
			DBCollection collection = db.getCollection(collectionName);
			
			BasicDBObject query = new BasicDBObject();
			query.put(key, value);

			// run query
			DBCursor resultCursor = collection.find(query);

			while (resultCursor.hasNext()) {
				String resultString = resultCursor.next().toString();
				jsons.add(resultString);
			}
		} catch (Exception e) {
			log.error("Unable to connect to Mongodb " + e.getMessage());
		}

		return jsons;
	}
	
	public List<String> readData(Map<String, Object> params) {
		List<String> jsons = new ArrayList<String>();

		try {
			DBCollection collection = db.getCollection(collectionName);
			
			BasicDBObject query = new BasicDBObject();
			
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.put(entry.getKey(), entry.getValue());
			}

			// run query
			DBCursor resultCursor = collection.find(query);

			while (resultCursor.hasNext()) {
				String resultString = resultCursor.next().toString();
				jsons.add(resultString);
			}
		} catch (Exception e) {
			log.error("Unable to connect to Mongodb " + e.getMessage());
		}

		return jsons;
	}
	
	public DBCursor readDataDbCursor(String key, Object value) {
		try {
			DBCollection collection = db.getCollection(collectionName);
			
			BasicDBObject query = new BasicDBObject();
			query.put(key, value);
			return collection.find(query);
		} catch (Exception e) {
			log.error("Unable to connect to Mongodb " + e.getMessage());
		}

		return null;
	}
	
	public void searchAndUpdateField(String key, Object value, String updateField, Object updateValue){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append(updateField, updateValue));
		//db.students.update( { _id: 1, grades: 80 }, { $set: { "grades.$" : 82 } } )
	 
		BasicDBObject searchQuery = new BasicDBObject().append(key, value);
	 
		WriteResult result = collection.update(searchQuery, newDocument);
		
		if(result.getN() == 0){
			BasicDBObject insertDocument = new BasicDBObject();
			insertDocument.put(key, value);
			insertDocument.put(updateField, updateValue);
			this.insert(insertDocument);
		}
	}
	
	public void searchAndUpdateList(String key, Object value, String updateField, Object updateValue) {
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject cmd = new BasicDBObject().append("$push", new  BasicDBObject(updateField, updateValue));
		
		
		WriteResult result = collection.update(new BasicDBObject().append(key, value), cmd);
		
		if(result.getN() == 0){
			BasicDBObject insertDocument = new BasicDBObject();
			insertDocument.put(key, value);
			List<Object> list = new ArrayList<Object>();
			list.add(updateValue);
			insertDocument.put(updateField, list);
			this.insert(insertDocument);
		}
	}
	
	public void searchAndUpdateList(String key, Object value, String updateField, List updateValues) {
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject cmd = new BasicDBObject();
		
		for(Object updateValue : updateValues){
			cmd.append("$push", new  BasicDBObject(updateField, updateValue));
		}

		WriteResult result = collection.update(new BasicDBObject().append(key, value), cmd);
		
		if(result.getN() == 0){
			BasicDBObject insertDocument = new BasicDBObject();
			insertDocument.put(key, value);
			insertDocument.put(updateField, updateValues);
			this.insert(insertDocument);
		}
	}
	
	public DBCursor findInArray(String fieldName, Object value){
		DBCollection collection = db.getCollection(collectionName);
	    BasicDBObject eleMatch = new BasicDBObject();
	    eleMatch.put(fieldName, value);

	    DBObject query = new BasicDBObject(fieldName, value);
	    return collection.find(query);
	}
	
	public DBCursor findInListTypeString(String fieldName, List<String> values){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject inQuery = new BasicDBObject();
		inQuery.put(fieldName, new BasicDBObject("$in", values));
		return collection.find(inQuery);
	}
	
	public DBCursor findInListTypeLong(String fieldName, List<Long> values){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject inQuery = new BasicDBObject();
		inQuery.put(fieldName, new BasicDBObject("$in", values));
		return collection.find(inQuery);
	}
	
	public List<String> readSublist(DBObject sortCriteria, int start, int limit) {
		List<String> jsons = new ArrayList<String>();
		try {
			DBCollection collection = db.getCollection(collectionName);

			DBCursor cursorDocJSON = collection.find(); 


			cursorDocJSON.sort(sortCriteria); //.limit(4);
			cursorDocJSON.skip(start).limit(limit);
			while (cursorDocJSON.hasNext()) {
				jsons.add(cursorDocJSON.next().toString());
			}
			
		} catch (Exception e) {
			log.error("Unable to connect to Mongodb " + e.getMessage());
		}

		return jsons;
	}
	
	public DBCollection getCollection(){
		return db.getCollection(collectionName);
	}

}
