package com.example.spring.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

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
