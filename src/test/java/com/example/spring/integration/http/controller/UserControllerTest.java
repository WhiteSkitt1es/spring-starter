package com.example.spring.integration.http.controller;

import com.example.spring.database.entity.Role;
import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.example.spring.dto.UserCreateEditDto.Fields.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor

class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @BeforeEach
    void init() {
//        List<GrantedAuthority> roles = Arrays.asList(Role.USER, Role.ADMIN);
//        User testUser = new User("test@gmail.com", "testPassword", roles);
//        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(authenticationToken);
//        SecurityContextHolder.setContext(securityContext);
    }

    @Test
//    @WithMockUser
    void findAll() throws Exception {
        mockMvc.perform(get("/users")
                        .with(user("test@gmail.com").password("testPassword").authorities(Role.USER)))

                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                .param(username, "test@gmail.com")
                .param(firstname, "Test")
                .param(lastname, "Test")
                .param(role, "ADMIN")
                .param(companyId, "1")
                .param(birthDate, "2000-01-01")
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }
}