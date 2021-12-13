package kr.bos.service;

import java.util.List;
import kr.bos.dto.request.ReviewReq;
import kr.bos.dto.response.ReviewRes;
import kr.bos.exception.AccessDeniedException;
import kr.bos.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Review Service.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    /**
     * 리뷰 목록 조회하기.
     *
     * @since 1.0.0
     */
    public List<ReviewRes> getReviews(Long studyCafeId) {
        return reviewMapper.selectReviewsStudyCafeId(studyCafeId);
    }

    /**
     * 리뷰 생성하기.
     *
     * @since 1.0.0
     */
    public void createReview(ReviewReq reviewReq, Long userId, Long studyCafeId) {
        reviewMapper.insertReview(reviewReq, userId, studyCafeId);
    }

    /**
     * 리뷰 업데이트. 업데이트 실패시 AccessDeniedException 예외 발생.
     *
     * @since 1.0.0
     */
    public void updateReview(ReviewReq reviewReq, Long userId, Long reviewId) {
        int updateCount = reviewMapper.updateReview(reviewReq, userId, reviewId);
        if (updateCount == 0) {
            throw new AccessDeniedException();
        }
    }

    /**
     * 리뷰 삭제하기. 삭제 실패시 AccessDeniedException 예외 발생.
     *
     * @since 1.0.0
     */
    public void deleteReview(Long userId, Long reviewId) {
        int deleteCount = reviewMapper.deleteReview(userId, reviewId);
        if (deleteCount == 0) {
            throw new AccessDeniedException();
        }
    }
}