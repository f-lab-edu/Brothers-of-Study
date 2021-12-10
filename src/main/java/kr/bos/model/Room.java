package kr.bos.model;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Room Model.
 *
 * @since 1.0.0
 */
@Builder
public class Room {

    private Long id;
    private Long studyCafeId;
    private Integer number;
    private Integer capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
