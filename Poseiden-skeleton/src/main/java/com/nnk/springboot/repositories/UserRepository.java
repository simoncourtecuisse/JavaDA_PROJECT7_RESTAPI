package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.AuthenticationProvider;
import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
   Optional<User> findByUsername(String username);
   Boolean existsByUsername(String username);
   @Modifying
   @Query("UPDATE User u SET u.authProvider = ?2 WHERE u.username = ?1")
   public void updateAuthenticationProvider(String username, AuthenticationProvider authProvider);
}
