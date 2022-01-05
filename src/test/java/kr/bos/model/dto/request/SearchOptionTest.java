package kr.bos.model.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SearchOptionTest {

    @Test
    @DisplayName("SearchOption 생성에 성공합니다. :페이지 번호 정상 입력.")
    public void createSearchOptionTestWhenSucces1s() {
        SearchOption searchOption = SearchOption.builder().page(1).build();
        assertEquals(searchOption.getOffset(), 0);

        searchOption = SearchOption.builder().page(5).build();
        assertEquals(searchOption.getOffset(), 40);

        searchOption = SearchOption.builder().page(10).build();
        assertEquals(searchOption.getOffset(), 90);
    }

    @Test
    @DisplayName("SearchOption 생성에 성공합니다. :페이지 번호 입력하지 않는 경우.")
    public void createSearchOptionTestWhenSuccess2() {
        SearchOption searchOption = SearchOption.builder().page(null).build();
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