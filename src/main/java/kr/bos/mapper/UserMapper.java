package kr.bos.mapper;

import java.util.Optional;
import kr.bos.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * User Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface UserMapper {

    void insertUser(UserDto userDto);

    boolean isExistsEmail(String email);

    Optional<UserDto> selectUserByEmail(String email);

    void deleteUser(Long id);
}