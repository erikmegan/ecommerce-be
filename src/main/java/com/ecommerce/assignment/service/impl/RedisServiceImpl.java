package com.ecommerce.assignment.service.impl;

import com.ecommerce.assignment.service.api.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public Object getByKey(String key, String hash) {
    return redisTemplate.opsForHash().get(key, hash);
  }

  @Override
  public void save(String key, String hash, Object object) {
    redisTemplate.opsForHash().put(key, hash, object);
  }
}
