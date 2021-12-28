package kr.bos.service;

import java.util.Optional;
import kr.bos.model.domain.User;
import kr.bos.model.dto.request.UserReq;
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
    public void signUp(UserReq userReq) {
        if (isExistsEmail(userReq.getEmail())) {
            throw new DuplicatedEmailException();
        }

        String encryptPassword = PasswordEncrypt.encrypt(userReq.getPassword());
        userReq.setPassword(encryptPassword);
        userMapper.insertUser(userReq);
    }

    public boolean isExistsEmail(String email) {
        return userMapper.isExistsEmail(email);
    }

    /**
     * Email 일치하는 유저 검색.
     *
     * @since 1.0.0
     */
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email)
            .orElseThrow(SelectUserNotFoundException::new);
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