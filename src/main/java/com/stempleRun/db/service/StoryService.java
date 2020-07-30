package com.stempleRun.db.service;

import java.util.ArrayList;

import com.stempleRun.db.dto.Story;
import com.stempleRun.db.mapper.StoryMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryService {

	@Autowired
	StoryMapper storyMapper;
	
	public ArrayList<Story> getStorys() throws Exception {
		return storyMapper.getStorys();
	}
	
	public void register(Story s) {
		storyMapper.insert(s);
	}
	
	public Story findNum(String s_title) {
		
		return storyMapper.findNum(s_title);
	}

	public Story findSNum(int s_num) {
		return storyMapper.findSNum(s_num);
	}
	
	public int getS_num() {
		return storyMapper.getS_num();
	}
}
