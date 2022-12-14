package com.aerotravel.flightticketbooking.repository;



import com.aerotravel.flightticketbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   User findByUsername(String username);

    boolean existsByUsername(String username);

    User findByusername(String username);
}
