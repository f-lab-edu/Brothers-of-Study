package kr.bos.model;

import java.util.List;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * StudyCafe Model.
 *
 * @since 1.0.0
 */
@Builder
@Getter
public class StudyCafe {

    Long id;
    Long userId;
    String title;
    String address;
    String thumbnail;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    List<Room> rooms;
}
