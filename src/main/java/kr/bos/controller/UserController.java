package kr.bos.controller;

import javax.validation.Valid;
import kr.bos.annotation.CurrentUserId;
import kr.bos.annotation.LoginCheck;
import kr.bos.dto.request.LoginInfoReq;
import kr.bos.dto.request.UserReq;
import kr.bos.service.LoginService;
import kr.bos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User 컨트롤러.
 *
 * @since 1.0.0
 */
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    /**
     * 회원 가입.
     *
     * @param userReq 회원가입 입력 정보
     * @since 1.0.0
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody UserReq userReq) {
        userService.signUp(userReq);
    }

    /**
     * 로그인.
     *
     * @param loginInfoReq 로그인 입력 정보
     * @since 1.0.0
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@Valid @RequestBody LoginInfoReq loginInfoReq) {
        loginService.login(loginInfoReq);
    }

    /**
     * 로그아웃.
     *
     * @since 1.0.0
     */
    @PostMapping("/logout")
    @LoginCheck
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        loginService.logout();
    }

    /**
     * 회원탈퇴.
     *
     * @since 1.0.0
     */
    @DeleteMapping
    @LoginCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@CurrentUserId Long userId) {
        userService.deleteUser(userId);
    }
}