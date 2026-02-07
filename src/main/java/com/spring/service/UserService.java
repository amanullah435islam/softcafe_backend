package com.spring.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.model.User;
import com.spring.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    // For authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // No role, empty authorities
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                Collections.emptyList()  // no roles/authorities
        );
    }


    // helper to register new user (password already encoded by caller)
    public User save(User user) {
        return userRepository.save(user);
    }
    
    
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }
    
  
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
