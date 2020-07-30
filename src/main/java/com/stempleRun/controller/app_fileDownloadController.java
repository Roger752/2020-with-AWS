package com.stempleRun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stempleRun.db.service.StorageService;

@Controller
public class app_fileDownloadController {

	@Autowired
	StorageService ss;
	
	@RequestMapping(value = "/uploads/{fileName}")
	public ResponseEntity<Resource> asdf(@PathVariable String fileName) {
		System.out.println(fileName);
		Resource file = ss.loadAsResource(fileName);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
