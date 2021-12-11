package kr.bos.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.bos.model.domain.StudyCafe;

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
