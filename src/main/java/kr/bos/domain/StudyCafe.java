package kr.bos.domain;

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

    private Long id;
    private Long userId;
    private String title;
    private String address;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Room> rooms;
}
