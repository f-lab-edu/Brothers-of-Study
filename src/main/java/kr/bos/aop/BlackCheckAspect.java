package kr.bos.aop;

import kr.bos.exception.AccessDeniedException;
import kr.bos.mapper.StudyCafeMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 블랙리스트 체크 AOP.
 *
 * @since 1.0.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class BlackCheckAspect {

    private final StudyCafeMapper studyCafeMapper;

    /**
     * {@link kr.bos.annotation.BlackCheck}가 붙어있는 메소드에 블랙리스트 체크 AOP 적용.
     *
     * @since 1.0.0
     */
    @Before("@annotation(kr.bos.annotation.BlackCheck) && args(userId, studyCafeId, ..)")
    public void blackCheck(Long userId, Long studyCafeId) {
        if (studyCafeMapper.isBlockUser(userId, studyCafeId)) {
            throw new AccessDeniedException("The service cannot be accessed.");
        }
    }
}
