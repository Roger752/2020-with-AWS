package com.stempleRun.db.mapper;

import java.util.ArrayList;

import com.stempleRun.db.dto.Story;

public interface StoryMapper {

	public ArrayList<Story> getStorys() throws Exception;
	
	public void insert(Story s);
	
	public Story findNum(String s_title);

	public Story findSNum(int s_num);
	
	public int getS_num();
}
