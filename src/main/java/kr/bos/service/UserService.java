package kr.bos.service;

import kr.bos.dto.UserDto;
import kr.bos.exception.DuplicatedEmailException;
import kr.bos.mapper.UserMapper;
import kr.bos.utils.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * User Service.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    /**
     * 회원 가입.
     *
     * @param userDto 회원가입 입력 정보
     *
     * @since 1.0.0
     */
    public void signUp(UserDto userDto) {
        if (isExistsEmail(userDto.getEmail())) {
            throw new DuplicatedEmailException("This Email already exists.");
        }

        String encryptPassword = PasswordEncrypt.encrypt(userDto.getPassword());
        userDto.setPassword(encryptPassword);
        userMapper.insertUser(userDto);
    }

    public boolean isExistsEmail(String email) {
        return userMapper.isExistsEmail(email);
    }
}