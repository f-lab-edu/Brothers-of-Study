package kr.bos.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Room 사용 정보 Response Dto.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomUseInfoRes {

    private Long id;
    private Integer number;
    private Integer capacity;
    private Boolean currentUse;
}
