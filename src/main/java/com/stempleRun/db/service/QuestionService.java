package com.stempleRun.db.service;

import com.stempleRun.db.dto.Question;
import com.stempleRun.db.mapper.QuestionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

	@Autowired
	QuestionMapper questionMapper;
	
	public void register(String q_content) {
		questionMapper.insert(q_content);
	}
	
	public Question findNum(String q_content) {
		return questionMapper.findNum(q_content);
	}

	public Question getQuestion(int q_num) {
		return questionMapper.getQuestion(q_num);
	}
	
	public int getQ_num() {
		return questionMapper.getQ_num();
	}
	
	public void change(int q_num, String q_content) {
		questionMapper.change(q_num, q_content);
	}
	
	public int remove(int q_num) {
		return questionMapper.remove(q_num);
	}
}
