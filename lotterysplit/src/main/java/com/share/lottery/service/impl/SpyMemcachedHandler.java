package com.share.lottery.service.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import net.spy.memcached.CASMutation;
import net.spy.memcached.CASMutator;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;
import net.spy.memcached.transcoders.Transcoder;

import org.springframework.stereotype.Component;

import com.share.lottery.service.ISpyMemcachedHandler;

//@Component
public class SpyMemcachedHandler implements ISpyMemcachedHandler {

	private MemcachedClient client;
	
	@PostConstruct
	public void init(){
		try {
			client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object getObject(String key){
		// Try to get a value, for up to 5 seconds, and cancel if it doesn't return
		Future<Object> f = client.asyncGet(key);
		try {
			Object myObj= f.get(5, TimeUnit.SECONDS);
		    return myObj;
		} catch(TimeoutException e) {
		    // Since we don't need this, go ahead and cancel the operation.  This
		    // is not strictly necessary, but it'll save some work on the server.
		    f.cancel(false);
		    // Do other timeout related stuff
		} catch (InterruptedException e) {
			f.cancel(false);
			e.printStackTrace();
		} catch (ExecutionException e) {
			f.cancel(false);
			e.printStackTrace();
		}
		return null;
	}
	
	public void cacheObject(String key, Object obj){
		client.set(key, 3600, obj);
	}
	
	public void deleteCache(String key){
		client.delete(key);
	}
	
	public List<Object> addAnItem(final String key, final Object newItem) {

	    // This is how we modify a list when we find one in the cache.
	    CASMutation<List<Object>> mutation = new CASMutation<List<Object>>() {

	        // This is only invoked when a value actually exists.
	        public List<Object> getNewValue(List<Object> current) {
	            // Not strictly necessary if you specify the storage as
	            // LinkedList (our initial value isn't), but I like to keep
	            // things functional anyway, so I'm going to copy this list
	            // first.
	            LinkedList<Object> ll = new LinkedList<Object>(current);

	            // If the list is already "full", pop one off the end.
	            if(ll.size() > 500) {
	                ll.removeLast();
	            }
	            // Add mine first.
	            ll.addFirst(newItem);

	            return ll;
	        }
	    };

	    // The initial value -- only used when there's no list stored under
	    // the key.
	    List<Object> initialValue=Collections.singletonList(newItem);
	    
	    Transcoder tc = new SerializingTranscoder();

	    // The mutator who'll do all the low-level stuff.
	    CASMutator<List<Object>> mutator = new CASMutator<List<Object>>(client, tc);

	    // This returns whatever value was successfully stored within the
	    // cache -- either the initial list as above, or a mutated existing
	    // one
	    try {
			return mutator.cas(key, initialValue, 0, mutation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
	
	/*
	public List<Object> removeAnItem(final String key, final Object newItem) {

	    // This is how we modify a list when we find one in the cache.
	    CASMutation<List<Object>> mutation = new CASMutation<List<Object>>() {

	        // This is only invoked when a value actually exists.
	        public List<Object> getNewValue(List<Object> current) {
	            // Not strictly necessary if you specify the storage as
	            // LinkedList (our initial value isn't), but I like to keep
	            // things functional anyway, so I'm going to copy this list
	            // first.
	            LinkedList<Object> ll = new LinkedList<Object>(current);

	            // If the list is already "full", pop one off the end.
	            if(ll.size() > 500) {
	                ll.removeLast();
	            }
	            // Add mine first.
	            ll.addFirst(newItem);

	            return ll;
	        }
	    };

	    // The initial value -- only used when there's no list stored under
	    // the key.
	    List<Object> initialValue=Collections.singletonList(newItem);
	    
	    Transcoder tc = new SerializingTranscoder();

	    // The mutator who'll do all the low-level stuff.
	    CASMutator<List<Object>> mutator = new CASMutator<List<Object>>(client, tc);

	    // This returns whatever value was successfully stored within the
	    // cache -- either the initial list as above, or a mutated existing
	    // one
	    try {
			return mutator.cas(key, initialValue, 0, mutation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
	*/

}
