package com.stempleRun.db.mapper;

import java.util.ArrayList;
import java.util.Date;

import com.stempleRun.db.dto.Spot;
import com.stempleRun.db.dto.PostVO;
import com.stempleRun.db.dto.Spot;

public interface SpotMapper {


	public ArrayList<Spot> getPictures();

	public ArrayList<Spot> getWeekPictures();

	public ArrayList<Spot> getMonthPictures();

	public ArrayList<Spot> getDayPictures();

	public ArrayList<PostVO> getSearchList(String keyword);


}
