package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.BingoVO;
import com.stempleRun.db.mapper.Bingo_CallMapper;


@Service
public class Bingo_CallService {

	@Autowired
	Bingo_CallMapper bingo_CallMapper;
	
	public ArrayList<BingoVO> getbingoList(){
		return bingo_CallMapper.getbingoList();
	}
}
