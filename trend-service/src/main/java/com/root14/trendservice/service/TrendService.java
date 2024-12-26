package com.root14.trendservice.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrendService {

    private final RedisTemplate<String, String> redisTemplate;

    public TrendService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ResponseEntity<Object> getTrend(String period) {
        Set<String> topTrends = redisTemplate.opsForZSet().reverseRange("trends", 0, 4);

        //todo add period  support

        List<String> topTrendList = Objects.requireNonNull(topTrends).stream().toList();
        return ResponseEntity.ok(topTrendList);
    }


}
