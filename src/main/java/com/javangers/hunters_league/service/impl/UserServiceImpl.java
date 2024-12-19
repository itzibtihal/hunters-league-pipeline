package com.javangers.hunters_league.service.impl;

import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.domain.enumeration.Role;
import com.javangers.hunters_league.repository.UserRepository;
import com.javangers.hunters_league.service.UserService;
import com.javangers.hunters_league.web.errors.AuthenticationException;
import com.javangers.hunters_league.web.errors.InvalidLicenseException;
import com.javangers.hunters_league.web.errors.UserAlreadyExistsException;
import com.javangers.hunters_league.web.errors.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public User login(User logingUser) {
        User user = findUserByEmail(logingUser.getEmail());
        if (!passwordEncoder.matches(logingUser.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid username or password");
        }

        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .cin(user.getCin())
                .email(user.getEmail())
                .nationality(user.getNationality())
                .licenseExpirationDate(user.getLicenseExpirationDate())
                .role(user.getRole())
                .build();
    }

    @Override
    public User register(User newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        if (newUser.getLicenseExpirationDate() != null &&
            newUser.getLicenseExpirationDate().isBefore(LocalDateTime.now())) {
            throw new InvalidLicenseException("License expiration date must be in the future");
        }

        User user = User.builder()
                .username(newUser.getUsername())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .cin(newUser.getCin())
                .email(newUser.getEmail())
                .nationality(newUser.getNationality())
                .joinDate(LocalDateTime.now())
                .licenseExpirationDate(newUser.getLicenseExpirationDate())
                .role(Role.MEMBER)
                .build();

        return userRepository.save(user);
    }

}