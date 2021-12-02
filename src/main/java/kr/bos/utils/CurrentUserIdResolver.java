package kr.bos.utils;

import kr.bos.annotation.CurrentUserId;
import kr.bos.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * CurrentUserIdResolver.
 *
 * {@link kr.bos.annotation.CurrentUserId}가 컨트롤러 메서드 파라미터에 있을 때 바인딩 처리.
 * HandlerMethodArgumentResolver는 컨트롤러 메서드에서 특정 조건에 맞는 파라미터가 있을 때 값을 바인딩할 수 있는 인터페이스.
 * ex) @RequestBody, @PathVariable 들도 HandlerMethodArgumentResolver는를 통해 바인딩 됨.
 *
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class CurrentUserIdResolver implements HandlerMethodArgumentResolver {

    private final LoginService loginService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
        ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
        WebDataBinderFactory webDataBinderFactory) {
        return loginService.getCurrentUser();
    }
}