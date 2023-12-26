package com.example.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Validated
@ConfigurationProperties(prefix = "db")
public record DatabaseProperties(String username,
                                 String password,
                                 String driver,
                                 String url,
                                 PoolProperties pool,
                                 List<PoolProperties> pools) {
    public static record PoolProperties(Integer size,
                                        Integer timeout) {
    }
}
