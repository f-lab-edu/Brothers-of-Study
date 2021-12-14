package kr.bos.mapper;

import kr.bos.model.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * Reservation Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface ReservationMapper {

    void insertReservation(Reservation reservation);

    boolean isExistsReservationByRoomIdAndUseTime(@Param("roomId") Long roomId,
        @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}