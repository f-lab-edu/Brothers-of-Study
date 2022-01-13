package kr.bos.model.dto.request;

import java.util.Objects;
import lombok.Getter;
import org.apache.ibatis.session.RowBounds;

/**
 * SearchOption.
 * <br>
 * 검색 옵션을 위한 클래스.
 *
 * @since 1.0.0
 */
@Getter
public class SearchOption {

    private final String keyword;
    private final Boolean isMyStudyCafe;
    private final Boolean isBookmark;
    private final OrderOption order;
    private final Integer offset;
    private final Integer limit;
    private final Long userId;

    private Integer cacheKey;

    /**
     * SearchOptionBuilder를 위한 생성자.
     *
     * @param keyword       검색어
     * @param isMyStudyCafe 내 스터디 카페만 검색할 지
     * @param isBookmark    북마크한 스터디 카페만 검색할 지
     * @param order         정렬 옵션
     * @param offset        OFFSET
     * @param limit         LIMIT
     * @param userId        유저 ID.
     * @since 1.0.0
     */
    private SearchOption(String keyword, Boolean isMyStudyCafe, Boolean isBookmark,
        OrderOption order, Integer offset, Integer limit, Long userId) {
        this.keyword = keyword;
        this.isMyStudyCafe = isMyStudyCafe;
        this.isBookmark = isBookmark;
        this.order = order;
        this.offset = offset;
        this.limit = limit;
        this.userId = userId;

        if (isMyStudyCafe || isBookmark) {
            this.cacheKey = Objects.hash(keyword, order, offset, limit);
        }
    }

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

        private String keyword;
        private Boolean isMyStudyCafe = false;
        private Boolean isBookmark = false;
        private OrderOption order;
        private Integer offset;
        private Integer limit;
        private Long userId;

        /**
         * 검색 키워드 지정.
         *
         * @since 1.0.0
         */
        public SearchOptionBuilder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        /**
         * 내 스터디 카페만 조회.
         *
         * @since 1.0.0
         */
        public SearchOptionBuilder isMyStudyCafe(Boolean isMyStudyCafe) {
            if (isMyStudyCafe == null) {
                this.isMyStudyCafe = false;
                return this;
            }

            this.isMyStudyCafe = isMyStudyCafe;
            return this;
        }

        /**
         * 북마크 등록한 스터디 카페만 조회.
         *
         * @since 1.0.0
         */
        public SearchOptionBuilder isBookmark(Boolean isBookmark) {
            if (isBookmark == null) {
                this.isBookmark = false;
                return this;
            }

            this.isBookmark = isBookmark;
            return this;
        }

        /**
         * 검색 정렬 옵션 {@link OrderOption} 지정.
         *
         * @since 1.0.0
         */
        public SearchOptionBuilder order(String order) {
            if (order == null) {
                this.order = OrderOption.RECENTLY;
                return this;
            }

            this.order = OrderOption.valueOf(order);
            return this;
        }

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

        /**
         * isMyStudyCafe, isBookmark 필터 처리를 위한 userId.
         *
         * @since 1.0.0
         */
        public SearchOptionBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public SearchOption build() {
            return new SearchOption(keyword, isMyStudyCafe, isBookmark, order, offset, limit,
                userId);
        }
    }
}
