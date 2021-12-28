package kr.bos.aop;

import java.util.Optional;
import kr.bos.exception.AccessDeniedException;
import kr.bos.exception.SelectStudyCafeNotFoundException;
import kr.bos.mapper.StudyCafeMapper;
import kr.bos.model.domain.StudyCafe;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 관리자 검증 AOP.
 *
 * @since 1.0.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class OwnerCheckAspect {

    private final StudyCafeMapper studyCafeMapper;

    /**
     * {@link kr.bos.annotation.OwnerCheck}가 붙어있는 메소드에 관리자 검증 AOP 적용.
     *
     * @since 1.0.0
     */
    @Before("@annotation(kr.bos.annotation.OwnerCheck) && args(userId, studyCafeId, ..)")
    public void ownerCheck(Long userId, Long studyCafeId) {
        StudyCafe studyCafe = studyCafeMapper.selectStudyCafeById(studyCafeId)
            .orElseThrow(SelectStudyCafeNotFoundException::new);

        if (!studyCafe.getUserId().equals(userId)) {
            throw new AccessDeniedException();
        }
    }
}
