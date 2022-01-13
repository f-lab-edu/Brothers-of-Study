package kr.bos.mapper;

import java.util.List;
import java.util.Optional;
import kr.bos.model.domain.StudyCafe;
import kr.bos.model.dto.request.SearchOption;
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

    List<StudyCafeRes> selectStudyCafesBySearchOption(SearchOption searchOption);

    Long selectStudyCafesCountsBySearchOption(SearchOption searchOption);

    void insertStudyCafe(StudyCafe studyCafe);

    void insertBookmark(@Param("userId") Long userId, @Param("studyCafeId") Long studyCafeId);

    int deleteBookmark(@Param("userId") Long userId, @Param("studyCafeId") Long studyCafeId);

    boolean isBlockUser(@Param("userId") Long userId, @Param("studyCafeId") Long studyCafeId);

    Optional<StudyCafe> selectStudyCafeById(Long studyCafeId);

    Optional<StudyCafeDetailRes> selectStudyCafeDetailById(Long studyCafeId);

    boolean isExistsBookmark(@Param("userId") Long userId, @Param("studyCafeId") Long studyCafeId);
}
