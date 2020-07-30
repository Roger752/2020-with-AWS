package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.Amenity;
import com.stempleRun.db.dto.Cul_amenity;
import com.stempleRun.db.mapper.AmenityMapper;
import com.stempleRun.db.mapper.Cul_AmenityMapper;


@Service
public class Cul_amenityService {
	@Autowired
	Cul_AmenityMapper cul_amenityMapper;
	
	public void register(Cul_amenity ca) {
		cul_amenityMapper.insert(ca);
	}
	
//	public Amenity findNum(String a_name) {
//		return amenityMapper.findNum(a_name);
//	}
}
