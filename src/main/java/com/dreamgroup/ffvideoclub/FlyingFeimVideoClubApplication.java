package com.dreamgroup.ffvideoclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FlyingFeimVideoClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyingFeimVideoClubApplication.class, args);
	}

}
