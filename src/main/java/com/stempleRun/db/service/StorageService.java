package com.stempleRun.db.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
// 이게 맞나
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.stempleRun.config.StorageConfig;
import com.stempleRun.exception.StorageException;
import com.stempleRun.exception.StorageFileNotFoundException;

@Service
public class StorageService {

	private final Path rootLocation;

	@Autowired
	public StorageService(StorageConfig properties) {
		// Paths.get => 경로에 관한 정보 가져오기
		this.rootLocation = Paths.get(properties.getLocation());
	}

	// 폴더 만드는 함수
	private String checkandCreateFolder() {
		return "20200721";
	}
	
	// 저장
	public void store(MultipartFile file) {
		String folderName = checkandCreateFolder();
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("빈 파일: " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException("파일 이름 오류" + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(folderName).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			} // .resolve() => 고정된 루트 경로에 부분 경로를 추가하는 방법
		} catch (IOException e) {
			throw new StorageException("저장 실패 " + filename, e);
		}
	}

	// 저장된 목록 다 띄우기
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	// 다운로드
	public Resource loadAsResource(String filename) {
		try {    
			Path file = load(filename);    
			Resource resource = new UrlResource(file.toUri());
			System.out.println("저장할때 file.toUri = " + file.toUri());
			System.out.println("Resource = " + resource);
			if (resource.exists() || resource.isReadable()) {     
				return resource;    
			}    
			else {     
				throw new StorageFileNotFoundException("Could not read file: " + filename);
			}   
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		  }
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	// 초기화
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
