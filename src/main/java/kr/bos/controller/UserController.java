package kr.bos.controller;

import javax.validation.Valid;
import kr.bos.annotation.CurrentUserId;
import kr.bos.annotation.LoginCheck;
import kr.bos.model.dto.request.LoginReq;
import kr.bos.model.dto.request.SignUpReq;
import kr.bos.model.dto.request.UserUpdateReq;
import kr.bos.model.dto.response.UserInfoRes;
import kr.bos.service.LoginService;
import kr.bos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller.
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
     * @param signUpReq 회원가입 입력 정보
     * @since 1.0.0
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignUpReq signUpReq) {
        userService.signUp(signUpReq);
    }

    /**
     * 로그인.
     *
     * @param loginReq 로그인 입력 정보
     * @since 1.0.0
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@Valid @RequestBody LoginReq loginReq) {
        loginService.login(loginReq);
    }

    /**
     * 회원 정보 조회.
     *
     * @param userId 유저 ID.
     * @since 1.0.0
     */
    @GetMapping
    @LoginCheck
    @ResponseStatus(HttpStatus.OK)
    public UserInfoRes getUserInfo(@CurrentUserId Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 회원 정보 수정. (Name, Address)
     *
     * @param userId 유저 ID.
     * @since 1.0.0
     */
    @PutMapping
    @LoginCheck
    @ResponseStatus(HttpStatus.OK)
    public void updateUserInfo(@CurrentUserId Long userId, @RequestBody UserUpdateReq userReq) {
        userService.updateUserInfo(userId, userReq);
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
     * @param userId 유저 ID.
     * @since 1.0.0
     */
    @DeleteMapping
    @LoginCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@CurrentUserId Long userId) {
        userService.deleteUser(userId);
    }
}