package kr.bos.controller;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 배포를 위한 Controller.
 *
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class DeployController {

    private final Environment env;

    /**
     * 현재 구동중인 profile 체크 엔드포인트.
     * <br>
     * 배포 방식은 블루-그린 배포 방식을 적용. 구동중인 profile을 확인하고 배포할 profile을 선택.
     *
     * @since 1.0.0
     */
    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("profile1", "profile2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);
        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
    }

    /**
     * 서버가 정상적으로 구동되는지 확인하는 엔드포인트.
     *
     * @since 1.0.0
     */
    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
