package com.bucketdev.betappgroupsvc.repository;

import com.bucketdev.betappgroupsvc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
