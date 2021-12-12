package kr.bos.mapper;

import kr.bos.domain.StudyCafe;
import org.apache.ibatis.annotations.Mapper;

/**
 * StudyCafe Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface StudyCafeMapper {

    void insertStudyCafe(StudyCafe studyCafe);
}
