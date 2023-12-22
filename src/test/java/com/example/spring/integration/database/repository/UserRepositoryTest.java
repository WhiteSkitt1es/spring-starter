package com.example.spring.integration.database.repository;

import com.example.spring.database.entity.Role;
import com.example.spring.database.entity.Users;
import com.example.spring.database.repository.UserRepository;
import com.example.spring.dto.PersonalInfo;
import com.example.spring.dto.PersonalInfo2;
import com.example.spring.dto.UserFilter;
import com.example.spring.integration.IntegrationTestBase;
import com.example.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkBatch() {
        List<Users> users = userRepository.findAll();
        userRepository.updateCompanyAndRole(users);
    }

    @Test
    void checkJdbcTemplate() {
        List<PersonalInfo> users = userRepository.findAllByCompanyIdAndRole(1, Role.USER);
        assertThat(users).hasSize(1);
    }

    @Test
    @Commit
    void checkAuditing() {
        Users ivan = userRepository.findById(1L).get();
        ivan.setBirthDate(ivan.getBirthDate().plusYears(1L));
        userRepository.flush();
        System.out.println();
    }

    @Test
    void checkCustomImplementation() {
        UserFilter filter = new UserFilter(
                null, "%ov%", LocalDate.now()
        );
        List<Users> users = userRepository.findAllByFilter(filter);
        System.out.println();
    }

    @Test
    void checkProjections() {
        List<PersonalInfo2> users = userRepository.findAllByCompanyId(1);
        assertThat(users).hasSize(2);
    }

    @Test
    void checkPageable() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<Users> slice = userRepository.findAllBy(pageable);
        slice.forEach(user -> System.out.println(user.getCompany().getName()));

        while(slice.hasNext()) {
            slice = userRepository.findAllBy(slice.nextPageable());
            slice.forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }

    @Test
    void checkTop3() {
//        Sort.TypedSort<Users> sortBy = Sort.sort(Users.class);
//        Sort sorting = sortBy.by(Users::getFirstname).and(sortBy.by(Users::getLastname));
        Sort sorting = Sort.by("firstname").and(Sort.by("lastname"));
        List<Users> allUsers = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sorting);
        assertThat(allUsers).hasSize(3);
    }

    @Test
    void checkFirstTop() {
        Optional<Users> topUser = userRepository.findTopByOrderByIdDesc();
        assertTrue(topUser.isPresent());
        topUser.ifPresent(users -> assertEquals(5L, users.getId()));
    }

    @Test
    void checkUpdate() {
        Users ivan = userRepository.getById(1L);
        assertSame(Role.ADMIN, ivan.getRole());

        int resultCount = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, resultCount);

        Users theSameIvan = userRepository.getById(1L);
        assertSame(Role.USER, theSameIvan.getRole());
    }

    @Test
    void checkQueries() {
        List<Users> users = userRepository.findAllBy("a", "ov");
        assertThat(users).hasSize(3);
        System.out.println(users);
    }
}