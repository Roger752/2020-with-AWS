package com.stempleRun.db.mapper;

import java.util.ArrayList;

import com.stempleRun.db.dto.App_bingo;

public interface app_bingoMapper {
	//빙고 리스트에서 빙고 선택 시 문화재 리스트
	public ArrayList<App_bingo> bingogetCultures(int b_num);
	
	public int change(int b_num, int c_num, int b_order);
}
