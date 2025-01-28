package com.example.indiapaymenthub.repository;

import com.example.indiapaymenthub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

  
    // Find all users by userType
    List<User> findByUserType(String userType);

    // Count users by userType
    long countByUserType(String userType);

    // Find users by email using native SQL
    @Query(value = "SELECT * FROM User u WHERE u.email = :email", nativeQuery = true)
    User findByEmailNative(@Param("email") String email);

    // Check if a user exists by email
    boolean existsByEmail(String email);
}
