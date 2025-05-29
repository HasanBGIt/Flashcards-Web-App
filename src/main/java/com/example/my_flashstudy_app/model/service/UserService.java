package com.example.my_flashstudy_app.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.my_flashstudy_app.model.entity.User;
import com.example.my_flashstudy_app.model.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //-------------------------------------------------------------------
    public List<User> getAll() {
        return userRepo.findAll();
    }
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    public Optional<User> findById(Integer id) {
        return userRepo.findById(id);
    }

    //-------------------------------------------------------------------
    public void save(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEmail(user.getEmail());
            user.setUsername(user.getUsername());
        }
        userRepo.save(user);
    }



}
