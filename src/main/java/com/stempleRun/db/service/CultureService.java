package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.Bingo_onlyVO;
import com.stempleRun.db.dto.Culture;
import com.stempleRun.db.mapper.CultureMapper;

@Service
public class CultureService {

	@Autowired
	CultureMapper cultureMapper;
	
	public ArrayList<Culture> getCultures() throws Exception {
		return cultureMapper.getCultures();
	}
	
	public Culture getCulture(int c_num) {
		return cultureMapper.getCulture(c_num);
	}
	public Culture findNum(String c_name) {
		return cultureMapper.findNum(c_name);
	}
	
	public void register(Culture c) {
		cultureMapper.insert(c);
	}
	
}
