package kr.bos.mapper;

import java.time.LocalDateTime;
import java.util.List;
import kr.bos.model.domain.Room;
import kr.bos.model.dto.response.RoomUseInfoRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Room Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface RoomMapper {

    void insertRooms(List<Room> rooms);

    void insertRoom(Room room);

    void updateRoom(Room room);

    void deleteRoom(Long roomId);

    List<RoomUseInfoRes> selectRoomUseInfo(@Param("studyCafeId") Long studyCafeId,
        @Param("searchTime") LocalDateTime searchTime);

    boolean isExistsRoomNumber(@Param("roomNumber") Integer roomNumber,
        @Param("studyCafeId") Long studyCafeId);
}
