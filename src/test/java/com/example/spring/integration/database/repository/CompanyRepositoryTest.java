package com.example.spring.integration.database.repository;

import com.example.spring.database.entity.Company;
import com.example.spring.database.repository.CompanyRepository;
import com.example.spring.integration.annotation.IT;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
//@Transactional
//@Rollback
//@Commit
class CompanyRepositoryTest {

    private static final Integer APPLE_ID = 5;
    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    private final CompanyRepository companyRepository;

    @Test
    void checkFindByQueries() {
        companyRepository.findByName("google");
        companyRepository.findALLByNameContainingIgnoreCase("a");
    }

    @Test
    void delete() {
        Optional<Company> byId = companyRepository.findById(APPLE_ID);
        assertTrue(byId.isPresent());
        byId.ifPresent(companyRepository::delete);
        entityManager.flush();
        assertTrue(companyRepository.findById(APPLE_ID).isEmpty());
    }

    @Test
    void findById() {
        transactionTemplate.executeWithoutResult(tx -> {
            Company company = entityManager.find(Company.class, 1);
            assertNotNull(company);
            assertThat(company.getLocales()).hasSize(2);
        });
    }

    @Test
    void save() {
        Company company = Company.builder()
                .name("Yandex")
                .locales(Map.of(
                        "ru", "Yandex описание",
                        "en", "Yandex description"
                ))
                .build();
        entityManager.persist(company);
        assertEquals(11, company.getId());
    }
}