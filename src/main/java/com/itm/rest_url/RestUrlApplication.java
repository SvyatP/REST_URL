package com.itm.rest_url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestUrlApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(RestUrlApplication.class, args);

        Communication communication = context.getBean("communication", Communication.class);
        System.out.println("Answer: " + communication.getAnswer());
    }

}
