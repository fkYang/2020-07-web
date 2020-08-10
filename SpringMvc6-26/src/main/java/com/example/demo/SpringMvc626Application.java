package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;
//@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class})
//@ImportResource(locations = {"classpath:spring-MVC.xml"})
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.demo.dao")
public class SpringMvc626Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvc626Application.class, args);
    }

}
