package com.stempleRun.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stempleRun.db.dto.Picture;
import com.stempleRun.db.dto.PostVO;

@Repository
public interface PictrueRepository extends JpaRepository<Picture, Integer>{
	
	@Query(value = "DELETE FROM post WHERE pic_num = ?1", nativeQuery = true)
	public PostVO deletepost(int pic_num);
	
	@Query(value = "SELECT* FROM picture WHERE pic_date >= ?1 AND pic_date < ?2", nativeQuery = true)
	public List<Picture> getDate(String startDate, String endDate); //첫번째 두번째 순서대로 들어감
}
