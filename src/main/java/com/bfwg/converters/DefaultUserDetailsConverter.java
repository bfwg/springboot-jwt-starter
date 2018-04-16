package com.bfwg.converters;

import com.bfwg.dto.DefaultUserDetails;
import com.bfwg.remote.UserEntity;
import com.bfwg.web.UserResponse;
import com.bfwg.web.request.RegisterRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DefaultUserDetailsConverter {

    public static DefaultUserDetails from(UserEntity user) {
        return DefaultUserDetails.builder().
            id(user.getId()).
            password(user.getPassword()).
            email(user.getEmail()).
            username(user.getUsername()).
            firstname(user.getFirstName()).
            lastname(user.getLastName()).
            role(user.getRole()).
            enabled(user.isEnabled()).
            phoneNumber(user.getPhoneNumber()).
            lastPasswordRestTime(user.getLastPasswordResetDate().getTime()).build();
    }

    public static DefaultUserDetails from(RegisterRequest registerRequest) {
        return DefaultUserDetails.builder()
            .email(registerRequest.email)
            .password(registerRequest.password)
            .firstname(registerRequest.firstname)
            .username(registerRequest.email)
            .lastname(registerRequest.lastname)
            .phoneNumber(registerRequest.phoneNumber)
            .build();
    }

    public static List<UserResponse> toUserResponseList(List<DefaultUserDetails> userDetailsList) {
        List<UserResponse> responseList = new ArrayList<>();
        userDetailsList.stream().forEach(u -> responseList.add(new UserResponse(u)));
        return responseList;
    }

    public static List<DefaultUserDetails> from(List<UserEntity> result) {
        List<DefaultUserDetails> defaultUserDetails = new ArrayList<>();
        result.stream().forEach(u -> defaultUserDetails.add( DefaultUserDetailsConverter.from(u)));
        return defaultUserDetails;
    }

    public static UserEntity toEntity(DefaultUserDetails userDetails){
        UserEntity entity = new UserEntity();
        entity.setId(userDetails.getId());
        entity.setPassword(userDetails.getPassword());
        entity.setRole(userDetails.getRole());
        entity.setLastPasswordResetDate(new Timestamp(userDetails.getLastPasswordResetDate().getTime()));
        entity.setEmail(userDetails.getEmail());
        entity.setPhoneNumber(userDetails.getPhoneNumber());
        entity.setUsername(userDetails.getUsername());
        entity.setFirstName(userDetails.getFirstname());
        entity.setLastName(userDetails.getLastname());
        entity.setEnabled(userDetails.isEnabled());

        String role = userDetails.getRole();
        if(role == null){
            role = "ROLE_USER";
        }
        entity.setRole(role);

        return entity;
    }

}
