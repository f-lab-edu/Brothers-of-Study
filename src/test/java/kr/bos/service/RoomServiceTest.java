package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.bos.exception.DuplicatedException;
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
    public void createRoomTestWhenSuccess() {
        when(roomMapper.isExistsRoomNumber(1, 1L)).thenReturn(false);
        roomService.createRoom(roomReq, 1L);
        verify(roomMapper).insertRoom(any(Room.class));
        verify(roomMapper).insertRoomLock(any());
    }

    @Test
    @DisplayName("방 생성에 실패합니다. :중복된 방 번호.")
    public void createRoomTestWhenFail() {
        when(roomMapper.isExistsRoomNumber(1,  1L)).thenReturn(true);
        assertThrows(DuplicatedException.class,
            () -> roomService.createRoom(roomReq, 1L));
    }

    @Test
    @DisplayName("방 수정에 성공합니다.")
    public void updateRoomTestWhenSuccess() {
        when(roomMapper.isExistsRoomNumber(anyInt(), anyLong())).thenReturn(false);
        roomService.updateRoom(roomReq, 1L, 1L);
        verify(roomMapper).updateRoom(any(Room.class));
    }

    @Test
    @DisplayName("방 수정에 실패합니다. :중복된 방 번호")
    public void updateRoomTestWhenFail() {
        when(roomMapper.isExistsRoomNumber(anyInt(), anyLong())).thenReturn(true);
        assertThrows(DuplicatedException.class,
            () -> roomService.updateRoom(roomReq, 1L, 1L));
    }

    @Test
    @DisplayName("방 삭제에 성공합니다.")
    public void deleteRoomTestWhenSuccess() {
        when(reservationMapper.isExistsNowReservationByRoomId(anyLong())).thenReturn(false);
        roomService.deleteRoom(anyLong());
        verify(reservationMapper).isExistsNowReservationByRoomId(anyLong());
        verify(roomMapper).deleteRoom(anyLong());
    }

    @Test
    @DisplayName("방 삭제에 실패합니다. :등록된 예약이 존재합니다.")
    public void deleteRoomTestWhenFail() {
        when(reservationMapper.isExistsNowReservationByRoomId(anyLong())).thenReturn(true);
        assertThrows(DuplicatedException.class,
            () -> roomService.deleteRoom(anyLong()));
    }

}