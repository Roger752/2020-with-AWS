package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.Amenity;
import com.stempleRun.db.dto.Culture;
import com.stempleRun.db.mapper.AmenityMapper;
import com.stempleRun.db.mapper.CultureMapper;

@Service
public class AmenityService {

	@Autowired
	AmenityMapper amenityMapper;
	
	public ArrayList<Amenity> getAmenitys() throws Exception {
		return amenityMapper.getAmenitys();
	}
	
	public void register(Amenity a) {
		amenityMapper.amenityinsert(a);
	}
	
	public Amenity getAmenity(String a_num) {
		return amenityMapper.getAmenity(a_num);
	}
	
	public Amenity findNum(String a_latitude,String a_longitude) {
		return amenityMapper.findNum(a_latitude,a_longitude);
	}
}
