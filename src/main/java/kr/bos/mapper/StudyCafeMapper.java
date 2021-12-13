package kr.bos.mapper;

import kr.bos.domain.StudyCafe;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 * StudyCafe Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface StudyCafeMapper {

    void insertStudyCafe(StudyCafe studyCafe);

    int insertBookmark(@Param("userId") Long userId, @Param("studyCafeId") Long studyCafeId);

    int deleteBookmark(@Param("userId") Long userId, @Param("studyCafeId") Long studyCafeId);

    boolean isBlockUser(@Param("userId") Long userId, @Param("studyCafeId") Long studyCafeId);
}
