package kr.bos.model.dto.request;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Room Request Dto.
 *
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "number")
public class RoomReq {

    private Long studyCafeId;

    @Min(1)
    private Integer number;

    @Min(1)
    private Integer capacity;
}
