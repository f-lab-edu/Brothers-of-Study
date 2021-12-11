package kr.bos.model.domain;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Reservation Model.
 *
 * @since 1.0.0
 */
@Builder
public class Reservation {

    private Long userId;
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}