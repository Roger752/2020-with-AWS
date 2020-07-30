package com.stempleRun.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.BingoVO;
import com.stempleRun.db.mapper.BingoMapper;

@Service
public class BingoService {
	@Autowired
	BingoMapper bingoMapper;
	
	public void register(BingoVO b) {
		bingoMapper.bingoinsert(b);
	}
	
	public BingoVO findBingoNum(String b_title) {
		
		return bingoMapper.findBingoNum(b_title);
	}
	
	public BingoVO findBingoTitle(int b_num) {
		
		return bingoMapper.findBingoTitle(b_num);
	}
	
	public BingoVO findBingodetails(BingoVO b) {
		return bingoMapper.findBingodetails(b);
	}
	
	
}
