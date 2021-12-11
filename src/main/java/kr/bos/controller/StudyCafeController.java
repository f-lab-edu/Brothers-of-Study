package kr.bos.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.validation.Valid;
import kr.bos.annotation.BlackCheck;
import kr.bos.annotation.CurrentUserId;
import kr.bos.annotation.LoginCheck;
import kr.bos.model.dto.request.ReservationReq;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.service.ReservationService;
import kr.bos.service.ReviewService;
import kr.bos.service.StudyCafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final ReviewService reviewService;
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

    /**
     * 리뷰 목록 조회하기.
     *
     * @since 1.0.0
     */
    @GetMapping("/{studyCafeId}/reviews")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public PageInfo<kr.bos.dto.response.ReviewResponse> getReviews(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId, @RequestParam("page") Integer page,
        @RequestParam("size") Integer size) {
        PageHelper.startPage(page, size);
        return PageInfo.of(reviewService.getReviews(studyCafeId));
    }

    /**
     * 리뷰 등록하기.
     *
     * @since 1.0.0
     */
    @PostMapping("/{studyCafeId}/reviews")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId,
        @Valid @RequestBody kr.bos.dto.request.ReviewRequest reviewRequest) {
        reviewService.createReview(reviewRequest, userId, studyCafeId);
    }

    /**
     * 리뷰 수정하기.
     *
     * @since 1.0.0
     */
    @PutMapping("/{studyCafeId}/reviews/{reviewId}")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public void updateReview(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId, @PathVariable("reviewId") Long reviewId,
        @RequestBody kr.bos.dto.request.ReviewRequest reviewRequest) {
        reviewService.updateReview(reviewRequest, userId, reviewId);
    }

    /**
     * 리뷰 삭제하기.
     *
     * @since 1.0.0
     */
    @DeleteMapping("/{studyCafeId}/reviews/{reviewId}")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId, @PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }
}
