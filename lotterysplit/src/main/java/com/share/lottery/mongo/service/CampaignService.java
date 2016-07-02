package com.share.lottery.mongo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import com.share.lottery.model.CampaignDTO;
import com.share.lottery.model.GroupDTO;
import com.share.lottery.mongo.manager.MongoManager;

@Component
public class CampaignService implements ICampaignService {

	private final static String CAMPAIGN =  "campaignName";

	private final static String BRAND = "brand";

	private final static String POOL = "pool";

	private final static String TICKET_ID = "lotteryTicketId";
	
	private final static String USERID = "userId";
	
	@Autowired
	@Qualifier("campaignMongoManager")
	private MongoManager campaignMongoManager;
		
	public List<CampaignDTO> getCampaigns(Long userId) {
		List<CampaignDTO> results = new ArrayList<CampaignDTO>();
		DBCursor cursor = campaignMongoManager.readDataDbCursor(USERID, userId);
		while(cursor.hasNext()) {
			cursor.next();

			CampaignDTO campaign = new CampaignDTO();
			campaign.setUserId((Long) cursor.curr().get(USERID));
			campaign.setBrand((String) cursor.curr().get(BRAND));
			campaign.setPool((List<String>) cursor.curr().get(POOL));
			campaign.setCampaignName((String) cursor.curr().get(CAMPAIGN));
			campaign.setLotteryTicketId((Long) cursor.curr().get(TICKET_ID));
			results.add(campaign);
		}
		
		return results;
	}
	
	public void updateCampaign(String campaignName, String email, String fbId){
		DBCollection collection = campaignMongoManager.getCollection();
		BasicDBObject cmd = new BasicDBObject();
		
		cmd.append("$push", new  BasicDBObject(POOL, email + " " + fbId));
		
		BasicDBObject query = new BasicDBObject().append(CAMPAIGN, campaignName);

		WriteResult result = collection.update(query, cmd);
		
		if(result.getN() == 0){

		}
	}
	
	public void saveCampaign(Long userId, String campaignName, String brand, Long lotteryTicketId){
		DBCollection collection = campaignMongoManager.getCollection();
		BasicDBObject insertDocument = new BasicDBObject();
		insertDocument.put(USERID, userId);
		insertDocument.put(CAMPAIGN, campaignName);
		insertDocument.put(BRAND, brand);
		insertDocument.put(TICKET_ID, lotteryTicketId);
		collection.insert(insertDocument);
	}


}
