package com.qinggong.demo.dao;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisDao {
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	// 从redis中查询数据
	public Object get(String key) {
		ValueOperations<String, Object> opera = redisTemplate.opsForValue();
		return opera.get(key);
	}

	// 向redis中插入数据
	public void set(String key, Object value, Long expire) {
		ValueOperations<String, Object> opera = redisTemplate.opsForValue();
		// 时间单位：毫秒
		opera.set(key, value, expire, TimeUnit.MILLISECONDS);
	}
}
