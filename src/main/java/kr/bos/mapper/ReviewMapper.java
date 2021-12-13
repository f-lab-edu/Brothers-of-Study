package kr.bos.mapper;

import java.util.List;
import kr.bos.dto.request.ReviewReq;
import kr.bos.dto.response.ReviewRes;
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

    void insertReview(@Param("reviewRequest") ReviewReq reviewReq,
        @Param("userId") Long userId,
        @Param("reviewId") Long reviewId);

    int deleteReview(@Param("userId") Long userId, @Param("reviewId") Long reviewId);

    int updateReview(@Param("reviewRequest") ReviewReq reviewReq,
        @Param("userId") Long userId,
        @Param("reviewId") Long reviewId);
}
