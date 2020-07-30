package com.stempleRun;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.stempleRun.config.StorageConfig;
import com.stempleRun.db.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
public class StempleRunApplication {

	public static void main(String[] args) {
		SpringApplication.run(StempleRunApplication.class, args);
	}

	/*
	 * // 서버 다시키면 파일 다 삭제
	 * 
	 * @Bean CommandLineRunner init(StorageService storageService) { return (args)
	 * -> { storageService.deleteAll(); storageService.init(); }; }
	 */
}