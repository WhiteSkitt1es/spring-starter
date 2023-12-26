package com.example.spring.database.repository;

import com.example.spring.database.entity.Role;
import com.example.spring.database.entity.Users;
import com.example.spring.dto.PersonalInfo;
import com.example.spring.dto.PersonalInfo2;
import jakarta.persistence.Entity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<Users, Long>,
        FilterUserRepository,
        RevisionRepository<Users, Long, Integer> {

    @Query("select u from Users u " +
        "where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<Users> findAllBy(String firstname, String lastname );

    @Query(value = "SELECT * FROM users u WHERE u.username = :username",
        nativeQuery = true)
    List<Users> findAllByUsername(String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Users u " +
        "set u.role = :role " +
        "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    Optional<Users> findTopByOrderByIdDesc();

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
//    @Lock(LockModeType.OPTIMISTIC) Java level, add in Entity field version++
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Users> findTop3ByBirthDateBefore(LocalDate localDate, Sort sort);

    // Collection, Stream
    // Streamable, Slice, Page
//    @EntityGraph("Users.company")
    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(value = "select u from Users u",
        countQuery = "select count(distinct u.firstname) from Users u")
    Page<Users> findAllBy(Pageable pageable);

//    <T> List<T> findAllByCompanyId(Integer companyId, Class<T> clazz);
    @Query(value = "SELECT firstname, lastname, birth_date birthDate " +
                   "FROM users " +
                   "WHERE company_id = :companyId",
            nativeQuery = true)
    List<PersonalInfo2> findAllByCompanyId(Integer companyId);

    Optional<Users> findByUsername(String username);
}
