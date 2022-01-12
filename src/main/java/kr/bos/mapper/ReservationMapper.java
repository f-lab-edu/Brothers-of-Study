package kr.bos.mapper;

import java.time.LocalDateTime;
import java.util.Optional;
import kr.bos.model.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Reservation Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface ReservationMapper {

    void insertReservation(Reservation reservation);

    boolean isExistsNowReservationByRoomId(Long roomId);

    boolean isExistsReservationByRoomIdAndUseTime(@Param("roomId") Long roomId,
        @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    Optional<Reservation> selectReservationByIdAndUserId(@Param("userId") Long userId,
        @Param("reservationId") Long reservationId);

    void deleteReservation(Long id);
}