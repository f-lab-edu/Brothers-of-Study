package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.bos.exception.DuplicatedEmailException;
import kr.bos.exception.SelectUserNotFoundException;
import kr.bos.mapper.UserMapper;
import kr.bos.model.domain.User;
import kr.bos.model.dto.request.UserReq;
import kr.bos.utils.PasswordEncrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    User user;
    UserReq userReq;

    @BeforeEach
    public void makeUser() {
        user = User.builder()
            .id(1L)
            .email("email@email.com")
            .password(PasswordEncrypt.encrypt("password"))
            .name("name")
            .address("address")
            .build();

        userReq = UserReq.builder()
            .email("email@email.com")
            .password("password")
            .name("name")
            .address("address")
            .build();
    }

    @Test
    @DisplayName("회원가입에 성공합니다.")
    public void signUpTestWhenSuccess() {
        when(userMapper.isExistsEmail(userReq.getEmail())).thenReturn(false);
        userService.signUp(userReq);
        verify(userMapper).insertUser(userReq);
    }

    @Test
    @DisplayName("회원가입에 실패합니다. :중복된 이메일")
    public void signUpTestWhenFail() {
        when(userMapper.isExistsEmail(userReq.getEmail())).thenReturn(true);
        assertThrows(DuplicatedEmailException.class, () -> userService.signUp(userReq));
        verify(userMapper).isExistsEmail(userReq.getEmail());
    }

    @Test
    @DisplayName("회원 조회에 성공합니다.")
    public void selectUserByEmailTestWhenSuccess() {
        when(userMapper.selectUserByEmail(userReq.getEmail()))
            .thenReturn(Optional.ofNullable(user));
        userService.selectUserByEmail(userReq.getEmail());
    }

    @Test
    @DisplayName("회원 조회에 실패합니다. :존재하지않는 이메일")
    public void selectUserByEmailTestWhenFail() {
        assertThrows(SelectUserNotFoundException.class,
            () -> userService.selectUserByEmail(userReq.getEmail()));
    }

    @Test
    @DisplayName("회원탈퇴에 성공합니다.")
    public void deleteUserWhenSuccess() {
        userService.deleteUser(user.getId());
        verify(userMapper).deleteUser(user.getId());
    }
}