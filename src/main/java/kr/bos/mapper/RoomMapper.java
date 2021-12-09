package kr.bos.mapper;

import kr.bos.dto.StudyCafeDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * Room Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface RoomMapper {

    void insertRooms(StudyCafeDto studyCafeDto);
}
