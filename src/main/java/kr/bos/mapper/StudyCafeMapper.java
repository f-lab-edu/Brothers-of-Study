package kr.bos.mapper;

import kr.bos.model.domain.StudyCafe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Optional;


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

    Optional<StudyCafe> getStudyCafeById(Long id);

    Optional<Long> getStudyCafeIdByName(String name);

    List<StudyCafe> getStudyCafesByKeyword(String name);

    int updateStudyCafe(StudyCafe studyCafe);

    int deleteStudyCafe(Long id);
}
