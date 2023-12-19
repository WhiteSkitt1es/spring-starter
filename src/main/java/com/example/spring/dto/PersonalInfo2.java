package com.example.spring.dto;

import org.springframework.beans.factory.annotation.Value;

public interface PersonalInfo2 {

    String getFirstname();

    String getLastname();

    String getBirthdate();

    @Value("#{target.firstname + ' ' + target.lastname}")
    String getFullName();
}
