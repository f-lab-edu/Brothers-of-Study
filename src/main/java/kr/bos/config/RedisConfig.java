package kr.bos.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig. 레디스의 세션과 캐시 사용을 분리하기 위한 설정.
 *
 * @since 1.0.0
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.session.host}")
    private String redisSessionHost;

    @Value("${spring.redis.session.port}")
    private int redisSessionPort;

    @Value("${spring.redis.session.password}")
    private String redisSessionPassword;

    @Value("${spring.redis.cache.host}")
    private String redisCacheHost;

    @Value("${spring.redis.cache.port}")
    private int redisCachePort;

    @Value("${spring.redis.cache.password}")
    private String redisCachePassword;

    /**
     * Session 사용을 위한 RedisConnectionFactory Bean 등록.
     * <br>
     * RedisConnectionFactory: 레디스 서버 연결을 위한 Connection을 관리하는 인터페이스.
     *
     * @since 1.0.0
     */
    @Bean({"redisConnectionFactory", "redisSessionConnectionFactory"})
    public RedisConnectionFactory redisSessionConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration();
        redisStandaloneConfig.setHostName(redisSessionHost);
        redisStandaloneConfig.setPort(redisSessionPort);
        redisStandaloneConfig.setPassword(redisCachePassword);
        return new LettuceConnectionFactory(redisStandaloneConfig);
    }

    /**
     * Cache 사용을 위한 RedisConnectionFactory Bean 등록.
     *
     * @since 1.0.0
     */
    @Bean
    public RedisConnectionFactory redisCacheConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration();
        redisStandaloneConfig.setHostName(redisCacheHost);
        redisStandaloneConfig.setPort(redisCachePort);
        redisStandaloneConfig.setPassword(redisCachePassword);
        return new LettuceConnectionFactory(redisStandaloneConfig);
    }

    /**
     * Redis Template Bean 등록.
     * <br>
     * RedisTemplate: Session RedisConnection 에서 넘겨준 byte 를 직렬화하는 역할 수행.
     *
     * @since 1.0.0
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
            new GenericJackson2JsonRedisSerializer();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisSessionConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }

    /**
     * RedisCacheManager Bean 등록.
     * <br>
     * RedisCacheManager: 스프링에서 추상화되어있는 CacheManager 인터페이스를 레디스 사용을 위해 구현한 클래스.
     * <br>
     * 캐시 key, value를 직렬화,역직렬화 하기위한 설정 입력.
     *
     * @since 1.0.0
     */
    @Bean
    public RedisCacheManager redisCacheManager(
        @Qualifier("redisCacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues().serializeKeysWith(SerializationPair.fromSerializer(
                new StringRedisSerializer()))
            .serializeValuesWith(SerializationPair.fromSerializer(
                new GenericJackson2JsonRedisSerializer()))
            .entryTtl(Duration.ofDays(1L));

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheConfig).build();
    }
}
