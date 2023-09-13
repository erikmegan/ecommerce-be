package com.ecommerce.assignment.service.api;

import com.ecommerce.assignment.service.model.RegisterUserRequest;
import com.ecommerce.assignment.service.model.UserServiceResponse;

public interface UserService {

    public UserServiceResponse registerUser(RegisterUserRequest registerUserRequest) ;

    public String login(String username);

    public UserServiceResponse getUserFromToken(String token);



}
