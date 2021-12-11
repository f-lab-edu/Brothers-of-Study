package kr.bos.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import kr.bos.exception.ReservationWrongTimeInputException;
import kr.bos.mapper.ReservationMapper;
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

    /**
     * 예약하기.
     * <br>
     * 1. 시작 시간이 현재 시간보다 앞에있는 경우. 2. 시작 시간이 완료시간보다 뒤에있는 경우. 3. 총 이용 시간이 10분 이내일 경우
     * ReservationWrongTimeInputException 발생.
     *
     * @since 1.0.0
     */
    public void createReservation(ReservationReq reservationReq, Long userId,
        Long roomId) {
        LocalDateTime startTime = reservationReq.getStartTime();
        LocalDateTime endTime = reservationReq.getEndTime();

        if (startTime.isBefore(LocalDateTime.now()) || startTime.isAfter(endTime)
            || ChronoUnit.MINUTES.between(startTime, endTime) < 10) {
            throw new ReservationWrongTimeInputException();
        }

        Reservation reservation = Reservation.builder()
            .userId(userId)
            .roomId(roomId)
            .startTime(startTime)
            .endTime(endTime)
            .build();

        reservationMapper.insertReservation(reservation);
    }
}