package kr.bos.mapper;

import java.util.List;
import kr.bos.model.domain.Review;
import kr.bos.model.dto.request.PageOption;
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

    Long selectReviewsCountByStudyCafeId(Long studyCafeId);

    List<ReviewRes> selectReviewsByStudyCafeId(@Param("studyCafeId") Long studyCafeId,
        @Param("pageOption") PageOption pageOption);

    void insertReview(Review review);

    int updateReview(Review review);

    int deleteReview(@Param("userId") Long userId, @Param("reviewId") Long reviewId);
}
