package com.example.spring.mapper;

import com.example.spring.database.entity.Company;
import com.example.spring.database.entity.Users;
import com.example.spring.database.repository.CompanyRepository;
import com.example.spring.dto.UserCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, Users>{

    private final CompanyRepository companyRepository;

    @Override
    public Users map(UserCreateEditDto fromObject, Users toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Users map(UserCreateEditDto object) {
        Users user = new Users();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateEditDto object, Users user) {
        user.setUsername(object.getUsername());
        user.setBirthDate(object.getBirthDate());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));
    }

    private Company getCompany(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
}
