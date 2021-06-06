package com.hotel.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

public class HotelApplication {

	private static final Logger log = LoggerFactory.getLogger(HotelApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

}
