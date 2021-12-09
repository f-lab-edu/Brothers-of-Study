package kr.bos.mapper;

import kr.bos.dto.StudyCafeDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * StudyCafe Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface StudyCafeMapper {

    void insertStudyCafe(StudyCafeDto studyCafeDto);
}
