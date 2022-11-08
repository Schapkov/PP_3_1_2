package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user join fetch user.roles where user.username = :username")
    User findByUsername(@Param("username") String username);
}






