package kr.bos.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.bos.exception.DuplicatedRoomNumberException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * StudyCafe Dto.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyCafeDto {

    @NotBlank
    private String title;

    @NotBlank
    private String address;

    private String thumbnail;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<RoomDto> rooms;

    /**
     * Rooms 입력시 중복된 방 번호가 있는지 체크. 중복된 방이 있다면 DuplicatedRoomNumberException 발생.
     *
     * @since 1.0.0
     */
    public void setRooms(List<RoomDto> rooms) {
        if (rooms.size() != rooms.stream().distinct().count()) {
            throw new DuplicatedRoomNumberException();
        }

        this.rooms = rooms;
    }
}
