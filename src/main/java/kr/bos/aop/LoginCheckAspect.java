package kr.bos.aop;

import kr.bos.exception.RequiredLoginException;
import kr.bos.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 로그인 검증 AOP.
 *
 * @since 1.0.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final LoginService loginService;

    /**
     * {@link kr.bos.annotation.LoginCheck}가 붙어있는 메소드에 로그인 검증 AOP 적용.
     *
     * @since 1.0.0
     */
    @Before("@annotation(kr.bos.annotation.LoginCheck)")
    public Long loginCheck() {
        Long userId = loginService.getCurrentUser();
        if (userId == null) {
            throw new RequiredLoginException("This service requires login");
        }

        return userId;
    }
}