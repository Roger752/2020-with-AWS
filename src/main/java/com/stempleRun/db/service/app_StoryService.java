package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.app_story;
import com.stempleRun.db.mapper.app_StoryMapper;

@Service
public class app_StoryService {

	@Autowired
	app_StoryMapper mapper;
	
	public ArrayList<app_story> getStorys() throws Exception {
		return mapper.getStorys();
	}
	
}
