package com.stempleRun.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stempleRun.db.dto.EntityMember;

@Repository
public interface MemberJpaRepository extends JpaRepository<EntityMember, String> {

	public EntityMember findByUserId(String userId);
	
}
