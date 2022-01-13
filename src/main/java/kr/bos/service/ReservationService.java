package kr.bos.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import kr.bos.exception.DuplicatedException;
import kr.bos.exception.NotFoundException;
import kr.bos.mapper.ReservationMapper;
import kr.bos.mapper.RoomMapper;
import kr.bos.model.domain.Reservation;
import kr.bos.model.dto.request.ReservationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Reservation Service.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final RoomMapper roomMapper;

    /**
     * 예약하기.
     * <br>
     * 1. 시작 시간이 현재 시간보다 앞에있는 경우. 2. 시작 시간이 완료시간보다 뒤에있는 경우. 3. 총 이용 시간이 10분 이내일 경우 예외 발생.
     *
     * @param reservationReq 예약 DTO
     * @param userId         유저 ID
     * @param roomId         방 ID
     * @since 1.0.0
     */
    public void createReservation(ReservationReq reservationReq, Long userId, Long roomId) {
        LocalDateTime startTime = reservationReq.getStartTime();
        LocalDateTime endTime = reservationReq.getEndTime();

        if (startTime.isBefore(LocalDateTime.now()) || startTime.isAfter(endTime)
            || ChronoUnit.MINUTES.between(startTime, endTime) < 10) {
            throw new IllegalArgumentException("Please check your reservation time again.");
        }

        roomMapper.getRoomLockById(roomId)
            .orElseThrow(() -> new NotFoundException("Select not found room_lock"));

        if (reservationMapper.isExistsReservationByRoomIdAndUseTime(roomId, startTime, endTime)) {
            throw new DuplicatedException("This Reservation Time already exists.");
        }

        Reservation reservation = Reservation.builder()
            .userId(userId)
            .roomId(roomId)
            .startTime(startTime)
            .endTime(endTime)
            .build();

        reservationMapper.insertReservation(reservation);
    }

    /**
     * 예약 취소하기.
     * <br>
     * 예약 시작 시간이 10분 전에 취소 가능. 10분 이내일 경우 예외 발생.
     *
     * @param userId        유저 ID
     * @param reservationId 예약 ID
     * @since 1.0.0
     */
    public void cancelReservation(Long userId, Long reservationId) {
        Reservation reservation = reservationMapper.selectReservationByIdAndUserId(userId,
            reservationId).orElseThrow(() -> new NotFoundException("Select not found reservation"));

        if (reservation.getStartTime().minusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservations can not be canceled 10 minutes.");
        }

        reservationMapper.deleteReservation(reservationId);
    }
}