package com.share.lottery.mongo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.share.lottery.model.TransactionBO;
import com.share.lottery.mongo.manager.MongoManager;

/*
 * This does not handle the case when user has multiple facebook ids on 1 device
 */
//@Component("barcodesService")
public class ImageService implements IImageService {
	
	public final static String ID = "_id";
	
	public final static String USER_ID = "userId";
	public final static String CREATED_ON = "createdOn";
	public final static String BARCODE_BITMAP = "barcodeBitMap";
	public final static String COUNT = "count";

	private final Log log = LogFactory.getLog(getClass());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	//@Autowired
	//@Qualifier("transactionMongoManager")
	private MongoManager transactionMongoManager;

	public int saveImage(Long userId, byte[] bytes) {

		if (userId == null || userId == 0) {
			log.info("Error blocking users, device_id should not be null");
			return -1;
		}

		// insert
		DBObject dbObject = new BasicDBObject();
		dbObject.put(USER_ID, userId);
		dbObject.put(CREATED_ON, new Date());
		dbObject.put(BARCODE_BITMAP, bytes);
		dbObject.put(COUNT, "0");
		
		transactionMongoManager.insert(dbObject);

		return 1;
	}
	
	
	public boolean existImage(byte[] image) {
		DBCursor cursor = transactionMongoManager.readDataDbCursor(BARCODE_BITMAP, image);
		while (cursor.hasNext()) {
			cursor.next();
			return true;
		}
		return false;
	}

}
