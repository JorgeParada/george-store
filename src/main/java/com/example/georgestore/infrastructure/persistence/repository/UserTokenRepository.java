package com.example.georgestore.infrastructure.persistence.repository;

import com.example.georgestore.infrastructure.persistence.entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity, String> {
}
