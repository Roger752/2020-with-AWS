package com.stempleRun.db.mapper;

import com.stempleRun.db.dto.Question;


public interface QuestionMapper {

	public void insert(String q_content);
	
	public Question findNum(String q_content);

	public Question getQuestion(int q_num);
	
	public int getQ_num();
	
	public void change(int q_num, String q_content);
	
	public int remove(int q_num);
}
