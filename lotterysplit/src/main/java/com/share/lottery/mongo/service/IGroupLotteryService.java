package com.share.lottery.mongo.service;

import java.util.List;

import com.share.lottery.model.GroupDTO;

public interface IGroupLotteryService {
	
	public List<GroupDTO> getGroups(Long userId);
	
	public void saveOrUpdateGroupTicket(String groupName, Long ticketId);
	
	public void saveOrUpdateGroupMember(String groupName, Long userId);
	public void saveOrUpdateGroupMember(String groupName, String email);

	public void saveOrUpdateGroupMembers(String groupName, List<Long> userIds);
	
	public void saveOrUpdateGroupMembers(String groupName, String[] emails);
	
	public void saveOrUpdateGroupMembers(String groupName, String[] emails, List<Long> userIds);
	
	public boolean userExistInGroup(Long userId, String groupName);
	
	public boolean userExistInGroup(String email, String groupName);
	
	public boolean groupNameAlreadyExist(String groupName);
	
	public GroupDTO getPublicGroup();

}
