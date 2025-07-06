package com.clueper.funapptracker.repository;

import com.clueper.funapptracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Finds a user by their username. Used for the login process.
    Optional<User> findByUsername(String username);

    // Optional: Finds a user by email, useful for registration checks.
    Optional<User> findByEmail(String email);
}
