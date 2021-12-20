package kr.bos.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import javax.validation.Valid;
import kr.bos.annotation.BlackCheck;
import kr.bos.annotation.CurrentUserId;
import kr.bos.annotation.LoginCheck;
import kr.bos.model.dto.request.ReservationReq;
import kr.bos.model.dto.request.ReviewReq;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.model.dto.response.ReviewRes;
import kr.bos.model.dto.response.StudyCafeRes;
import kr.bos.service.ReservationService;
import kr.bos.service.ReviewService;
import kr.bos.service.StudyCafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * 북 마크 등록하기.
     *
     * @since 1.0.0
     */
    @PostMapping("/{studyCafeId}/bookmarks")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBookmark(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId) {
        studyCafeService.registerBookmark(userId, studyCafeId);
    }

    /**
     * 북 마크 취소하기.
     *
     * @since 1.0.0
     */
    @DeleteMapping("/{studyCafeId}/bookmarks")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBookmark(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId) {
        studyCafeService.cancelBookmark(userId, studyCafeId);
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
    public PageInfo<ReviewRes> getReviews(@CurrentUserId Long userId,
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
        @Valid @RequestBody ReviewReq reviewReq) {
        reviewService.createReview(reviewReq, userId, studyCafeId);
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
        @RequestBody ReviewReq reviewReq) {
        reviewService.updateReview(reviewReq, userId, reviewId);
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

    /**
     * Search.
     *
     * @since 1.0.0
     */
    @GetMapping("/search/{keyword}")
    @LoginCheck
    @BlackCheck
    public List<StudyCafeRes> search(@CurrentUserId Long userId,
        @PathVariable("keyword") String keyword) {
        return studyCafeService.findStudyCafesByKeyword(userId, keyword);
    }
}
