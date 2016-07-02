package com.share.lottery.model;

import java.io.Serializable;

public class IdBag implements Serializable {

	private Long id;
	
	public IdBag(Long id){
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
