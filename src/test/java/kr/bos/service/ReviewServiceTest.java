package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import kr.bos.exception.NotFoundException;
import kr.bos.mapper.ReviewMapper;
import kr.bos.model.domain.Review;
import kr.bos.model.dto.request.ReviewReq;
import kr.bos.model.dto.request.SearchOption;
import kr.bos.model.dto.response.ReviewRes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewMapper reviewMapper;

    Review review;
    ReviewReq reviewReq;

    @BeforeEach
    public void beforeEach() {
        review = Review.builder().build();
        reviewReq = new ReviewReq();
    }

    @Test
    @DisplayName("리뷰 목록 조회에 성공합니다.")
    public void getReviewsWhenSuccess() {
        SearchOption searchOption = SearchOption.builder().page(1).build();
        List<ReviewRes> reviewResList = new ArrayList<>();

        when(reviewMapper.selectReviewsCountByStudyCafeId(2L)).thenReturn(100L);
        when(reviewMapper.selectReviewsByStudyCafeId(2L, searchOption)
        ).thenReturn(reviewResList);

        var result = reviewService.getReviews(2L, searchOption);
        assertEquals(result.getTotalCount(), 100L);
        assertEquals(result.getList(), reviewResList);
        verify(reviewMapper).selectReviewsByStudyCafeId(2L, searchOption);
    }

    @Test
    @DisplayName("리뷰 생성에 성공합니다.")
    public void createReviewWhenSuccess() {
        reviewService.createReview(reviewReq, 1L, 2L);
        verify(reviewMapper).insertReview(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 업데이트에 성공합니다.")
    public void updateReviewWhenSuccess() {
        when(reviewMapper.updateReview(any(Review.class))).thenReturn(1);
        reviewService.updateReview(reviewReq, 1L, 2L);
        verify(reviewMapper).updateReview(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 업데이트에 실패합니다. :리뷰 작성자가 아닌 경우.")
    public void updateReviewWhenFail() {
        when(reviewMapper.updateReview(any(Review.class))).thenReturn(0);
        assertThrows(NotFoundException.class,
            () -> reviewService.updateReview(reviewReq, 1L, 2L));
        verify(reviewMapper).updateReview(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 삭제에 성공합니다.")
    public void deleteReviewWhenSuccess() {
        when(reviewMapper.deleteReview(1L, 2L)).thenReturn(1);
        reviewService.deleteReview(1L, 2L);
        verify(reviewMapper).deleteReview(1L, 2L);
    }

    @Test
    @DisplayName("리뷰 삭제에 실패합니다. :리뷰 작성자가 아닌 경우.")
    public void deleteReviewWhenFail() {
        when(reviewMapper.deleteReview(1L, 2L)).thenReturn(0);
        assertThrows(NotFoundException.class,
            () -> reviewService.deleteReview(1L, 2L));
        verify(reviewMapper).deleteReview(1L, 2L);
    }
}