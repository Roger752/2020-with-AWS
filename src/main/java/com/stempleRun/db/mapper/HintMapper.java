package com.stempleRun.db.mapper;

import com.stempleRun.db.dto.Hint;

public interface HintMapper {

	public void insert(Hint h);
	
	public void content_change(int q_num, String h_content);
	
	public void file_change(int q_num, String h_file);
	
	public int remove(int h_num);
}
