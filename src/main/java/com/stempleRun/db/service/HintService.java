package com.stempleRun.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.Hint;
import com.stempleRun.db.mapper.HintMapper;

@Service
public class HintService {

	@Autowired
	HintMapper hintMapper;
	
	public void register(Hint h) {
		hintMapper.insert(h);
	}
	
	public void content_change(int q_num, String h_content) {
		hintMapper.content_change(q_num, h_content);
	}
	
	public void file_change(int q_num, String h_file) {
		hintMapper.file_change(q_num, h_file);
	}
	
	public int remove(int h_num) {
		return hintMapper.remove(h_num);
	}
}
