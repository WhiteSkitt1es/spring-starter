package com.example.spring.mapper;

import com.example.spring.database.entity.Users;
import com.example.spring.dto.CompanyReadDto;
import com.example.spring.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<Users, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;
    @Override
    public UserReadDto map(Users object) {
        CompanyReadDto company = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstname(),
                object.getLastname(),
                object.getImage(),
                object.getRole(),
                company
        );
    }
}
