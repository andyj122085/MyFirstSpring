package org.example.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // 不需寫任何 SQL，Jpa 會自動提供增刪查改功能

    List<User> findByName(String name);
    List<User> findByNameAndAge(String name,int age);


}
