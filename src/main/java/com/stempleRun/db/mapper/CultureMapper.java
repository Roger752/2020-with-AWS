package com.stempleRun.db.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.stempleRun.db.dto.Bingo_onlyVO;
import com.stempleRun.db.dto.Culture;

public interface CultureMapper {

	public ArrayList<Culture> getCultures() throws Exception;

	public Culture getCulture(@Param("c_num") int c_num);
	
	public Culture findNum(String c_name);

	public void insert(Culture c);

}
