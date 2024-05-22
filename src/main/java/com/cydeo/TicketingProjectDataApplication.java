package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //this includes @Configuration
public class TicketingProjectDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectDataApplication.class, args);
    }


    //I am trying to add bean in the container through @Bean annotation
    //create a class annotated with @Configuration
    //write a method return the object that you are trying to add in the container
    //annotate this method with @Bean
    @Bean//it is not my class so we need to @Bean, so why you not create configuration class?bcz @SpringBootApplication includes @Configuration
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
