package com.clueper.funapptracker.service;

import com.clueper.funapptracker.dto.LoginRequestDTO;
import com.clueper.funapptracker.dto.RegisterRequestDTO;
import com.clueper.funapptracker.model.User;
import com.clueper.funapptracker.repository.UserRepository;
import com.clueper.funapptracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private final UserDetailsServiceImpl userService;



    /**
     * Registers a new user.
     * @param registerRequest The DTO containing registration details.
     */
//    public void registerUser(RegisterRequestDTO registerRequest) {
//        // Check if username or email already exists
//        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
//            throw new RuntimeException("Error: Username is already taken!");
//        }
//
//        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
//            throw new RuntimeException("Error: Email is already in use!");
//        }
//
//        // Create new user's account
//        User user = new User();
//        user.setUsername(registerRequest.getUsername());
//        user.setEmail(registerRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//
//        userRepository.save(user);
//    }

    public void registerUser(RegisterRequestDTO registerRequest){

        // Check if username or email already exists
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new RuntimeException("User Already exists");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        //Saving the new user to repository
        userRepository.save(User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build());
    }


//    public String loginUser(LoginRequestDTO loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return jwtUtil.generateToken(loginRequest.getUsername());
//    }

    public String loginUser(LoginRequestDTO request){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),request.getPassword()
        ));
        UserDetails user=userService.loadUserByUsername(request.getUsername());
        String token=jwtUtil.generateToken(user);
        return token;
    }
}
