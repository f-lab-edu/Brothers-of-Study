package kr.bos.mapper;

import java.util.Optional;
import kr.bos.model.domain.User;
import kr.bos.model.dto.request.UserReq;
import org.apache.ibatis.annotations.Mapper;

/**
 * User Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface UserMapper {

    void insertUser(UserReq userReq);

    boolean isExistsEmail(String email);

    Optional<User> selectUserByEmail(String email);

    void deleteUser(Long id);
}