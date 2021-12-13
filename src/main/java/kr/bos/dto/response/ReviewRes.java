package kr.bos.dto.response;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * Review Response Dto.
 *
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRes {

    @NotBlank
    private Long id;

    @NotBlank
    private String userName;

    @NotBlank
    private String description;

    @NotBlank
    @Range(min = 0, max = 5)
    private Float score;

    private LocalDateTime createdAt;
}
