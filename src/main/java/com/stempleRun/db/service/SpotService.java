package com.stempleRun.db.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.Spot;
import com.stempleRun.db.dto.PostVO;
import com.stempleRun.db.dto.Spot;
import com.stempleRun.db.mapper.SpotMapper;

@Service
public class SpotService {
	
	@Autowired
	SpotMapper spotMapper;
	 
	public ArrayList<PostVO> getSearchList(String keyword)throws Exception {
		return spotMapper.getSearchList(keyword);
	}
	
	public ArrayList<Spot> getPictures(){
		return spotMapper.getPictures();
	}
	public ArrayList<Spot> getWeekPictures(){
		return spotMapper.getWeekPictures();
	}
	public ArrayList<Spot> getMonthPictures(){
		return spotMapper.getMonthPictures();
	}
	public ArrayList<Spot> getDayPictures(){
		return spotMapper.getDayPictures();
		
	}

}
