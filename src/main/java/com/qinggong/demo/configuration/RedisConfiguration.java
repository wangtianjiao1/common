package com.qinggong.demo.configuration;

import java.lang.reflect.Method;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

@Configuration
public class RedisConfiguration {
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.max-active}")
	private int maxactive;

	@Value("${spring.redis.max-idle}")
	private int maxidle;

	@Value("${spring.redis.min-idle}")
	private int minidle;

	@Value("${spring.redis.max-wait}")
	private long maxwait;

	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory() {
		// 设置连接池属性
		GenericObjectPoolConfig poolconfig = new GenericObjectPoolConfig();
		poolconfig.setMaxTotal(maxactive);
		poolconfig.setMaxIdle(maxidle);
		poolconfig.setMinIdle(minidle);
		poolconfig.setMaxWaitMillis(maxwait);
		LettucePoolingClientConfiguration configuration = LettucePoolingClientConfiguration.builder().poolConfig(poolconfig).build();

		// 设置redis连接属性
		RedisStandaloneConfiguration redisconfig = new RedisStandaloneConfiguration();
		redisconfig.setHostName(host);
		redisconfig.setPort(port);
		redisconfig.setPassword(RedisPassword.of(password.toCharArray()));

		return new LettuceConnectionFactory(redisconfig, configuration);
	}

	// 配置redisTemplate
	@Bean
	public RedisTemplate<String, Object> redisCacheTemplate(LettuceConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		om.enableDefaultTyping(DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(om);
		template.setConnectionFactory(factory);
		template.setValueSerializer(serializer);
		template.setHashValueSerializer(serializer);

		return template;
	}

	// 配置统一的key生成方式
	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for(Object obj : params) {
					sb.append(obj.toString());
				}

				return sb.toString();
			}
		};
	}

	// 配置统一缓存管理
	// ...
}
