package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.bos.exception.DuplicatedRoomNumberException;
import kr.bos.exception.ExistsTimeReservationException;
import kr.bos.mapper.ReservationMapper;
import kr.bos.mapper.RoomMapper;
import kr.bos.model.domain.Room;
import kr.bos.model.dto.request.RoomReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    RoomService roomService;

    @Mock
    RoomMapper roomMapper;

    @Mock
    ReservationMapper reservationMapper;

    RoomReq roomReq;

    @BeforeEach
    public void beforeEach() {
        roomReq = RoomReq.builder()
            .number(1)
            .capacity(1)
            .build();
    }

    @Test
    @DisplayName("방 생성에 성공합니다.")
    public void deleteRoomTestWhenSuccess() {
        roomService.createRoom(roomReq, anyLong());
        verify(roomMapper).insertRoom(any(Room.class));
    }

    @Test
    @DisplayName("방 생성에 실패합니다. :중복된 방 번호.")
    public void createRoomTestWhenSuccess() {
        when(roomMapper.insertRoom(any(Room.class))).thenThrow(DuplicateKeyException.class);
        assertThrows(DuplicatedRoomNumberException.class,
            () -> roomService.createRoom(roomReq, anyLong()));
    }

    @Test
    @DisplayName("방 수정에 성공합니다.")
    public void createRoomTestWhenFail() {
        roomService.updateRoom(roomReq, 1L, 1L);
        verify(roomMapper).updateRoom(any(Room.class));
    }

    @Test
    @DisplayName("방 수정에 실패합니다. :중복된 방 번호")
    public void updateRoomTestWhenSuccess() {
        when(roomMapper.updateRoom(any(Room.class))).thenThrow(DuplicateKeyException.class);
        assertThrows(DuplicatedRoomNumberException.class,
            () -> roomService.updateRoom(roomReq, 1L, 1L));
    }

    @Test
    @DisplayName("방 삭제에 성공합니다.")
    public void updateRoomTestWhenFail() {
        when(reservationMapper.isExistsNowReservationByRoomId(anyLong())).thenReturn(false);
        roomService.deleteRoom(anyLong());
        verify(reservationMapper).isExistsNowReservationByRoomId(anyLong());
        verify(roomMapper).deleteRoom(anyLong());
    }

    @Test
    @DisplayName("방 삭제에 실패합니다. :등록된 예약이 존재합니다.")
    public void deleteRoomTestWhenFail() {
        when(reservationMapper.isExistsNowReservationByRoomId(anyLong())).thenReturn(true);
        assertThrows(ExistsTimeReservationException.class,
            () -> roomService.deleteRoom(anyLong()));
    }

}