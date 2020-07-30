package com.stempleRun.db.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.stempleRun.db.dto.Amenity;
import com.stempleRun.db.dto.Cul_amenity;

public interface Cul_AmenityMapper {

	public ArrayList<Cul_amenity> getCul_amenitys() throws Exception;
	
	public void insert(Cul_amenity ca);
	
	public Amenity findNum(String a_name);
}
