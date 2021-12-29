package kr.bos.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PageInfo.
 * <br>
 * 페이징 처리 후 담아서 보낼 DTO.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PageInfo<E> {

    private Long totalCount;
    private List<E> list;
}
