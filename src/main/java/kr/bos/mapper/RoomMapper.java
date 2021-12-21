package kr.bos.mapper;

import java.util.List;
import kr.bos.model.domain.Room;
import org.apache.ibatis.annotations.Mapper;

/**
 * Room Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface RoomMapper {

    void insertRooms(List<Room> rooms);

    int insertRoom(Room room);

    int updateRoom(Room room);

    void deleteRoom(Long roomId);

    List<RoomUseInfoRes> selectRoomUseInfo(@Param("studyCafeId") Long studyCafeId,
        @Param("searchTime") LocalDateTime searchTime);
}
