package kr.bos.model.dto.request;

/**
 * 검색에 사용할 정렬 옵션.
 *
 * @since 1.0.0
 */
public enum OrderOption {

    /**
     * 이름순으로 정렬되는 옵션.
     */
    NAME,

    /**
     * 최신순으로 정렬되는 옵션.
     */
    RECENTLY,

    /**
     * 리뷰 평균 순으로 정렬되는 옵션.
     */
    SCORE;
}
