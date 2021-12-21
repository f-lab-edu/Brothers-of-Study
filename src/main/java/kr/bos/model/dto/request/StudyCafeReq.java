package kr.bos.model.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * StudyCafe Request Dto.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyCafeReq {

    @NotBlank
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String address;

    private String thumbnail;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<RoomReq> rooms;
}
