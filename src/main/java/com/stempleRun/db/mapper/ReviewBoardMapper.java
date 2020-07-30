package com.stempleRun.db.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.stempleRun.db.dto.Member;
import com.stempleRun.db.dto.ReviewBoardVO;

public interface ReviewBoardMapper {
	
	//게시글 목록
	public ArrayList<ReviewBoardVO> getList() throws Exception;
	
	//게시글 상세
	public ReviewBoardVO reviewboardDetail(int bno) throws Exception;
	
	//게시글 작성
	public int reviewboardInsert(ReviewBoardVO reviewboard) throws Exception;
	
	//게시글 수정
	public int reviewboardUpdate(ReviewBoardVO reviewboard) throws Exception;
	
	//게시글 삭제
	public int reviewboardDelete(int bno) throws Exception;
	
	//게시글 조회수
	public int reviewboardCount(int bno) throws Exception;
	
	// 일반 추천수 증가
	public int getRecommend(int bno) throws Exception;
	
	// 관리자 추천수 증가
	public int admingetRecommend(int bno) throws Exception;
	
	// 작성자찾기
	public int testWriter(int bno) throws Exception;
}
