package kr.bos.mapper;

import kr.bos.model.domain.StudyCafe;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.model.dto.response.StudyCafeRes;
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

    Optional<StudyCafeRes> getStudyCafeById(Long id);

    Optional<Long> getStudyCafeIdByName(String name);

    List<StudyCafeRes> getStudyCafesByKeyword(Long userId, String name);

    int updateStudyCafe(StudyCafeReq studyCafeReq);

    int deleteStudyCafe(Long id);
}
