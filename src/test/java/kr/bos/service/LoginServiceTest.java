package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import kr.bos.model.domain.User;
import kr.bos.model.dto.request.LoginInfoReq;
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

    @Mock
    UserService userService;

    @InjectMocks
    LoginService loginService;

    User user;
    LoginInfoReq loginInfoReq;

    @BeforeEach
    public void beforeEach() {
        injectSessionInUserService();

        user = User.builder()
            .id(1L)
            .email("email@email.com")
            .password(PasswordEncrypt.encrypt("password"))
            .name("name")
            .address("address")
            .build();

        loginInfoReq = LoginInfoReq.builder()
            .email("email@email.com")
            .password("password")
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
        when(userService.selectUserByEmail(loginInfoReq.getEmail())).thenReturn(user);
        loginService.login(loginInfoReq);
        assertEquals(loginService.getCurrentUser(), user.getId());
    }

    @Test
    @DisplayName("로그인에 실패합니다. :잘못된 패스워드")
    public void loginTestWhenFail() {
        when(userService.selectUserByEmail(loginInfoReq.getEmail())).thenReturn(user);

        loginInfoReq.setPassword("");
        assertThrows(IllegalArgumentException.class, () -> loginService.login(loginInfoReq));

        loginInfoReq.setPassword("password" + "@");
        assertThrows(IllegalArgumentException.class, () -> loginService.login(loginInfoReq));

        loginInfoReq.setPassword("password".substring(1));
        assertThrows(IllegalArgumentException.class, () -> loginService.login(loginInfoReq));

        loginInfoReq.setPassword(PasswordEncrypt.encrypt("password"));
        assertThrows(IllegalArgumentException.class, () -> loginService.login(loginInfoReq));
    }

    @Test
    @DisplayName("로그아웃 성공.")
    public void logoutTestWhenSuccess() {
        loginService.logout();
        assertNull(loginService.getCurrentUser());
    }
}