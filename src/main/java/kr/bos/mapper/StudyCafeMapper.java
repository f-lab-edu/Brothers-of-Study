package kr.bos.mapper;

import java.util.List;
import java.util.Optional;
import kr.bos.model.domain.StudyCafe;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.model.dto.response.StudyCafeDetailRes;
import kr.bos.model.dto.response.StudyCafeRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    Optional<StudyCafe> selectStudyCafeById(Long studyCafeId);

    Optional<StudyCafeDetailRes> selectStudyCafeDetailById(Long studyCafeId);

    void deleteRoom(Long roomId);

    Optional<StudyCafeRes> getStudyCafeById(Long id);

    Optional<Long> getStudyCafeIdByName(String name);

    List<StudyCafeRes> getStudyCafesByKeyword(Long userId, String name);

    int updateStudyCafe(StudyCafeReq studyCafeReq);

    int deleteStudyCafe(Long id);
}
