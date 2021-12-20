package kr.bos.model.domain;

import java.util.List;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * StudyCafe Model.
 *
 * @since 1.0.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
