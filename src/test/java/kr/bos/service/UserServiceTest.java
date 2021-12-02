package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.bos.dto.UserDto;
import kr.bos.exception.DuplicatedEmailException;
import kr.bos.mapper.UserMapper;
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

    UserDto userDto;

    @BeforeEach
    public void makeUser() {
        userDto = UserDto.builder()
            .email("email@email.com")
            .password("password")
            .name("name")
            .address("address")
            .build();
    }

    @Test
    @DisplayName("회원가입에 성공합니다.")
    public void signUpTestWhenSuccess() {
        when(userMapper.isExistsEmail(userDto.getEmail())).thenReturn(false);
        userService.signUp(userDto);
        verify(userMapper).insertUser(userDto);
    }

    @Test
    @DisplayName("회원가입에 실패합니다. :중복된 이메일")
    public void signUpTestWhenFail() {
        when(userMapper.isExistsEmail(userDto.getEmail())).thenReturn(true);
        assertThrows(DuplicatedEmailException.class, () -> userService.signUp(userDto));
        verify(userMapper).isExistsEmail(userDto.getEmail());
    }
}