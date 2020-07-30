package com.stempleRun.db.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.stempleRun.db.dto.EntityMember;
import com.stempleRun.db.dto.Member;

public interface MemberMapper {

	public ArrayList<EntityMember> getMembers() throws Exception;
	
	public EntityMember getMember(@Param("id") String id);
	
	public int getM_num(@Param("id") String id) throws Exception;
	
	public String getM_name(@Param("id") int id) throws Exception;
	
	public Member app_getMember(String m_id);
}
