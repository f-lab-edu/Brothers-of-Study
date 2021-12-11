package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import kr.bos.exception.ReservationWrongTimeInputException;
import kr.bos.mapper.ReservationMapper;
import kr.bos.model.domain.Reservation;
import kr.bos.model.dto.request.ReservationReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationMapper reservationMapper;

    ReservationReq reservationReq;

    @BeforeEach
    public void beforeEach() {
        reservationReq = ReservationReq.builder()
            .startTime(LocalDateTime.now().plusMinutes(1))
            .endTime(LocalDateTime.now().plusMinutes(11))
            .build();
    }

    @Test
    @DisplayName("예약 생성에 성공합니다.")
    public void createReviewWhenSuccess() {
        reservationService.createReservation(reservationReq, 1L, 2L);
        verify(reservationMapper).insertReservation(any(Reservation.class));
    }

    @Test
    @DisplayName("예약 생성에 실패합니다. :잘못된 시간 입력")
    public void createReviewWhenFail() {
        reservationReq.setEndTime(LocalDateTime.now().plusMinutes(2));
        assertThrows(ReservationWrongTimeInputException.class,
            () -> reservationService.createReservation(reservationReq, 1L, 2L));

        reservationReq.setStartTime(LocalDateTime.now().plusMinutes(100));
        assertThrows(ReservationWrongTimeInputException.class,
            () -> reservationService.createReservation(reservationReq, 1L, 2L));

        reservationReq.setStartTime(LocalDateTime.now().minusMinutes(1));
        assertThrows(ReservationWrongTimeInputException.class,
            () -> reservationService.createReservation(reservationReq, 1L, 2L));
    }
}