package com.stempleRun.db.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.PictureVO;
import com.stempleRun.db.dto.PostVO;
import com.stempleRun.db.dto.RecommendVO;
import com.stempleRun.db.mapper.PostMapper;

@Service
public class PostService {

	@Autowired
	PostMapper postMapper;

	// 게시글 목록
	public ArrayList<PostVO> getList() throws Exception {
		return postMapper.getList();
	}

	// 인기순
	public ArrayList<PostVO> getPopular() throws Exception {
		return postMapper.getPopular();
	}

	// 최신순
	public ArrayList<PostVO> getLately() throws Exception {
		return postMapper.getLately();
	}

	// 게시글 입력
	public int postInsert(PostVO post) throws Exception {
		return postMapper.postInsert(post);
	}

	public int pictureInsert(PictureVO picture) throws Exception {
		return postMapper.pictureInsert(picture);
	}

	// 게시글 상세
	public PostVO postDetail(int p_num) throws Exception {
		return postMapper.postDetail(p_num);
	}

	public String pictureDetail(int p_num) throws Exception {
		return postMapper.pictureDetail(p_num);
	}

	public PictureVO locationDetail(int p_num) throws Exception {
		return postMapper.locationDetail(p_num);
	}

	public String postWriter(int p_num) throws Exception {
		return postMapper.postWriter(p_num);
	}

	public String getPictag(int p_num) throws Exception {
		return postMapper.getPictag(p_num);
	}

	// 게시글 삭제
	public int postDelete(int p_num) throws Exception {
		return postMapper.postDelete(p_num);
	}

	// 추천수 증가
	public int getRecommend(int p_num) throws Exception {
		return postMapper.getRecommend(p_num);
	}

	// 게시글 수정
	public int postUpdate(PostVO post) throws Exception {
		return postMapper.postUpdate(post);
	}

	public int locationUpdate(PictureVO picture) throws Exception {
		return postMapper.locationUpdate(picture);
	}

	public int getPicnum(int p_num) throws Exception {
		return postMapper.getPicnum(p_num);
	}

	public String getLatitude(int p_num) throws Exception {
		return postMapper.getLatitude(p_num);
	}

	public String getLongitude(int p_num) throws Exception {
		return postMapper.getLongitude(p_num);
	}

	public int pictagUpdate(PictureVO picture) throws Exception {
		return postMapper.pictagUpdate(picture);
	}

	// 일간 좌표조회
	public List<PostVO> getDailyPost() {
		return postMapper.getDailyPost();
	}

	public List<PostVO> getWeeklyPost() {
		return postMapper.getWeeklyPost();
	}

	public List<PostVO> getMonthlyPost() {
		return postMapper.getMonthlyPost();
	}

	// 추천수
	public int getReco(RecommendVO reco) throws Exception {
		return postMapper.getReco(reco);
	}

	public RecommendVO compareReco(RecommendVO reco) throws Exception {
		return postMapper.compareReco(reco);
	}

	public int countReco(int p_num) throws Exception {
		return postMapper.countReco(p_num);
	}

	// 안되는거
	public PictureVO getPicture(int pic_num) throws Exception {
		return postMapper.getPicture(pic_num);
	}

	public ArrayList<PostVO> getInfo() throws Exception {
		return postMapper.getInfo();
	}

}
