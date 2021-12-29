package kr.bos.model.dto.request;

import lombok.Getter;

/**
 * PageOption.
 * <br>
 * 생성자로 페이지 번호, 페이지 사이즈 입력을 받아 limit, offset 변환.
 *
 * @since 1.0.0
 */
@Getter
public class PageOption {

    private final Integer limit;
    private final Integer offset;

    public PageOption(Integer page, Integer size) {
        this.limit = (page - 1) * size;
        this.offset = size;
    }
}
