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
@Component("transactionService")
public class TransactionService implements ITransactionService {
	
	public final static String ID = "_id";
	
	public final static String USER_ID = "userId";
	public final static String CREATED_ON = "createdOn";
	public final static String AMOUNT = "amount";
	public final static String TYPE = "type";

	private final Log log = LogFactory.getLog(getClass());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	//@Autowired
	//@Qualifier("transactionMongoManager")
	private MongoManager transactionMongoManager;

	public int creditTransaction(Long userId, Double amount) {

		if (userId == null || userId == 0) {
			log.info("Error blocking users, device_id should not be null");
			return -1;
		}

		// insert
		DBObject dbObject = new BasicDBObject();
		dbObject.put(USER_ID, userId);
		dbObject.put(CREATED_ON, new Date());
		dbObject.put(TYPE, TransactionBO.CREDIT);
		dbObject.put(AMOUNT, amount);
		
		transactionMongoManager.insert(dbObject);

		return 1;
	}

	// TODO turn to DTO list
	public List<TransactionBO> getAll(Long userId) {

		List<TransactionBO> transactionList = new ArrayList<TransactionBO>();
		DBCursor cursor = transactionMongoManager.readDataDbCursor(USER_ID, userId);
		while (cursor.hasNext()) {
			cursor.next();

			Date createdOn = null;
			if (cursor.curr().get(CREATED_ON) != null) {
				createdOn = (Date) cursor.curr().get(CREATED_ON);
			}

			TransactionBO transactions = new TransactionBO(((ObjectId) cursor
					.curr().get(ID)).toString(), userId, (Double) cursor.curr()
					.get(AMOUNT), (Integer) cursor.curr().get(TYPE), createdOn);

			transactionList.add(transactions);
		}
		return transactionList;
	}

	/**
	 * Json to object list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param json
	 *            the json
	 * @param clazz
	 *            the clazz
	 * @return the list
	 */
	public <T> List<T> jsonToObjectList(String json, Class<T> clazz) {
		List<T> list = null;
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory t = mapper.getTypeFactory();
		try {
			list = mapper.readValue(json,
					t.constructCollectionType(ArrayList.class, clazz));
		} catch (JsonParseException e) {
			log.error(e);
		} catch (JsonMappingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return list;
	}

}
