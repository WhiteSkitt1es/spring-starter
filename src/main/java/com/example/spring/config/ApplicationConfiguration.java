package com.example.spring.config;

import com.example.spring.database.pool.ConnectionPool;
import com.example.web.config.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

//@ImportResource("classpath:#application.xml")
@Import(WebConfiguration.class)
@Configuration(/*proxyBeanMethods = false*/)
//@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = "com.example.spring",
//        useDefaultFilters = false,
//        includeFilters = {
//            @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class),
//            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
//            @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
//        }
//)
public class ApplicationConfiguration {

        @Bean("pool")
        public ConnectionPool pool(@Value("${db.username}") String username, @Value("${db.pool.size}") Integer poolSize) {
                return new ConnectionPool(username, poolSize);
        }

        @Bean
        @Profile("prod|web")
        public ConnectionPool profilePool() {
                return new ConnectionPool("test-text", 10);
        }
}
