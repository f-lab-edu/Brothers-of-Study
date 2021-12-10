package kr.bos.mapper;

import java.util.List;
import java.util.Optional;
import kr.bos.domain.StudyCafe;
import org.apache.ibatis.annotations.Mapper;


/**
 * StudyCafe Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface StudyCafeMapper {

    Optional<StudyCafe> getStudyCafeById(Long id);

    Optional<Long> getStudyCafeIdByName(String name);

    List<StudyCafe> getStudyCafesByKeyword(String name);

    int insertStudyCafe(StudyCafe studyCafe);

    int updateStudyCafe(StudyCafe studyCafe);

    int deleteStudyCafe(Long id);
}
