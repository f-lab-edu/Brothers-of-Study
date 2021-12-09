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

    Long id;
    Long studyCafeId;
    Integer number;
    Integer capacity;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
