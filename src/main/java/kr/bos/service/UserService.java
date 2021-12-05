package kr.bos.service;

import java.util.Optional;
import kr.bos.dto.UserDto;
import kr.bos.exception.DuplicatedEmailException;
import kr.bos.exception.SelectUserNotFoundException;
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
     * @since 1.0.0
     */
    public void signUp(UserDto userDto) {
        if (isExistsEmail(userDto.getEmail())) {
            throw new DuplicatedEmailException();
        }

        String encryptPassword = PasswordEncrypt.encrypt(userDto.getPassword());
        userDto.setPassword(encryptPassword);
        userMapper.insertUser(userDto);
    }

    public boolean isExistsEmail(String email) {
        return userMapper.isExistsEmail(email);
    }

    /**
     * Email 일치하는 유저 검색.
     *
     * @since 1.0.0
     */
    public UserDto selectUserByEmail(String email) {
        Optional<UserDto> userDto = userMapper.selectUserByEmail(email);
        userDto.orElseThrow(SelectUserNotFoundException::new);
        return userDto.get();
    }

    /**
     * id에 해당하는 유저 삭제.
     *
     * @since 1.0.0
     */
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }
}