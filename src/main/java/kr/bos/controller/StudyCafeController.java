package kr.bos.controller;

import javax.validation.Valid;
import kr.bos.annotation.BlackCheck;
import kr.bos.annotation.CurrentUserId;
import kr.bos.annotation.LoginCheck;
import kr.bos.annotation.OwnerCheck;
import kr.bos.model.dto.request.ReservationReq;
import kr.bos.model.dto.request.ReviewReq;
import kr.bos.model.dto.request.RoomReq;
import kr.bos.model.dto.request.SearchOption;
import kr.bos.model.dto.request.SearchTimeReq;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.model.dto.response.PageInfo;
import kr.bos.model.dto.response.StudyCafeDetailRes;
import kr.bos.service.ReservationService;
import kr.bos.service.ReviewService;
import kr.bos.service.RoomService;
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
 * StudyCafe Controller.
 *
 * @since 1.0.0
 */
@RequestMapping("/study_cafes")
@RequiredArgsConstructor
@RestController
public class StudyCafeController {

    private final StudyCafeService studyCafeService;
    private final ReviewService reviewService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    /**
     * 스터디 카페 등록하기.
     *
     * @param userId       유저 ID
     * @param studyCafeReq 스터디카페 Request DTO
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
     * 스터디 상세 조회하기.
     *
     * @param userId        유저 ID - @BlackCheck 용도
     * @param studyCafeId   스터디카페 ID
     * @param searchTimeReq 검색시간 DTO
     * @since 1.0.0
     */
    @GetMapping("/{studyCafeId}")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public StudyCafeDetailRes getStudyCafe(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId,
        @RequestBody SearchTimeReq searchTimeReq) {
        return studyCafeService.getStudyCafe(studyCafeId, searchTimeReq);
    }

    /**
     * 북 마크 등록하기.
     *
     * @param userId      유저 ID
     * @param studyCafeId 스터디카페 ID
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
     * @param userId      유저 ID
     * @param studyCafeId 스터디카페 ID
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
     * 방 추가하기.
     *
     * @param userId      유저 ID - @BlackCheck 용도
     * @param studyCafeId 스터디카페 ID
     * @param roomReq     Room DTO
     * @since 1.0.0
     */
    @PostMapping("/{studyCafeId}/rooms")
    @LoginCheck
    @OwnerCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void createRoom(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId, @RequestBody RoomReq roomReq) {
        roomService.createRoom(roomReq, studyCafeId);
    }

    /**
     * 방 수정하기.
     *
     * @param userId      유저 ID - @BlackCheck 용도
     * @param studyCafeId 스터디카페 ID
     * @param roomId      방 ID
     * @param roomReq     Room DTO
     * @since 1.0.0
     */
    @PutMapping("/{studyCafeId}/rooms/{roomId}")
    @LoginCheck
    @OwnerCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public void updateRoom(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId,
        @PathVariable("roomId") Long roomId, @RequestBody RoomReq roomReq) {
        roomService.updateRoom(roomReq, roomId, studyCafeId);
    }

    /**
     * 방 삭제하기.
     *
     * @param userId      유저 ID - @BlackCheck, @OwnerCheck 용도
     * @param studyCafeId 스터디카페 ID - @BlackCheck, @OwnerCheck 용도
     * @param roomId      방 ID
     * @since 1.0.0
     */
    @DeleteMapping("/{studyCafeId}/rooms/{roomId}")
    @LoginCheck
    @OwnerCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId, @PathVariable("roomId") Long roomId) {
        roomService.deleteRoom(roomId);
    }

    /**
     * 예약하기.
     *
     * @param userId         유저 ID
     * @param roomId         방 ID
     * @param reservationReq 예약 요청 DTO.
     * @since 1.0.0
     */
    @PostMapping("/{studyCafeId}/rooms/{roomId}/reservations")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@CurrentUserId Long userId,
        @PathVariable("roomId") Long roomId,
        @Valid @RequestBody ReservationReq reservationReq) {
        reservationService.createReservation(reservationReq, userId, roomId);
    }

    /**
     * 예약 취소하기.
     *
     * @param userId        유저 ID
     * @param reservationId 예약 ID
     * @since 1.0.0
     */
    @DeleteMapping("/{studyCafeId}/rooms/{roomId}/reservations/{reservationId}")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@CurrentUserId Long userId,
        @PathVariable("reservationId") Long reservationId) {
        reservationService.cancelReservation(userId, reservationId);
    }

    /**
     * 리뷰 목록 조회하기.
     *
     * @param userId      유저 ID - @BlackCheck 용도
     * @param studyCafeId 스터디카페 ID
     * @param page        페이지 번호
     * @since 1.0.0
     */
    @GetMapping("/{studyCafeId}/reviews")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public PageInfo getReviews(@CurrentUserId Long userId,
        @PathVariable("studyCafeId") Long studyCafeId, @RequestParam("page") Integer page) {
        SearchOption searchOption = SearchOption.builder().page(page).build();
        return reviewService.getReviews(studyCafeId, searchOption);
    }

    /**
     * 리뷰 등록하기.
     *
     * @param userId      유저 ID
     * @param studyCafeId 스터디카페 ID
     * @param reviewReq   리뷰 DTO
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
     * @param userId      유저 ID
     * @param studyCafeId 스터디카페 ID - @BlackCheck 용도
     * @param reviewId    리뷰 ID
     * @param reviewReq   리뷰 DTO
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
     * @param userId      유저 ID
     * @param studyCafeId 스터디카페 ID - @BlackCheck 용도
     * @param reviewId    리뷰 ID
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
