package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.Area;
import com.stempleRun.db.mapper.AreaMapper;

@Service
public class AreaService {

	@Autowired
	AreaMapper areaMapper;
	
	public ArrayList<Area> getAreas() throws Exception {
		return areaMapper.getAreas();
	}
	
}
