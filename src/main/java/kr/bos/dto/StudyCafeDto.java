package kr.bos.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kr.bos.model.domain.StudyCafe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * StudyCafe DTO.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StudyCafeDto {

    private Long id;

    private Long userId;

    private String title;

    private String address;

    private String thumbnail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    /**
      * Transfer Method : StudyCafe Entity(Domain Object) to StudyCafe DTO.
      *
      * @since 1.0.0
     */
    public static StudyCafeDto toStudyCafeDto(StudyCafe studyCafe) {
        return StudyCafeDto.builder()
            .id(studyCafe.getId())
            .userId(studyCafe.getUserId())
            .title(studyCafe.getTitle())
            .address(studyCafe.getAddress())
            .thumbnail(studyCafe.getThumbnail())
            .createdAt(studyCafe.getCreatedAt())
            .updatedAt(studyCafe.getUpdatedAt())
            .build();
    }

    /**
     * Transfer Method : StudyCafe Entity List(Domain Object) to StudyCafe DTO List.

     * @since 1.0.0
     */
    public static List<StudyCafeDto> toStudyCafeDtos(List<StudyCafe> studyCafes) {
        return studyCafes.stream()
            .map(StudyCafeDto::toStudyCafeDto)
            .collect(Collectors.toList());
    }
}
