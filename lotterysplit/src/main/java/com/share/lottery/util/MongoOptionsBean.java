package com.share.lottery.util;

import javax.net.SocketFactory;

import com.mongodb.DBDecoderFactory;
import com.mongodb.MongoOptions;


/**
 * Bean Object for MongoOptions.
 *
 * @author jawad.hameed
 */
public class MongoOptionsBean extends MongoOptions {

	/**
	 * Sets the auto connect retry.
	 *
	 * @param autoConnectRetry the new auto connect retry
	 */
	public void setAutoConnectRetry(boolean autoConnectRetry) {
		this.autoConnectRetry = autoConnectRetry;
	}
	
	/**
	 * Sets the connections per host.
	 *
	 * @param connectionsPerHost the new connections per host
	 */
	public void setConnectionsPerHost(int connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}
	
	/**
	 * Sets the connect timeout.
	 *
	 * @param connectTimeout the new connect timeout
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	/**
	 * Sets the db decoder factory.
	 *
	 * @param dbDecoderFactory the new db decoder factory
	 */
	public void setDbDecoderFactory(DBDecoderFactory dbDecoderFactory) {
		this.dbDecoderFactory = dbDecoderFactory;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Sets the fsync.
	 *
	 * @param fsync the new fsync
	 */
	public void setFsync(boolean fsync) {
		this.fsync = fsync;
	}
	
	/**
	 * Sets the j.
	 *
	 * @param j the new j
	 */
	public void setJ(boolean j) {
		this.j = j;
	}
	
	/**
	 * Sets the max auto connect retry time.
	 *
	 * @param maxAutoConnectRetryTime the new max auto connect retry time
	 */
	public void setMaxAutoConnectRetryTime(long maxAutoConnectRetryTime) {
		this.maxAutoConnectRetryTime = maxAutoConnectRetryTime;
	}
	
	/**
	 * Sets the max wait time.
	 *
	 * @param maxWaitTime the new max wait time
	 */
	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
	
	/**
	 * Sets the safe.
	 *
	 * @param safe the new safe
	 */
	public void setSafe(boolean safe) {
		this.safe = safe;
	}
	
	/**
	 * Sets the slave ok.
	 *
	 * @param slaveOk the new slave ok
	 */
	public void setSlaveOk(boolean slaveOk) {
		this.slaveOk = slaveOk;
	}
	
	/**
	 * Sets the socket factory.
	 *
	 * @param socketFactory the new socket factory
	 */
	public void setSocketFactory(SocketFactory socketFactory) {
		this.socketFactory = socketFactory;
	}
	
	/**
	 * Sets the socket keep alive.
	 *
	 * @param socketKeepAlive the new socket keep alive
	 */
	public void setSocketKeepAlive(boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}
	
	/**
	 * Sets the socket timeout.
	 *
	 * @param socketTimeout the new socket timeout
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
	/**
	 * Sets the threads allowed to block for connection multiplier.
	 *
	 * @param threadsAllowedToBlockForConnectionMultiplier the new threads allowed to block for connection multiplier
	 */
	public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}
	
	/**
	 * Sets the w.
	 *
	 * @param w the new w
	 */
	public void setW(int w) {
		this.w = w;
	}
	
	/**
	 * Sets the wtimeout.
	 *
	 * @param wtimeout the new wtimeout
	 */
	public void setWtimeout(int wtimeout) {
		this.wtimeout = wtimeout;
	}
}

