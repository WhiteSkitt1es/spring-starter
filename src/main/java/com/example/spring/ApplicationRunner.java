package com.example.spring;

import com.example.spring.bpp.config.ApplicationConfiguration;
import com.example.spring.database.pool.ConnectionPool;
import com.example.spring.database.repository.CompanyRepository;
import com.example.spring.database.repository.CrudRepository;
import com.example.spring.service.CompanyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        try (/*var context = new ClassPathXmlApplicationContext("application.xml")*/
            var context = new AnnotationConfigApplicationContext()) {
            context.register(ApplicationConfiguration.class);
            context.getEnvironment().setActiveProfiles("web", "prod");
            context.refresh();
            ConnectionPool connectionPool = context.getBean("pool", ConnectionPool.class);
            System.out.println(connectionPool);

            CompanyService companyService = context.getBean(CompanyService.class);
            System.out.println(companyService.findById(1));
        }
    }
}
