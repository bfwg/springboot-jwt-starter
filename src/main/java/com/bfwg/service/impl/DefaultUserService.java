package com.bfwg.service.impl;

import com.bfwg.converters.DefaultUserDetailsConverter;
import com.bfwg.dto.DefaultUserDetails;
import com.bfwg.remote.UserEntity;
import com.bfwg.remote.repository.UserRepository;
import com.bfwg.service.IdGenerator;
import com.bfwg.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.parser.Entity;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.bfwg.converters.DefaultUserDetailsConverter.toEntity;


public class DefaultUserService implements UserService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    final IdGenerator idGenerator;

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;

    public DefaultUserService(IdGenerator idGenerator, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public DefaultUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

        return DefaultUserDetailsConverter.from(user);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();
        LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

        if (authenticationManager == null) {
            LOGGER.debug("No authentication manager set. can't change Password!");
            return;
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));

        LOGGER.debug("Changing password for user '" + username + "'");

        DefaultUserDetails user = loadUserByUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));

        update(user);

    }

    @Override
    public DefaultUserDetails findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return DefaultUserDetailsConverter.from(userEntity);
    }

    @Override
    public DefaultUserDetails findByUsername(String username) throws UsernameNotFoundException {
        UserEntity u = userRepository.findByUsername(username);

        if (u == null) {
            u = userRepository.findByEmail(username);
        }

        return DefaultUserDetailsConverter.from(u);
    }

    @Override
    public DefaultUserDetails findById(String id) throws AccessDeniedException {
        UserEntity u = userRepository.findOne(id);
        return DefaultUserDetailsConverter.from(u);
    }

    @Override
    public List<DefaultUserDetails> findAll() throws AccessDeniedException {
        List<UserEntity> result = userRepository.findAll();
        return DefaultUserDetailsConverter.from(result);
    }

    @Override
    public void create(DefaultUserDetails user) {
        UserEntity userEntity = toEntity(user);
        userEntity.setId(idGenerator.getNext());
        userEntity.setEnabled(true);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        userEntity.setPassword(encodedPassword);
        userRepository.save(userEntity);
    }

    @Override
    public void update(DefaultUserDetails user) {
        UserEntity userEntity = toEntity(user);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        userEntity.setPassword(encodedPassword);
        userRepository.save(userEntity);
    }

}
