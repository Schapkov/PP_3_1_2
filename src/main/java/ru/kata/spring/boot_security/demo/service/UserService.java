package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {

    public User findByUsername(String name);

    public User getUserById(Long aLong);

    public List<User> findAll();

    public void deleteById(Long aLong);

    public void saveUser(User user);

    public UserDetails loadUserByUsername(String username);








}