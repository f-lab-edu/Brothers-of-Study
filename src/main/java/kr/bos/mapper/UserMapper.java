package kr.bos.mapper;

import java.util.Optional;
import kr.bos.model.domain.User;
import kr.bos.model.dto.response.UserInfoRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface UserMapper {

    void insertUser(User user);

    boolean isExistsEmail(String email);

    Optional<User> selectUserByEmail(String email);

    void deleteUser(Long id);

    Optional<UserInfoRes> selectUserById(Long userId);

    void updateUserById(User user);
}