package kr.bos.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * StudyCafe Domain Object.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StudyCafe {

    private Long id;

    private Long userId;

    private String title;

    private String address;

    private String thumbnail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
