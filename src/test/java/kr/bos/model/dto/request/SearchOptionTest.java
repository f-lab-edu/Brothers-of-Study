package kr.bos.model.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SearchOptionTest {

    @Test
    @DisplayName("SearchOption 생성에 성공합니다.")
    public void createSearchOptionTestWhenSucces1s() {
        SearchOption searchOption = SearchOption.builder().page(1).build();
        assertEquals(searchOption.getOffset(), 0);

        searchOption = SearchOption.builder().page(5).build();
        assertEquals(searchOption.getOffset(), 40);

        searchOption = SearchOption.builder().page(10).build();
        assertEquals(searchOption.getOffset(), 90);

        searchOption = SearchOption.builder()
            .keyword("keyword")
            .isBookmark(null)
            .isMyStudyCafe(null)
            .order(null)
            .userId(1L)
            .build();

        assertEquals(searchOption.getKeyword(), "keyword");
        assertEquals(searchOption.getIsMyStudyCafe(), false);
        assertEquals(searchOption.getIsBookmark(), false);
        assertEquals(searchOption.getOrder(), OrderOption.RECENTLY);
        assertEquals(searchOption.getUserId(), 1L);
        assertNull(searchOption.getCacheKey());

        searchOption = SearchOption.builder()
            .isMyStudyCafe(true)
            .isBookmark(true)
            .order("SCORE")
            .build();

        assertEquals(searchOption.getIsMyStudyCafe(), true);
        assertEquals(searchOption.getIsBookmark(), true);
        assertEquals(searchOption.getOrder(), OrderOption.SCORE);
        assertNotNull(searchOption.getCacheKey());

        searchOption = SearchOption.builder()
            .order("NAME")
            .isMyStudyCafe(null)
            .isBookmark(true)
            .build();

        assertEquals(searchOption.getOrder(), OrderOption.NAME);
        assertNotNull(searchOption.getCacheKey());

        searchOption = SearchOption.builder()
            .isMyStudyCafe(null)
            .isBookmark(true)
            .build();

        assertNotNull(searchOption.getCacheKey());
    }

    @Test
    @DisplayName("SearchOption 생성에 성공합니다. :페이지 번호 입력하지 않는 경우.")
    public void createSearchOptionTestWhenSuccess2() {
        SearchOption searchOption = SearchOption.builder()
            .isBookmark(null)
            .isMyStudyCafe(null)
            .page(null)
            .build();

        assertEquals(searchOption.getOffset(), 0);
    }

    @Test
    @DisplayName("SearchOption 생성에 실패했습니다. :잘못된 페이지 넘버.")
    public void createSearchOptionTestWhenFail() {
        assertThrows(IllegalArgumentException.class,
            () -> SearchOption.builder().page(0).build());
        assertThrows(IllegalArgumentException.class,
            () -> SearchOption.builder().page(-10).build());
    }
}