package kr.bos.controller;

import javax.validation.Valid;
import kr.bos.dto.UserDto;
import kr.bos.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
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

    /**
     * 회원 가입.
     *
     * @param userDto 회원가입 입력 정보
     *
     * @since 1.0.0
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDto userDto) {
        userService.signUp(userDto);
    }
}
