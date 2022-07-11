package com.example.accessingdatapostgresql.services;

import com.example.accessingdatapostgresql.entities.Role;
import com.example.accessingdatapostgresql.entities.User;
import com.example.accessingdatapostgresql.repositories.ShopRepository;
import com.example.accessingdatapostgresql.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ShopRepository shopRepository;

    public boolean createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null)
            return false;
        user.getRoles().add(shopRepository.getAllRoles().stream().filter(p -> p.getName().equals("USER")).findAny().orElse(null));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
