package kr.bos.mapper;

import java.util.List;
import java.util.Map;
import kr.bos.dto.request.ReviewRequest;
import kr.bos.dto.response.ReviewResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Review Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface ReviewMapper {

    List<ReviewResponse> selectReviewsStudyCafeId(Long studyCafeId);

    void insertReview(@Param("reviewRequest") ReviewRequest reviewRequest,
        @Param("userId") Long userId,
        @Param("reviewId") Long reviewId);

    int deleteReview(@Param("userId") Long userId, @Param("reviewId") Long reviewId);

    int updateReview(@Param("reviewRequest") ReviewRequest reviewRequest,
        @Param("userId") Long userId,
        @Param("reviewId") Long reviewId);
}
