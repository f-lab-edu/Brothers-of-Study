package kr.bos.model.domain;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Review Model.
 *
 * @since 1.0.0
 */
@Builder
public class Review {

    private Long id;
    private Long userId;
    private Long studyCafeId;
    private String description;
    private Float score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
