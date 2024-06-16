package com.siteexample.attestation.repo;

import com.siteexample.attestation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long>{
    User findByEmail(String email);
}
