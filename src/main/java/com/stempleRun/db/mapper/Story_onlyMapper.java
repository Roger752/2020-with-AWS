package com.stempleRun.db.mapper;

import java.util.ArrayList;

import com.stempleRun.db.dto.Story_change;
import com.stempleRun.db.dto.Story_only;

public interface Story_onlyMapper {
	
	public void insert(Story_only so);

	public ArrayList<Story_only> getStoryOnly();

	public Story_only findStoryOnlyBySNum(int s_num);
	
	public ArrayList<Story_change> story_change(int s_num);
	
	public void change(int s_num, int c_num, int s_order, String s_file);
	
	public int remove(int s_num, int s_order);
}
