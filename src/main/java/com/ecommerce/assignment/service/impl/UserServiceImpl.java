package com.ecommerce.assignment.service.impl;

import com.ecommerce.assignment.entity.User;
import com.ecommerce.assignment.entity.constant.CommonConstant;
import com.ecommerce.assignment.entity.constant.ErrorCategory;
import com.ecommerce.assignment.exception.BusinessException;
import com.ecommerce.assignment.repository.UserRepository;
import com.ecommerce.assignment.service.api.RedisService;
import com.ecommerce.assignment.service.api.UserService;
import com.ecommerce.assignment.service.model.RegisterUserRequest;
import com.ecommerce.assignment.service.model.UserServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RedisService redisService;

  @Transactional(rollbackFor = Exception.class)
  public UserServiceResponse registerUser(RegisterUserRequest registerUserRequest) {
    User user = userRepository.findByUsernameAndIsDeleted(registerUserRequest.getUsername(), false);
    if (Objects.nonNull(user)) {
      throw BusinessException.builder().errorCode(ErrorCategory.USER_EXIST).build();
    }

    User newUser = User.builder()
            .username(registerUserRequest.getUsername())
            .build();
    userRepository.save(newUser);

    return buildUserResponse(newUser);
  }

//  private String generateToken(String username){
//    String token = UUID.randomUUID().toString();
//    redisService.save(CommonConstant.REDIS_KEY, token, username);
//    return token;
//  }



  private UserServiceResponse buildUserResponse(User user) {
    return UserServiceResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .createdDate(user.getCreatedDate())
        .updatedDate(user.getUpdatedDate())
        .build();
  }


//  public String login(String username) {
//    String token = (String) redisService.getByKey(CommonConstant.REDIS_KEY, username);
//    if (Objects.isNull(token)) {
//      String newToken = UUID.randomUUID().toString();
//      User user = userRepository.findByUsernameAndIsDeleted(username, false);
//      if (Objects.isNull(user)) {
//        throw BusinessException.builder().errorCode(ErrorCategory.USER_NOT_FOUND).build();
//      }
//      redisService.save(CommonConstant.REDIS_KEY, username, newToken);
//      redisService.save(CommonConstant.REDIS_KEY, newToken, user);
//      return newToken;
//    }
//    return token;
//  }

  public String login(String username) {
    String token = (String) redisService.getByKey(CommonConstant.REDIS_KEY, username);
    if (Objects.isNull(token)) {
      User user = userRepository.findByUsernameAndIsDeleted(username, false);
      if (Objects.isNull(user)) {
        throw BusinessException.builder().errorCode(ErrorCategory.USER_NOT_FOUND).build();
      }
      String newToken = UUID.randomUUID().toString();
      redisService.save(CommonConstant.REDIS_KEY, username, newToken);
      redisService.save(CommonConstant.REDIS_KEY, newToken, user);
      return newToken;
    }
    return token;
  }

  @Transactional
  public UserServiceResponse getUserFromToken(String token) {
    User user = (User) redisService.getByKey(CommonConstant.REDIS_KEY, token);
    if (Objects.isNull(user)) {
      throw BusinessException.builder().errorCode(ErrorCategory.USER_NOT_FOUND).build();
    }
    return buildUserResponse(user);
  }

}
