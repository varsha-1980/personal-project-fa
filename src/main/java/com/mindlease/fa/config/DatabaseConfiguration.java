package com.mindlease.fa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DatabaseConfiguration {

	@Profile("dev")
	@Bean
	public String devDatabaseConnection() {
		log.info("DB connection for DEV - H2");
		return "DB connection for DEV - H2";
	}

	@Profile("test")
	@Bean
	public String testDatabaseConnection() {
		log.info("DB Connection to RDS_TEST - Low Cost Instance");
		return "DB Connection to RDS_TEST - Low Cost Instance";
	}

	@Profile("prod")
	@Bean
	public String prodDatabaseConnection() {
		log.info("DB Connection to RDS_PROD - High Performance Instance");
		return "DB Connection to RDS_PROD - High Performance Instance";
	}
}
