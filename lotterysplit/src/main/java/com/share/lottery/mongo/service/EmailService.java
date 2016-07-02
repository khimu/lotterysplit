package com.share.lottery.mongo.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import com.share.lottery.model.EmailBO;
import com.share.lottery.mongo.manager.IMongoManager;

@Component
public class EmailService implements IEmailService {
	
	public final static String EMAILS = "emails";	
	public final static String IGNORE = "ignore";
	public final static String TYPE = "type";

	@Autowired
	@Qualifier("emailMongoManager")
	public IMongoManager emailMongoManager;
	
	public void delete(String type){
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append(TYPE, type);
		
		emailMongoManager.remove(dbObject);
	}
	
	public void save(String type, String email){
		DBCollection collection = emailMongoManager.getCollection();
		BasicDBObject cmd = new BasicDBObject();
		
		cmd.append("$addToSet", new  BasicDBObject(EMAILS, email));

		WriteResult result = collection.update(new BasicDBObject().append(TYPE, type), cmd);
		
		if(result.getN() == 0){
			BasicDBObject insertDocument = new BasicDBObject();
			Set<String> emails = new HashSet<String>();
			emails.add(email);
			insertDocument.put(TYPE, type);
			insertDocument.put(EMAILS, emails);
			insertDocument.put(IGNORE, new HashSet<String>());
			emailMongoManager.insert(insertDocument);
		}		
	}
	
	public void replace(String type, EmailBO emailBo){
		BasicDBObject searchQuery = new BasicDBObject().append(TYPE, type);
		emailMongoManager.remove(searchQuery);
		
		/*
		DBCollection collection = emailMongoManager.getCollection();
		BasicDBObject unset = new BasicDBObject();
		unset.append("$pull", new BasicDBObject().append(EMAILS, new BasicDBObject("$exists", true)));
		unset.append("$pull", new BasicDBObject().append(IGNORE,  new BasicDBObject("$exists", true)));
		collection.update(new BasicDBObject().append(TYPE, type), unset);

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$push", new BasicDBObject().append(EMAILS, new BasicDBObject("$each", emailBo.getEmails())));
		newDocument.append("$push", new BasicDBObject().append(IGNORE,  new BasicDBObject("$each", emailBo.getIgnore())));
		collection.update(new BasicDBObject().append(TYPE, type), newDocument);
		*/

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put(TYPE, type);
		newDocument.put(EMAILS, emailBo.getEmails());
		newDocument.put(IGNORE, emailBo.getIgnore());
		emailMongoManager.insert(newDocument);

		//db.students.update( { _id: 1, grades: 80 }, { $set: { "grades.$" : 82 } } )
	}
	
	public void ignore(String type, String email){
		DBCollection collection = emailMongoManager.getCollection();
		BasicDBObject cmd = new BasicDBObject();
		
		cmd.append("$addToSet", new  BasicDBObject(IGNORE, email));

		WriteResult result = collection.update(new BasicDBObject().append(TYPE, type), cmd);
	}
	
	public int getCount(String type) {
		DBCollection collection = emailMongoManager.getCollection();
		
		BasicDBObject count = new BasicDBObject();
		count.append("_id",  null);
		count.append("count", new BasicDBObject().append("$sum", 1));
		
		AggregationOutput cursor = collection.aggregate(
				new BasicDBObject().append("$match", new BasicDBObject().append(TYPE, type)),
				new BasicDBObject().append("$unwind", "$"+EMAILS), new BasicDBObject("$group", count) );

		Iterator itr = cursor.results().iterator();
		if(itr.hasNext()){
			BasicDBObject obj = (BasicDBObject)itr.next();
			return obj.getInt("count");
		}
		
		return 0;
	}

	public EmailBO getEmails(String type, int pageIndex) {
		int pageSize = 500;
		DBCollection collection = emailMongoManager.getCollection();
		
		BasicDBObject searchQuery = new BasicDBObject().append(TYPE, type);
		
		DBCursor cursor = collection.find(searchQuery);
		
		//cursor.SetSortOrder(sortBy);

	  cursor.skip(pageIndex * pageSize);
	  cursor.limit(pageSize);

		while(cursor.hasNext()) {
			cursor.next();
			EmailBO emailBO = new EmailBO();
			
			Set<String> emails = new HashSet<String>();
			List<String> emailList = (List<String>) cursor.curr().get(EMAILS);
			if(emailList != null){
				emails.addAll(emailList);
			}
			
			Set<String> ignore = new HashSet<String>();
			List<String> list = (List<String>) cursor.curr().get(IGNORE);
			if(list != null){
				ignore.addAll(list);
			}
			
			emailBO.setEmails(emails);
			emailBO.setIgnore(ignore);
			emailBO.setType((String) cursor.curr().get(TYPE));
			return emailBO;
		}
		
		return null;
	}
}
