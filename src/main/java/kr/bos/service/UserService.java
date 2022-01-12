package kr.bos.service;

import kr.bos.exception.DuplicatedException;
import kr.bos.exception.NotFoundException;
import kr.bos.mapper.UserMapper;
import kr.bos.model.domain.User;
import kr.bos.model.dto.request.UserReq;
import kr.bos.model.dto.response.UserInfoRes;
import kr.bos.utils.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param userReq 회원가입 DTO
     * @since 1.0.0
     */
    public void signUp(UserReq userReq) {
        if (isExistsEmail(userReq.getEmail())) {
            throw new DuplicatedException("This email already exists.");
        }

        User user = User.builder()
            .email(userReq.getEmail())
            .password(PasswordEncrypt.encrypt(userReq.getPassword()))
            .name(userReq.getName())
            .address(userReq.getAddress())
            .build();

        userMapper.insertUser(user);
    }

    @Transactional(readOnly = true)
    public boolean isExistsEmail(String email) {
        return userMapper.isExistsEmail(email);
    }

    /**
     * Email 일치하는 유저 검색.
     *
     * @param email 이메일
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email)
            .orElseThrow(() -> new NotFoundException("Select not found user"));
    }

    /**
     * id에 해당하는 유저 삭제.
     *
     * @param userId 유저 ID
     * @since 1.0.0
     */
    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }

    /**
     * id에 해당하는 유저 정보 조회.
     *
     * @param userId 유저 ID.
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public UserInfoRes getUserInfo(Long userId) {
        return userMapper.selectUserById(userId)
            .orElseThrow(() -> new NotFoundException("Select not found user"));
    }

    /**
     * id에 해당하는 유저 정보 수정. (Name, Address).
     *
     * @param userId  유저 ID.
     * @param userReq 회원수정 DTO.
     * @since 1.0.0
     */
    public void updateUserInfo(Long userId, UserReq userReq) {
        User user = User.builder()
            .name(userReq.getName())
            .name(userReq.getAddress())
            .build();

        userMapper.updateUserById(userId, user);
    }
}