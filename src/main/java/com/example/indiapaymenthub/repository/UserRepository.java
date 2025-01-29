package com.example.indiapaymenthub.repository;

import com.example.indiapaymenthub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserType(String userType);

    long countByUserType(String userType);

    @Query(value = "SELECT * FROM User u WHERE u.email = :email", nativeQuery = true)
    User findByEmailNative(@Param("email") String email);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
