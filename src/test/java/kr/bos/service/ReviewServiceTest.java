package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.bos.dto.request.ReviewRequest;
import kr.bos.exception.AccessDeniedException;
import kr.bos.mapper.ReviewMapper;
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

    ReviewRequest reviewRequest;

    @BeforeEach
    public void beforeEach() {
        reviewRequest = new ReviewRequest();
    }

    @Test
    @DisplayName("리뷰 목록 조회에 성공합니다.")
    public void getReviewsWhenSuccess() {
        reviewService.getReviews(2L);
        verify(reviewMapper).selectReviewsStudyCafeId(2L);
    }

    @Test
    @DisplayName("리뷰 생성에 성공합니다.")
    public void createReviewWhenSuccess() {
        reviewService.createReview(reviewRequest, 1L, 2L);
        verify(reviewMapper).insertReview(reviewRequest, 1L, 2L);
    }

    @Test
    @DisplayName("리뷰 업데이트에 성공합니다.")
    public void updateReviewWhenSuccess() {
        when(reviewMapper.updateReview(reviewRequest, 1L, 2L)).thenReturn(1);
        reviewService.updateReview(reviewRequest, 1L, 2L);
        verify(reviewMapper).updateReview(reviewRequest, 1L, 2L);
    }

    @Test
    @DisplayName("리뷰 업데이트에 실패합니다. :리뷰 작성자가 아닌 경우.")
    public void updateReviewWhenFail() {
        when(reviewMapper.updateReview(reviewRequest, 1L, 2L)).thenReturn(0);
        assertThrows(AccessDeniedException.class,
            () -> reviewService.updateReview(reviewRequest, 1L, 2L));
        verify(reviewMapper).updateReview(reviewRequest, 1L, 2L);
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
        assertThrows(AccessDeniedException.class,
            () -> reviewService.deleteReview(1L, 2L));
        verify(reviewMapper).deleteReview(1L, 2L);
    }
}