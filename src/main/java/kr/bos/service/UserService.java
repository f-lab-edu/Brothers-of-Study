package kr.bos.service;

import java.util.Optional;
import kr.bos.exception.DuplicatedEmailException;
import kr.bos.exception.SelectUserNotFoundException;
import kr.bos.mapper.UserMapper;
import kr.bos.model.domain.User;
import kr.bos.model.dto.request.UserReq;
import kr.bos.model.dto.response.UserInfoRes;
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
        Optional<User> user = userMapper.selectUserByEmail(email);
        user.orElseThrow(SelectUserNotFoundException::new);
        return user.get();
    }

    /**
     * id에 해당하는 유저 삭제.
     *
     * @since 1.0.0
     */
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    /**
     * id에 해당하는 유저 정보 조회.
     *
     * @param userId 유저 ID.
     * @since 1.0.0
     */
    public UserInfoRes getUserInfo(Long userId) {
        return userMapper.selectUserById(userId)
            .orElseThrow(SelectUserNotFoundException::new);
    }

    /**
     * id에 해당하는 유저 정보 수정. (Name, Address).
     *
     * @param userId  유저 ID.
     * @param userReq 회원수정 DTO.
     * @since 1.0.0
     */
    public void updateUserInfo(Long userId, UserReq userReq) {
        userMapper.updateUserById(userId, userReq);
    }
}