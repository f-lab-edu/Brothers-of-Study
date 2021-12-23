package kr.bos.utils;

import java.util.HashMap;
import java.util.Map;
import kr.bos.model.dto.response.StudyCafeRes;

/**
 * 스터디 카페 필터링을 위한 클래스.
 *
 * @since 1.0.0
 */
public class StudyCafeListFilter {

    /**
     * 필터링 키워드를 기준으로, 필터링을 위한 메서드.
     *
     * @since 1.0.0
     */
    public static boolean filter(StudyCafeRes studyCafe, String filterKeyword, String regionName) {
        Map<String, Boolean> filterMapper = new HashMap<>();

        filterMapper.put("bookMarked", studyCafe.getBookMarked() > 0);
        filterMapper.put("region", studyCafe.getAddress().contains(regionName));
        filterMapper.put("roomCount", studyCafe.getEmptyRoomCount() > 0);

        return filterMapper.get(filterKeyword);
    }
}
