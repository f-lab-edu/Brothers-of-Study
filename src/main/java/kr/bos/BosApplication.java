package kr.bos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Starting point of an application.
*/
@EnableCaching
@EnableRedisHttpSession
@SpringBootApplication
public class BosApplication {

    public static void main(String[] args) {
        SpringApplication.run(BosApplication.class);
    }
}
