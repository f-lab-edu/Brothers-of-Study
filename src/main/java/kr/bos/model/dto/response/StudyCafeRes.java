package kr.bos.model.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * StudyCafe Response Dto.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StudyCafeRes {

    private Long id;
    private String title;
    private String address;
    private Float reviewAverage;
}
