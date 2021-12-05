package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import kr.bos.dto.LoginInfoDto;
import kr.bos.dto.UserDto;
import kr.bos.exception.InvalidPasswordException;
import kr.bos.utils.PasswordEncrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    private static final String USER_KEY = "USER_ID";

    @Mock
    UserService userService;

    @InjectMocks
    LoginService loginService;

    UserDto userDto;
    LoginInfoDto loginInfoDto;
    String password;

    @BeforeEach
    public void beforeEach() {
        injectSessionInUserService();

        password = "password";
        userDto = UserDto.builder()
            .id(1L)
            .email("email@email.com")
            .password(PasswordEncrypt.encrypt(password))
            .name("name")
            .address("address")
            .build();

        loginInfoDto = LoginInfoDto.builder()
            .email("email@email.com")
            .password(password)
            .build();
    }

    private void injectSessionInUserService() {
        try {
            Field sessionField = loginService.getClass().getDeclaredField("session");
            sessionField.setAccessible(true);
            sessionField.set(loginService, new MockHttpSession());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("로그인에 성공합니다.")
    public void loginTestWhenSuccess() {
        when(userService.selectUserByEmail(loginInfoDto.getEmail())).thenReturn(userDto);
        loginService.login(loginInfoDto);
        assertEquals(loginService.getCurrentUser(), userDto.getId());
    }

    @Test
    @DisplayName("로그인에 실패합니다. :잘못된 패스워드")
    public void loginTestWhenFail() {
        when(userService.selectUserByEmail(loginInfoDto.getEmail())).thenReturn(userDto);

        loginInfoDto.setPassword("");
        assertThrows(InvalidPasswordException.class,
            () -> loginService.login(loginInfoDto));

        loginInfoDto.setPassword(password + "@");
        assertThrows(InvalidPasswordException.class,
            () -> loginService.login(loginInfoDto));

        loginInfoDto.setPassword(password.substring(1));
        assertThrows(InvalidPasswordException.class,
            () -> loginService.login(loginInfoDto));

        loginInfoDto.setPassword(PasswordEncrypt.encrypt(password));
        assertThrows(InvalidPasswordException.class,
            () -> loginService.login(loginInfoDto));
    }

    @Test
    @DisplayName("로그아웃 성공.")
    public void logoutTestWhenSuccess() {
        loginService.logout();
        assertNull(loginService.getCurrentUser());
    }
}