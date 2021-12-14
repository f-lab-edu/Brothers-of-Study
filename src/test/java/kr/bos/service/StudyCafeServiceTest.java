package kr.bos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import kr.bos.exception.BookmarkNotFoundException;
import kr.bos.exception.DuplicatedBookmarkException;
import kr.bos.mapper.RoomMapper;
import kr.bos.mapper.StudyCafeMapper;
import kr.bos.model.domain.StudyCafe;
import kr.bos.model.dto.request.RoomReq;
import kr.bos.model.dto.request.StudyCafeReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

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

    @Test
    @DisplayName("북마크 등록에 성공합니다.")
    public void registerBookmarkTestWhenSuccess() {
        studyCafeService.registerBookmark(1L, 1L);
        verify(studyCafeMapper).insertBookmark(1L, 1L);
    }

    @Test
    @DisplayName("북마크 등록에 실패합니다. :이미 등록된 북마크")
    public void registerBookmarkTestWhenFail() {
        when(studyCafeMapper.insertBookmark(1L, 1L)).thenThrow(DuplicateKeyException.class);
        assertThrows(DuplicatedBookmarkException.class,
            () -> studyCafeService.registerBookmark(1L, 1L));
    }

    @Test
    @DisplayName("북마크 취소에 성공합니다.")
    public void cancelBookmarkTestWhenSuccess() {
        when(studyCafeMapper.deleteBookmark(1L, 1L)).thenReturn(1);
        studyCafeService.cancelBookmark(1L, 1L);
        verify(studyCafeMapper).deleteBookmark(1L, 1L);
    }

    @Test
    @DisplayName("북마크 취소에 실패합니다. :등록하지 않은 북마크")
    public void cancelBookmarkTestWhenFail() {
        when(studyCafeMapper.deleteBookmark(1L, 1L)).thenReturn(0);
        assertThrows(BookmarkNotFoundException.class,
            () -> studyCafeService.cancelBookmark(1L, 1L));
    }
}