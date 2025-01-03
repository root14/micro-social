package com.root14.trendservice.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class RedisScheduler {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisScheduler(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * run every midnight(00:00) archive trends and clear cache
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void archiveCache() {
        //todo store it to  somewhere
        //clear cache
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
    }

}
