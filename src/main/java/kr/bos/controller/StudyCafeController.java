package kr.bos.controller;

import javax.validation.Valid;
import kr.bos.annotation.BlackCheck;
import kr.bos.annotation.CurrentUserId;
import kr.bos.annotation.LoginCheck;
import kr.bos.model.dto.request.ReservationReq;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.service.ReservationService;
import kr.bos.service.StudyCafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * StudyCafe 컨트롤러.
 *
 * @since 1.0.0
 */
@RequestMapping("/study_cafes")
@RequiredArgsConstructor
@RestController
public class StudyCafeController {

    private final StudyCafeService studyCafeService;
    private final ReservationService reservationService;

    /**
     * 스터디 카페 등록하기.
     *
     * @since 1.0.0
     */
    @PostMapping
    @LoginCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void registerStudyCafe(@CurrentUserId Long userId,
        @Valid @RequestBody StudyCafeReq studyCafeReq) {

        studyCafeService.registerStudyCafe(userId, studyCafeReq);
    }

    /**
     * 예약하기.
     *
     * @since 1.0.0
     */
    @PostMapping("/{studyCafeId}/rooms/{roomId}/reservations")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId,
        @PathVariable("roomId") Long roomId,
        @Valid @RequestBody ReservationReq reservationReq) {
        reservationService.createReservation(reservationReq, userId, roomId);
    }
}
