package com.ecommerce.assignment.repository;

import com.ecommerce.assignment.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    public User findByUsernameAndIsDeleted(String username, boolean isDeleted);

    Optional<User> findByUsername(String username01);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users p SET is_deleted = :isDeleted WHERE p.id = :id", nativeQuery = true)
    void updateIsDeleted(@Param("isDeleted") boolean isDeleted, @Param("id") Long id);
}
