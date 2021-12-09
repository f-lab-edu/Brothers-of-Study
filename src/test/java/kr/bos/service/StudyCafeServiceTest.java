package kr.bos.service;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import kr.bos.dto.RoomDto;
import kr.bos.dto.StudyCafeDto;
import kr.bos.mapper.RoomMapper;
import kr.bos.mapper.StudyCafeMapper;
import org.junit.jupiter.api.BeforeEach;
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

    StudyCafeDto studyCafeDto;

    @Test
    @DisplayName("스터디 카페 등록에 성공합니다.")
    public void registerStudyCafeTestWhenSuccess() {
        List<RoomDto> rooms = new ArrayList<>();
        RoomDto room1 = RoomDto.builder()
            .number(1)
            .capacity(1)
            .build();

        RoomDto room2 = RoomDto.builder()
            .number(2)
            .capacity(2)
            .build();

        rooms.add(room1);
        rooms.add(room2);

        studyCafeDto = StudyCafeDto.builder()
            .id(1L)
            .title("title")
            .address("address")
            .thumbnail("thumbnail")
            .rooms(rooms)
            .build();

        studyCafeService.registerStudyCafe(1L, studyCafeDto);
        verify(studyCafeMapper).insertStudyCafe(studyCafeDto);
        verify(roomMapper).insertRooms(studyCafeDto);
    }
}