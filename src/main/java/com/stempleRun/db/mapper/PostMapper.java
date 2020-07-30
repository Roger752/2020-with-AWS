package com.stempleRun.db.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.stempleRun.db.dto.PictureVO;
import com.stempleRun.db.dto.PostVO;
import com.stempleRun.db.dto.RecommendVO;

public interface PostMapper {

	// 게시글 목록
	public ArrayList<PostVO> getList() throws Exception;

	// 게시글 인기순
	public ArrayList<PostVO> getPopular() throws Exception;

	// 게시글 최신순
	public ArrayList<PostVO> getLately() throws Exception;

	// 게시글 등록
	public int postInsert(PostVO post) throws Exception;

	// 사진 등록
	public int pictureInsert(PictureVO picture) throws Exception;

	// 게시글 상세
	public PostVO postDetail(int p_num) throws Exception;

	public String pictureDetail(@Param("p_num") int p_num) throws Exception;

	public PictureVO locationDetail(@Param("p_num") int p_num) throws Exception;

	public String postWriter(@Param("p_num") int p_num) throws Exception;

	public String getPictag(int p_num) throws Exception;

	// 게시글 삭제
	public int postDelete(@Param("p_num") int p_num) throws Exception;

	// 추천수 증가
	public int getRecommend(int p_num) throws Exception;

	// 게시글 수정
	public int postUpdate(PostVO post) throws Exception;

	public int locationUpdate(PictureVO picture) throws Exception;

	public int getPicnum(int p_num) throws Exception;

	public int pictagUpdate(PictureVO picture) throws Exception;

	public String getLatitude(int p_num) throws Exception;

	public String getLongitude(int p_num) throws Exception;

	// 핫스팟 좌표조회
	public List<PostVO> getDailyPost();

	public List<PostVO> getWeeklyPost();

	public List<PostVO> getMonthlyPost();

	// 추천수
	public int getReco(RecommendVO reco) throws Exception; // 추천하기

	public RecommendVO compareReco(RecommendVO reco) throws Exception; // 추천수 비교

	public int countReco(int p_num) throws Exception; // 추천수 조회
	// ↓ 안되는거

	public PictureVO getPicture(int pic_num) throws Exception;

	public ArrayList<PostVO> getInfo() throws Exception;
}
