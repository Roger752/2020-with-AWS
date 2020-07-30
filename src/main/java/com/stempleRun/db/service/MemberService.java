package com.stempleRun.db.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.EntityMember;
import com.stempleRun.db.dto.Member;
import com.stempleRun.db.mapper.MemberMapper;

@Service
public class MemberService {

	@Autowired
	MemberMapper memberMapper;

	public ArrayList<EntityMember> getMembers() throws Exception {
		return memberMapper.getMembers();
	}
	
	public EntityMember getMember(String m_id) {
		return memberMapper.getMember(m_id);
	}
	
	public int getM_num(@Param("id") String id) throws Exception {
		return memberMapper.getM_num(id);
	}
	
	public String getM_name(@Param("id") int id) throws Exception {
		return memberMapper.getM_name(id);
	}
	
	public Member app_getMember(String m_id) {
		return memberMapper.app_getMember(m_id);
	}

}
