package kr.bos.model.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.bos.exception.DuplicatedException;
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

    /**
     * Rooms 입력시 중복된 방 번호가 있는지 체크. 중복된 방이 있다면 DuplicatedRoomNumberException 발생.
     *
     * @since 1.0.0
     */
    public void setRooms(List<RoomReq> rooms) {
        if (rooms.size() != rooms.stream().distinct().count()) {
            throw new DuplicatedException("This room number already exists.");
        }

        this.rooms = rooms;
    }
}