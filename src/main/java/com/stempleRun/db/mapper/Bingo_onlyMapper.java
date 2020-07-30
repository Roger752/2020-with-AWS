package com.stempleRun.db.mapper;

import java.util.ArrayList;

import com.stempleRun.db.dto.Bingo_onlyVO;
import com.stempleRun.db.dto.Culture;
import com.stempleRun.db.dto.ReviewBoardVO;

public interface Bingo_onlyMapper {
	public void insert(Bingo_onlyVO bo);
	
	//빙고 리스트에서 빙고 선택 시 사용
	public Bingo_onlyVO bingocalldetailselect(int b_num) throws Exception;
	
	//빙고 수정
	public int bingoUpdateSave(Bingo_onlyVO bingoonly);
}
