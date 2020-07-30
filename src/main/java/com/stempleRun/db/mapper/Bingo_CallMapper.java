package com.stempleRun.db.mapper;

import java.util.ArrayList;

import com.stempleRun.db.dto.BingoVO;

public interface Bingo_CallMapper {
	//게시글 목록
	public ArrayList<BingoVO> getbingoList();
}
