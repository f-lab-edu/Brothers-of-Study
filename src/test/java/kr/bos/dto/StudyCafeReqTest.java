package kr.bos.dto;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import kr.bos.exception.DuplicatedRoomNumberException;
import kr.bos.model.dto.request.RoomReq;
import kr.bos.model.dto.request.StudyCafeReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeReqTest {

    List<RoomReq> rooms;
    StudyCafeReq studyCafeReq;

    @BeforeEach
    public void beforeEach() {
        studyCafeReq = new StudyCafeReq();

        rooms = new ArrayList<>();
        RoomReq room = RoomReq.builder()
            .number(1)
            .capacity(1)
            .build();

        rooms.add(room);
    }

    @Test
    @DisplayName("StudyCafeDto 생성에 성공합니다.")
    public void createStudyCafeDtoTestWhenSuccess() {
        RoomReq newRoom = RoomReq.builder()
            .number(rooms.get(0).getNumber() + 1)
            .capacity(1)
            .build();

        rooms.add(newRoom);
        studyCafeReq.setRooms(rooms);
    }

    @Test
    @DisplayName("StudyCafeDto 생성에 실패합니다. :중복된 방 번호")
    public void createStudyCafeDtoTestWhenFail() {
        RoomReq duplicatedRoom = RoomReq.builder()
            .number(rooms.get(0).getNumber())
            .capacity(1)
            .build();

        rooms.add(duplicatedRoom);
        assertThrows(DuplicatedRoomNumberException.class, () -> studyCafeReq.setRooms(rooms));
    }
}