package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.app_storyStart;
import com.stempleRun.db.mapper.app_storyStartMapper;

@Service
public class app_storyStartService {
	
	@Autowired
	app_storyStartMapper mapper;

	public ArrayList<app_storyStart> getNeedPHF(String s_num) throws Exception {
		
		return mapper.getNeedPHF(s_num);
	}
	
}
