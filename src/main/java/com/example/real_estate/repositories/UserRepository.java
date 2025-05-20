package com.example.real_estate.repositories;

/* imports */
import com.example.real_estate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<Object> findByEmail(String name);
}