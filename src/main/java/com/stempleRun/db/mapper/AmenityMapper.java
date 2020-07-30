package com.stempleRun.db.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.stempleRun.db.dto.Amenity;

public interface AmenityMapper {

	public ArrayList<Amenity> getAmenitys() throws Exception;

	public void amenityinsert(Amenity a);

	public Amenity getAmenity(@Param("a_num") String a_num);

	public Amenity findNum(String a_latitude,String a_longitude);

}
