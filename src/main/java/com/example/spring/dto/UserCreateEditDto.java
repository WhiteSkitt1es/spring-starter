package com.example.spring.dto;

import com.example.spring.validation.UserInfo;
import com.example.spring.validation.group.CreateAction;
import com.example.spring.validation.group.UpdateAction;
import jakarta.servlet.http.Part;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.File;
import java.time.LocalDate;
import com.example.spring.database.entity.Role;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@Value
@FieldNameConstants
@UserInfo(groups = UpdateAction.class)
public class UserCreateEditDto {
    @Email
    String username;

    @NotBlank(groups = CreateAction.class)
    String rawPassword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @Size(min = 3, max = 64)
    String firstname;

    String lastname;

    Role role;

    Integer companyId;

    MultipartFile image;
}
