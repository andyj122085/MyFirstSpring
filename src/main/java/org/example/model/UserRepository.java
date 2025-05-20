package org.example.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 不需寫任何 SQL，Jpa 會自動提供增刪查改功能
    Optional<User> findById(Long id); // 官方預設就這樣
    List<User> findByName(String name);
    List<User> findByNameAndAge(String name,int age);


}
