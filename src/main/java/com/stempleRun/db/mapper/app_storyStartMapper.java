package com.stempleRun.db.mapper;

import java.util.ArrayList;

import com.stempleRun.db.dto.app_storyStart;

public interface app_storyStartMapper {

	public ArrayList<app_storyStart> getNeedPHF(String s_num) throws Exception;	
}
