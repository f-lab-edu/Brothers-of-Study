package kr.bos.mapper;

import java.util.List;
import kr.bos.model.domain.Review;
import kr.bos.model.dto.request.ReviewReq;
import kr.bos.model.dto.response.ReviewRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Review Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface ReviewMapper {

    List<ReviewRes> selectReviewsStudyCafeId(Long studyCafeId);

    void insertReview(Review review);

    int updateReview(Review review);

    int deleteReview(@Param("userId") Long userId, @Param("reviewId") Long reviewId);
}
