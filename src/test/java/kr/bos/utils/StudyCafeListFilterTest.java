package kr.bos.utils;

import static org.assertj.core.api.Assertions.assertThat;

import kr.bos.model.dto.response.StudyCafeRes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeListFilterTest {

    StudyCafeRes studyCafeRes;
    StudyCafeListFilter studyCafeListFilter;

    @BeforeEach
    public void beforeEach() {
        studyCafeListFilter = new StudyCafeListFilter();
        studyCafeRes = StudyCafeRes.builder()
            .bookMarked(1)
            .address("서울시 강남구")
            .emptyRoomCount(5)
            .build();
    }

    @Test
    @DisplayName("북마크 여부로 필터링 할 때,"
        + "StudyCafeRes 객체가 북마크 되어있으면 true, 그렇지 않으면 false 를 반환합니다.")
    public void checkBookMarkedFilter() {
        assertThat(StudyCafeListFilter.filter(studyCafeRes, "bookMarked", ""))
            .isTrue();
        assertThat(StudyCafeListFilter.filter(
            StudyCafeRes.builder().bookMarked(0).address("").emptyRoomCount(0).build(),
            "bookMarked", ""))
            .isFalse();
    }

    @Test
    @DisplayName("지역을 기준으로 필터링 할 때,"
        + "StudyCafeRes 객체의 주소에 해당 지역 키워드가 존재할 시 true 를 반환합니다.")
    public void checkRegionFilter() {
        assertThat(StudyCafeListFilter.filter(studyCafeRes, "region", "강남"))
            .isTrue();
        assertThat(StudyCafeListFilter.filter(studyCafeRes, "region", "분당"))
            .isFalse();
    }

    @Test
    @DisplayName("빈 방 여부를 기준으로 필터링 할 때,"
        + "StudyCafeRes 객체가 1개 이상의 빈 방을 가지고 있다면 true, 그렇지 않다면 false 를 반환합니다.")
    public void checkEmptyRoomFilter() {
        assertThat(StudyCafeListFilter.filter(studyCafeRes, "roomCount", ""))
            .isTrue();
        assertThat(StudyCafeListFilter.filter(
            StudyCafeRes.builder().bookMarked(0).address("").emptyRoomCount(0).build(),
            "roomCount", ""))
            .isFalse();
    }
}
