package kr.bos.mapper;

import java.util.Optional;
import kr.bos.dto.request.UserReq;
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

    Optional<UserReq> selectUserByEmail(String email);

    void deleteUser(Long id);
}