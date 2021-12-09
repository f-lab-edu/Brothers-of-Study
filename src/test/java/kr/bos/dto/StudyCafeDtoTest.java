package kr.bos.dto;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import kr.bos.exception.DuplicatedRoomNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeDtoTest {

    List<RoomDto> rooms;
    StudyCafeDto studyCafeDto;

    @BeforeEach
    public void beforeEach() {
        studyCafeDto = new StudyCafeDto();

        rooms = new ArrayList<>();
        RoomDto room = RoomDto.builder()
            .number(1)
            .capacity(1)
            .build();

        rooms.add(room);
    }

    @Test
    @DisplayName("StudyCafeDto 생성에 성공합니다.")
    public void createStudyCafeDtoTestWhenSuccess() {
        RoomDto newRoom = RoomDto.builder()
            .number(rooms.get(0).getNumber() + 1)
            .capacity(1)
            .build();

        rooms.add(newRoom);
        studyCafeDto.setRooms(rooms);
    }

    @Test
    @DisplayName("StudyCafeDto 생성에 실패합니다. :중복된 방 번호")
    public void createStudyCafeDtoTestWhenFail() {
        RoomDto duplicatedRoom = RoomDto.builder()
            .number(rooms.get(0).getNumber())
            .capacity(1)
            .build();

        rooms.add(duplicatedRoom);
        assertThrows(DuplicatedRoomNumberException.class, () -> studyCafeDto.setRooms(rooms));
    }
}