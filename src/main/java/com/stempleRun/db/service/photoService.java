package com.stempleRun.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stempleRun.db.dto.Picture;
import com.stempleRun.db.repository.PictrueRepository;

@Component
public class photoService {

	@Autowired
	PictrueRepository photoRepository;

	public List<Picture> photolist() {

		List<Picture> photoList = photoRepository.findAll();
		return photoList;
	}

}
