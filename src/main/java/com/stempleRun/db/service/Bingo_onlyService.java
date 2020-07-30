package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.Bingo_onlyVO;
import com.stempleRun.db.dto.Culture;
import com.stempleRun.db.dto.ReviewBoardVO;
import com.stempleRun.db.mapper.Bingo_onlyMapper;

@Service
public class Bingo_onlyService {
	@Autowired
	Bingo_onlyMapper mapper;
	
	public void register(Bingo_onlyVO bo) {
		mapper.insert(bo);
	}
	
	public Bingo_onlyVO bingocalldetailselect(int b_num) throws Exception{
		return mapper.bingocalldetailselect(b_num);
	}
	
	
	//빙고 수정
	public int bingoUpdateSave(Bingo_onlyVO bingoonly) {
		return mapper.bingoUpdateSave(bingoonly);
	}
}
