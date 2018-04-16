package com.bfwg.config;

import com.bfwg.remote.repository.UserRepository;
import com.bfwg.service.AuthenticationService;
import com.bfwg.service.DefaultIdGenerator;
import com.bfwg.service.IdGenerator;
import com.bfwg.service.UserService;
import com.bfwg.service.impl.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenHelper tokenHelper;

    @Bean
    public IdGenerator idGenerator(){
        return new DefaultIdGenerator();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new DefaultUserService(idGenerator(), userRepository,passwordEncoder(),authenticationManager);
    }

    @Bean
    public AuthenticationService authenticationService(){
        return new AuthenticationService(userService(),tokenHelper,authenticationManager);
    }
}
