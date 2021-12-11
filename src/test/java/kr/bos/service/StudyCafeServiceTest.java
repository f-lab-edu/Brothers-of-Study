package kr.bos.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import kr.bos.model.domain.StudyCafe;
import kr.bos.model.dto.request.RoomReq;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.mapper.RoomMapper;
import kr.bos.mapper.StudyCafeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyCafeServiceTest {

    @InjectMocks
    StudyCafeService studyCafeService;

    @Mock
    StudyCafeMapper studyCafeMapper;

    @Mock
    RoomMapper roomMapper;

    StudyCafeReq studyCafeReq;

    @Test
    @DisplayName("스터디 카페 등록에 성공합니다.")
    public void registerStudyCafeTestWhenSuccess() {
        List<RoomReq> rooms = new ArrayList<>();
        RoomReq room1 = RoomReq.builder()
            .number(1)
            .capacity(1)
            .build();

        RoomReq room2 = RoomReq.builder()
            .number(2)
            .capacity(2)
            .build();

        rooms.add(room1);
        rooms.add(room2);

        studyCafeReq = StudyCafeReq.builder()
            .title("title")
            .address("address")
            .thumbnail("thumbnail")
            .rooms(rooms)
            .build();

        studyCafeService.registerStudyCafe(1L, studyCafeReq);
        verify(studyCafeMapper).insertStudyCafe(any(StudyCafe.class));
        verify(roomMapper).insertRooms(any());
    }
}