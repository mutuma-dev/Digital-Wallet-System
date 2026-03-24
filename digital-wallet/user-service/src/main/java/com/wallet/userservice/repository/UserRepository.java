package com.wallet.userservice.repository;

import com.wallet.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    //inherits JPA repository methods
}
