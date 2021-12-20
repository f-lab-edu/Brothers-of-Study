package kr.bos.model.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * StudyCafeRes.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StudyCafeRes {

    private Long id;

    private Long userId;

    private String title;

    private String address;

    private String thumbnail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Double reviewAverage;

    private Integer bookMarked;

    private Integer emptyRoomCount;
}
