package com.example.leaderIt_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude= HibernateJpaAutoConfiguration.class)
public class LeaderItProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderItProjectApplication.class, args);
	}

}
