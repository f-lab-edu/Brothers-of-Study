package kr.bos.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.session.RowBounds;

/**
 * SearchOption.
 * <br>
 * 검색 옵션을 위한 클래스.
 *
 * @since 1.0.0
 */
@Builder
@Getter
public class SearchOption {

    private Integer offset;
    private Integer limit;


    public static SearchOptionBuilder builder() {
        return new SearchOptionBuilder();
    }

    /**
     * SearchOptionBuilder.
     *
     * @since 1.0.0
     */
    public static class SearchOptionBuilder {

        private static final int DEFAULT_LIMIT = 10;

        private Integer offset;
        private Integer limit;

        /**
         * page 번호를 입력받아 {@link RowBounds} 를 생성. LIMIT의 기본 값은 10.
         * <br>
         * 페이지 번호를 입력하지 않는 경우 첫 페이지로 조회. 0 이하의 값이 들어왔을 경우 예외 발생.
         *
         * @since 1.0.0
         */
        public SearchOptionBuilder page(Integer page) {
            if (page == null) {
                page = 1;
            }

            if (page <= 0) {
                throw new IllegalArgumentException("Page number must be greater than zero");
            }

            this.offset = (page - 1) * DEFAULT_LIMIT;
            this.limit = DEFAULT_LIMIT;
            return this;
        }

        public SearchOption build() {
            return new SearchOption(offset, limit);
        }
    }
}
