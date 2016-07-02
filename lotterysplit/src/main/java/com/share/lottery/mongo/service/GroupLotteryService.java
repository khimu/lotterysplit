package com.share.lottery.mongo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.share.lottery.model.BalanceSheetDTO;
import com.share.lottery.model.GroupDTO;
import com.share.lottery.mongo.manager.MongoManager;

@Component
public class GroupLotteryService implements IGroupLotteryService {
	
	// default groups
	public final static String PRIVATE = "private";
	public final static String PUBLIC = "public";
	
	private final static String GROUP_NAME = "groupName";
	
	private final static String TICKETS = "tickets";
	
	private final static String USERIDS = "userIds";
	
	private final static String EMAILS = "emails";
	
	private final Log log = LogFactory.getLog(getClass());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Qualifier("groupLotteryMongoManager")
	private MongoManager groupLotteryMongoManager;
	
	@Autowired
	public GroupLotteryService(@Qualifier("groupLotteryMongoManager") MongoManager groupLotteryMongoManager){
		this.groupLotteryMongoManager = groupLotteryMongoManager;
		GroupDTO publicGroup = getPublicGroup();
		if(publicGroup == null){
			BasicDBObject insertDocument = new BasicDBObject();
			insertDocument.put(GROUP_NAME, "public");
	
			groupLotteryMongoManager.insert(insertDocument);
		}/*else{
			while(publicGroup.getTickets() == null || publicGroup.getTickets().size() == 0){
				BasicDBObject insertDocument = new BasicDBObject();
				insertDocument.put(GROUP_NAME, "public");
				this.groupLotteryMongoManager.remove(insertDocument);
				publicGroup = getPublicGroup();
			}
		}*/
	}
		
	public List<GroupDTO> getGroups(Long userId) {
		List<GroupDTO> results = new ArrayList<GroupDTO>();
		DBCursor cursor = groupLotteryMongoManager.findInArray(USERIDS, userId);
		while(cursor.hasNext()) {
			cursor.next();

			GroupDTO group = new GroupDTO();
			group.setEmails((List<String>) cursor.curr().get(EMAILS));
			group.setUserIds((List<Long>) cursor.curr().get(USERIDS));
			group.setGroupName((String) cursor.curr().get(GROUP_NAME));
			group.setTickets((List<Long>) cursor.curr().get(TICKETS));
			results.add(group);
		}
		
		return results;
	}
	
	public List<GroupDTO> getGroups(String email) {
		List<GroupDTO> results = new ArrayList<GroupDTO>();
		DBCursor cursor = groupLotteryMongoManager.findInArray(EMAILS, email);
		while(cursor.hasNext()) {
			cursor.next();

			GroupDTO group = new GroupDTO();
			group.setEmails((List<String>) cursor.curr().get(EMAILS));
			group.setUserIds((List<Long>) cursor.curr().get(USERIDS));
			group.setGroupName((String) cursor.curr().get(GROUP_NAME));
			group.setTickets((List<Long>) cursor.curr().get(TICKETS));
			results.add(group);
		}
		
		return results;
	}
	
	public GroupDTO getPublicGroup() {
		DBCursor cursor = groupLotteryMongoManager.readDataDbCursor(GROUP_NAME, "public");
		while(cursor.hasNext()) {
			cursor.next();

			GroupDTO group = new GroupDTO();
			group.setEmails((List<String>) cursor.curr().get(EMAILS));
			group.setUserIds((List<Long>) cursor.curr().get(USERIDS));
			group.setGroupName((String) cursor.curr().get(GROUP_NAME));
			group.setTickets((List<Long>) cursor.curr().get(TICKETS));
			return group;
		}
		
		return null;
	}
	
	public boolean groupNameAlreadyExist(String groupName){
		DBCollection collection = groupLotteryMongoManager.getCollection();
	    
		BasicDBObject query = new BasicDBObject();
		query.put(GROUP_NAME, groupName);

	    DBCursor result = collection.find(query);
	    
	    if(result.hasNext()){
	    	return true;
	    }
	    return false;
	}
	
	public boolean userExistInGroup(Long userId, String groupName){
		DBCollection collection = groupLotteryMongoManager.getCollection();
	    
		BasicDBObject query = new BasicDBObject();
		query.put(GROUP_NAME, groupName);

	    DBCursor result = collection.find(query);
	    if(result.hasNext()){
	    	result.next();

			GroupDTO group = new GroupDTO();
			group.setUserIds((List<Long>) result.curr().get(USERIDS));

			for(Long id : group.getUserIds()){
				if(userId.equals(id)){
					return true;
				}
			}
	    }
	    
	    return false;
	}
	
	public boolean userExistInGroup(String email, String groupName){
		DBCollection collection = groupLotteryMongoManager.getCollection();
	    
		BasicDBObject query = new BasicDBObject();
		query.put(GROUP_NAME, groupName);
		
	    //BasicDBObject eleMatch = new BasicDBObject();
	    //eleMatch.put(EMAILS, email);
	    
	    //BasicDBObject up = new BasicDBObject();
	    //up.put("$in",email);

	    DBCursor result = collection.find(query);
	    if(result.hasNext()){
	    	result.next();

			GroupDTO group = new GroupDTO();
			group.setEmails((List<String>) result.curr().get(EMAILS));

			
			for(String e : group.getEmails()){
				if(email.equals(e)){
					return true;
				}
			}
	    }
	    
	    return false;
	}

	
	public void saveOrUpdateGroupTicket(String groupName, Long ticketId){
		groupLotteryMongoManager.searchAndUpdateList(GROUP_NAME, groupName.toLowerCase(), TICKETS, ticketId);
	}
	
	public void saveOrUpdateGroupMember(String groupName, Long userId){
		groupLotteryMongoManager.searchAndUpdateList(GROUP_NAME, groupName.toLowerCase(), USERIDS, userId);
	}
	
	public void saveOrUpdateGroupMember(String groupName, String email){
		groupLotteryMongoManager.searchAndUpdateList(GROUP_NAME, groupName.toLowerCase(), EMAILS, email);
	}

	@Override
	public void saveOrUpdateGroupMembers(String groupName, String[] emails) {
		DBCollection collection = groupLotteryMongoManager.getCollection();
		BasicDBObject cmd = new BasicDBObject();
		
		for(Object updateValue : emails){
			cmd.append("$push", new  BasicDBObject(EMAILS, updateValue));
		}

		WriteResult result = collection.update(new BasicDBObject().append(GROUP_NAME, groupName), cmd);
		
		if(result.getN() == 0){
			BasicDBObject insertDocument = new BasicDBObject();
			insertDocument.put(GROUP_NAME, groupName);
			insertDocument.put(EMAILS, emails);
			groupLotteryMongoManager.insert(insertDocument);
		}		
	}
	
	@Override
	public void saveOrUpdateGroupMembers(String groupName, String[] emails, List<Long> userIds) {
		DBCollection collection = groupLotteryMongoManager.getCollection();
		BasicDBObject cmd = new BasicDBObject();
		
		for(Object updateValue : emails){
			cmd.append("$push", new  BasicDBObject(EMAILS, updateValue));
		}
		
		for(Object updateValue : userIds){
			cmd.append("$push", new  BasicDBObject(USERIDS, updateValue));
		}

		WriteResult result = collection.update(new BasicDBObject().append(GROUP_NAME, groupName), cmd);
		
		if(result.getN() == 0){
			BasicDBObject insertDocument = new BasicDBObject();
			insertDocument.put(GROUP_NAME, groupName);
			insertDocument.put(EMAILS, emails);
			insertDocument.put(USERIDS, userIds);
			groupLotteryMongoManager.insert(insertDocument);
		}		
	}
	
	@Override
	public void saveOrUpdateGroupMembers(String groupName, List<Long> userIds) {	
		groupLotteryMongoManager.searchAndUpdateList(GROUP_NAME, groupName.toLowerCase(), USERIDS, userIds);
	}
}
