package kr.bos.dto;

import java.util.Objects;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * RoomDto.
 *
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {

    private Long id;
    private Long studyCafeId;

    @Min(1)
    private Integer number;

    @Min(1)
    private Integer capacity;

    @Override
    public boolean equals(Object o) {
        return number.equals(((RoomDto) o).number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
