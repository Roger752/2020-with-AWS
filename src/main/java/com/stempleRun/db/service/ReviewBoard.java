package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.ReviewBoardVO;
import com.stempleRun.db.mapper.ReviewBoardMapper;

@Service
public class ReviewBoard {
	@Autowired
	ReviewBoardMapper reviewboardMapper;
	
	public ArrayList<ReviewBoardVO> getList() throws Exception{
		return reviewboardMapper.getList();
	}
	
	public ReviewBoardVO reviewboardDetail(int bno) throws Exception{
		return reviewboardMapper.reviewboardDetail(bno);
	}
	
	public int reviewboardInsert(ReviewBoardVO reviewboard) throws Exception{
		return reviewboardMapper.reviewboardInsert(reviewboard);
	}
	
	public int reviewboardUpdate(ReviewBoardVO reviewboard) throws Exception{
		return reviewboardMapper.reviewboardUpdate(reviewboard);
	}
	
	public int reviewboardDelete(int bno) throws Exception{
		return reviewboardMapper.reviewboardDelete(bno);
	}
	
	public int reviewboardCount(int bno) throws Exception{
		return reviewboardMapper.reviewboardCount(bno);
	}
	
	// 일반 추천수 증가
	public int getRecommend(int bno) throws Exception {
		return reviewboardMapper.getRecommend(bno);
	}
	
	// 관리자 추천수 증가
	public int admingetRecommend(int bno) throws Exception {
		return reviewboardMapper.admingetRecommend(bno);
	}
	
	// 아이디찾기
	public int testWriter(int bno) throws Exception {
		return reviewboardMapper.testWriter(bno);
	}
	
}
