package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.App_bingo;
import com.stempleRun.db.mapper.app_bingoMapper;

@Service
public class App_bingoService {
	//빙고 리스트 출력
	@Autowired
	app_bingoMapper app_bingoMapper;
	
	public ArrayList<App_bingo> bingogetCultures(int b_num){
		return app_bingoMapper.bingogetCultures(b_num);
	}
	
	public int change(int b_num, int c_num, int b_order) {
		return app_bingoMapper.change(b_num, c_num, b_order);
	}
}
