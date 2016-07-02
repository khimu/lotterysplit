package com.share.lottery.mongo.service;


public interface IImageService {
	public int saveImage(Long userId, byte[] bytes);
	
	public boolean existImage(byte[] image);
}
