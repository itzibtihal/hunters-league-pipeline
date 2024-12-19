package com.javangers.hunters_league.web.rest;

import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.security.CustomUserDetailsService;
import com.javangers.hunters_league.security.JwtTokenUtil;
import com.javangers.hunters_league.service.UserService;
import com.javangers.hunters_league.web.vm.LoginRequestVM;
import com.javangers.hunters_league.web.vm.LoginResponse;
import com.javangers.hunters_league.web.vm.RegisterRequestVM;
import com.javangers.hunters_league.web.vm.UserResponseVM;
import com.javangers.hunters_league.web.vm.mapper.LoginMapper;
import com.javangers.hunters_league.web.vm.mapper.RegisterMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {

    private final UserService loginService;
    private final LoginMapper loginMapper;
    private final RegisterMapper registerMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestVM loginRequest) {
        Authentication  authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetailsService.loadUserByEmail(loginRequest.getEmail()).getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateToken(authentication);

        return ResponseEntity.ok(new LoginResponse(jwt));

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestVM registerRequest) {
        User user = registerMapper.toEntity(registerRequest);
        User createdUser = loginService.register(user);
        UserResponseVM response = registerMapper.toResponseVM(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
