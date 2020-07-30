package com.stempleRun.db.service;

import java.util.ArrayList;

import com.stempleRun.db.dto.Story_change;
import com.stempleRun.db.dto.Story_only;
import com.stempleRun.db.mapper.Story_onlyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Story_onlyService {

	@Autowired
	Story_onlyMapper mapper;
	
	public void register(Story_only so) {
		mapper.insert(so);
	}
	
	public ArrayList<Story_only> getStoryOnly() {
		return mapper.getStoryOnly();
	}

	public Story_only findStoryOnlyBySNum(int s_num) {
		return mapper.findStoryOnlyBySNum(s_num);
	}
	
	public ArrayList<Story_change> story_change(int s_num) {
		return mapper.story_change(s_num);
	}
	
	public void change(int s_num, int c_num, int s_order, String s_file) {
		mapper.change(s_num, c_num, s_order, s_file);
	}
	
	public int remove(int s_num, int s_order) {
		return mapper.remove(s_num, s_order);
	}
}
