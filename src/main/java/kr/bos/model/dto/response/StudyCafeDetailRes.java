package kr.bos.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * StudyCafe Detail Response Dto.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudyCafeDetailRes {
    private Long id;
    private String title;
    private String address;
    private String thumbnail;

    private Long useCount;
    private Long bookmarkCount;
    private Float reviewAverage;

    private List<RoomUseInfoRes> rooms;
}