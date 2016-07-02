package com.share.lottery.mongo.service;

import java.util.List;

import com.share.lottery.model.CampaignDTO;

public interface ICampaignService {
	
	public List<CampaignDTO> getCampaigns(Long userId);
	
	public void updateCampaign(String campaignName, String email, String fbId);
	
	public void saveCampaign(Long userId, String campaignName, String brand, Long lotteryTicketId);

}
