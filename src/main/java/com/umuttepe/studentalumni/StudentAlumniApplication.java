package com.umuttepe.studentalumni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class StudentAlumniApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentAlumniApplication.class, args);
    }
}
