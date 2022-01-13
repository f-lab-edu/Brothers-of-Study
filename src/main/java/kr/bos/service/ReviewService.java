package kr.bos.service;

import java.util.List;
import kr.bos.exception.NotFoundException;
import kr.bos.mapper.ReviewMapper;
import kr.bos.model.domain.Review;
import kr.bos.model.dto.request.ReviewReq;
import kr.bos.model.dto.request.SearchOption;
import kr.bos.model.dto.response.PageInfo;
import kr.bos.model.dto.response.ReviewRes;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param studyCafeId  스터디카페 ID
     * @param searchOption 페이지 Option
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public PageInfo<ReviewRes> getReviews(Long studyCafeId, SearchOption searchOption) {
        List<ReviewRes> reviews = reviewMapper.selectReviewsByStudyCafeId(studyCafeId,
            searchOption);
        Long totalCount = reviewMapper.selectReviewsCountByStudyCafeId(studyCafeId);
        return new PageInfo<>(totalCount, reviews);
    }

    /**
     * 리뷰 생성하기.
     *
     * @param reviewReq   리뷰 Request DTO
     * @param userId      유저 ID
     * @param studyCafeId 스터디카페 ID
     * @since 1.0.0
     */
    @CacheEvict(value = "studyCafes", allEntries = true)
    public void createReview(ReviewReq reviewReq, Long userId, Long studyCafeId) {
        Review review = Review.builder()
            .userId(userId)
            .studyCafeId(studyCafeId)
            .description(reviewReq.getDescription())
            .score(reviewReq.getScore())
            .build();

        reviewMapper.insertReview(review);
    }

    /**
     * 리뷰 업데이트.
     *
     * @param reviewReq 리뷰 Request DTO
     * @param userId    유저 ID
     * @param reviewId  리뷰 ID
     * @since 1.0.0
     */
    @CacheEvict(value = "studyCafes", allEntries = true)
    public void updateReview(ReviewReq reviewReq, Long userId, Long reviewId) {
        Review review = Review.builder()
            .id(reviewId)
            .userId(userId)
            .description(reviewReq.getDescription())
            .score(reviewReq.getScore())
            .build();

        int updateCount = reviewMapper.updateReview(review);
        if (updateCount == 0) {
            throw new NotFoundException("Select not found review");
        }
    }

    /**
     * 리뷰 삭제하기.
     *
     * @param userId   유저 ID
     * @param reviewId 리뷰 ID
     * @since 1.0.0
     */
    @CacheEvict(value = "studyCafes", allEntries = true)
    public void deleteReview(Long userId, Long reviewId) {
        int deleteCount = reviewMapper.deleteReview(userId, reviewId);
        if (deleteCount == 0) {
            throw new NotFoundException("Select not found review");
        }
    }
}
