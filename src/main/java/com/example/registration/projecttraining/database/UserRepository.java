package com.example.registration.projecttraining.database;

import org.springframework.stereotype.Repository;
import com.example.registration.projecttraining.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long >{}
