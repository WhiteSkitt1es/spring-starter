package com.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);
        System.out.println(context.getBean("pool"));
    }
}

/**
 * try (/*var context = new ClassPathXmlApplicationContext("application.xml")
 *var context=new AnnotationConfigApplicationContext()){
         *context.register(ApplicationConfiguration.class);
        *context.getEnvironment().setActiveProfiles("web","prod");
        *context.refresh();
        *ConnectionPool connectionPool=context.getBean("pool",ConnectionPool.class);
        *System.out.println(connectionPool);
        *
        *CompanyService companyService=context.getBean(CompanyService.class);
        *System.out.println(companyService.findById(1));
        *}
 */
