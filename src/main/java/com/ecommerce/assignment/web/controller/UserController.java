package com.ecommerce.assignment.web.controller;

import com.ecommerce.assignment.service.api.UserService;
import com.ecommerce.assignment.service.model.RegisterUserRequest;
import com.ecommerce.assignment.service.model.UserServiceResponse;
import com.ecommerce.assignment.web.constant.ApiPath;
import com.ecommerce.assignment.web.model.UserApiRequest;
import com.ecommerce.assignment.web.model.UserApiResponse;
import com.ecommerce.assignment.web.model.UserLoginApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiPath.USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = ApiPath.CREATE_USER)
    public ResponseEntity<UserApiResponse> registerUser(@Valid @RequestBody UserApiRequest userApiRequest){
        UserServiceResponse serviceResponse = userService.registerUser(RegisterUserRequest.builder().username(userApiRequest.getUsername()).build());
        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(buildUserCreateResponse(serviceResponse));
    }

    private UserApiResponse buildUserCreateResponse(UserServiceResponse userServiceResponse){
        return UserApiResponse.builder()
                .username(userServiceResponse.getUsername())
                .createdDate(userServiceResponse.getCreatedDate()).build();
    }


    @PostMapping(value = ApiPath.LOGIN)
    public ResponseEntity<UserLoginApiResponse> login(@RequestParam String username){
        String serviceResponse = userService.login(username);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(UserLoginApiResponse.builder().token(serviceResponse).build());
    }
}
