package kr.bos.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import kr.bos.exception.BookmarkNotFoundException;
import kr.bos.exception.DuplicatedBookmarkException;
import kr.bos.exception.StudyCafeNotFoundException;
import kr.bos.mapper.RoomMapper;
import kr.bos.mapper.StudyCafeMapper;
import kr.bos.model.domain.StudyCafe;
import kr.bos.model.dto.request.RoomReq;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.model.dto.response.StudyCafeRes;
import org.junit.jupiter.api.Assertions;
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

    public void giveStudyCafeExistCondition(boolean isStudyCafeExist) {
        StudyCafeRes studyCafe = StudyCafeRes.builder()
             .id(1L)
             .title("열공 스터디카페 분당점")
             .userId(100L)
             .address("성남시 분당구 장안로")
             .thumbnail("Thumbnail")
             .createdAt(LocalDateTime.now())
             .updatedAt(LocalDateTime.now())
             .build();
        Long studyCafeId = 1L;

        Optional<StudyCafeRes> studyCafeOptional =
            isStudyCafeExist ? Optional.of(StudyCafeRes.builder().build()) : Optional.empty();
        Optional<Long> studyCafeIdOptional =
            isStudyCafeExist ? Optional.of(studyCafeId) : Optional.empty();

        lenient().doReturn(studyCafeOptional).when(studyCafeMapper).getStudyCafeById(any());
        lenient().doReturn(studyCafeIdOptional).when(studyCafeMapper).getStudyCafeIdByName(any());
    }


    @Test
    @DisplayName("스터디 카페 이름 키워드가 주어질 시 해당 키워드를 갖는 스터디 카페 Response 리스트 객체를 받습니다.")
    public void findStudyCafesByKeyword() {
        // given
        final String name = "강남";
        final Long userId = 1L;
        final List<StudyCafeRes> studyCafeResList = Arrays.asList(
            StudyCafeRes.builder()
                .id(1L)
                .title("강남스터디카페")
                .address("강남")
                .thumbnail("")
                .reviewAverage(3.5)
                .bookMarked(1)
                .emptyRoomCount(10)
                .build()
            );
        given(studyCafeMapper.getStudyCafesByKeyword(userId, name)).willReturn(studyCafeResList);

        // when
        List<StudyCafeRes> responseResult = studyCafeService.findStudyCafesByKeyword(userId, name);

        // then
        assertThat(responseResult).hasSize(1);
        assertThat(responseResult.get(0)).isInstanceOf(StudyCafeRes.class);
    }

    @Test
    @DisplayName("스터디 카페 객체가 주어지면 해당 스터디 카페가 등록되어 있는지 확인할 수 있습니다.")
    public void checkStudyCafeExists() {
        // given
        StudyCafeReq studyCafe = StudyCafeReq.builder().id(1L).title("강남").build();

        given(studyCafeMapper.getStudyCafeIdByName(studyCafe.getTitle()))
            .willReturn(Optional.of(1L));
        given(studyCafeMapper.getStudyCafeById(studyCafe.getId()))
            .willReturn(Optional.of(StudyCafeRes.builder().build()));

        // then
        assertThat(studyCafeService.isStudyCafeExists(studyCafe)).isTrue();
    }

    @Test
    @DisplayName("스터디 카페의 id가 조회되지 않으면 해당 스터디 카페는 등록되지 않은 상태입니다.")
    public void checkStudyCafeExistsWithNoId() {
        // given
        StudyCafeReq studyCafe = StudyCafeReq.builder().id(1L).title("강남").build();

        lenient().doReturn(Optional.of(StudyCafeRes.builder().build()))
            .when(studyCafeMapper).getStudyCafeIdByName(studyCafe.getTitle());
        lenient().doReturn(Optional.empty())
            .when(studyCafeMapper).getStudyCafeById(studyCafe.getId());


        // then
        assertThat(studyCafeService.isStudyCafeExists(studyCafe)).isFalse();
    }

    @Test
    @DisplayName("스터디 카페의 이름이 조회 되지 않으면 해당 스터디 카페는 등록되지 않은 상태입니다.")
    public void checkStudyCafeExistsWithNoTitle() {
        // given
        StudyCafeReq studyCafe = StudyCafeReq.builder().id(1L).title("강남").build();

        lenient().doReturn(Optional.empty())
            .when(studyCafeMapper).getStudyCafeIdByName(studyCafe.getTitle());
        lenient().doReturn(Optional.of(1L))
            .when(studyCafeMapper).getStudyCafeById(studyCafe.getId());


        // then
        assertThat(studyCafeService.isStudyCafeExists(studyCafe)).isFalse();
    }

    @Test
    @DisplayName("등록된 스터디 카페의 ID가 주어질 시 해당 스터디 카페를 찾아 DTO 객체로 돌려줍니다.")
    public void findStudyCafeByValidId() {
        // given
        final Long id = 1L;
        given(studyCafeMapper.getStudyCafeById(id))
            .willReturn(Optional.of(StudyCafeRes.builder().id(id).build()));

        // when
        StudyCafeRes studyCafeDto = studyCafeService.findStudyCafeById(id);

        // then
        assertThat(studyCafeDto.getId()).isEqualTo(id);
    }


    @Test
    @DisplayName("등록되지 않은 스터디 카페의 ID가 주어질 시 스터디 카페를 찾지 못하고 에러를 출력합니다.")
    public void findStudyCafeByInvalidId() {
        // given
        final Long invalidId = 1L;
        given(studyCafeMapper.getStudyCafeById(invalidId)).willReturn(Optional.empty());

        // when
        RuntimeException runtimeException =
            Assertions.assertThrows(StudyCafeNotFoundException.class,
                () -> studyCafeService.findStudyCafeById(invalidId));

        // then
        assertThat(runtimeException.getMessage())
            .isEqualTo("There is no matching Study Cafe with study cafe id: 1");
    }

    @Test
    @DisplayName("등록된 스터디 카페의 이름이 주어질 시 해당 스터디 카페의 id를 받을 수 있습니다.")
    public void findStudyCafeIdByValidName() {
        // given
        final String validName = "강남";
        final Long id = 1L;
        given(studyCafeMapper.getStudyCafeIdByName(validName)).willReturn(Optional.of(id));

        // when
        Long resultId = studyCafeService.findStudyCafeIdByName(validName);

        // then
        assertThat(resultId).isEqualTo(id);
    }

    @Test
    @DisplayName("등록되지 않은 스터디 카페의 이름이 주어질 시 스터디 카페를 찾지 못하고 오류를 출력합니다.")
    public void findStudyCafeIdByInvalidName() {
        // given
        final String invalidName = "강남";
        given(studyCafeMapper.getStudyCafeIdByName(invalidName)).willReturn(Optional.empty());

        // when
        RuntimeException runtimeException =
            Assertions.assertThrows(StudyCafeNotFoundException.class,
                () -> studyCafeService.findStudyCafeIdByName(invalidName));

        // then
        assertThat(runtimeException.getMessage()).isEqualTo("Study Cafe: 강남 was not founded.");
    }


    @Test
    @DisplayName("스터디 카페가 존재할 시, 스터디 카페의 정보를 업데이트합니다.")
    public void updateValidStudyCafe() {
        //given
        StudyCafeReq studyCafe = StudyCafeReq.builder().build();
        giveStudyCafeExistCondition(true);
        given(studyCafeMapper.updateStudyCafe(studyCafe)).willReturn(1);

        //when
        studyCafeService.updateStudyCafe(studyCafe);

        //then
        verify(studyCafeMapper).updateStudyCafe(studyCafe);
    }

    @Test
    @DisplayName("존재하지 않는 스터디 카페는 업데이트할 수 없습니다.")
    public void updateInvalidStudyCafe() {
        //given
        StudyCafeReq studyCafe = StudyCafeReq.builder().build();
        giveStudyCafeExistCondition(false);

        //when
        RuntimeException runtimeException =
            Assertions.assertThrows(StudyCafeNotFoundException.class,
                () -> studyCafeService.updateStudyCafe(studyCafe));

        //then
        assertThat(runtimeException.getMessage()).isNotNull();
    }

    @Test
    @DisplayName("등록된 스터디 카페는 삭제할 수 있습니다.")
    public void deleteRegisteredStudyCafe() {
        //given
        StudyCafeReq studyCafe = StudyCafeReq.builder().build();
        giveStudyCafeExistCondition(true);
        given(studyCafeMapper.deleteStudyCafe(studyCafe.getId())).willReturn(1);

        //when
        studyCafeService.deleteStudyCafe(studyCafe);

        //then
        verify(studyCafeMapper).deleteStudyCafe(studyCafe.getId());
    }

    @Test
    @DisplayName("등록되지 않은 스터디 카페는 삭제할 수 없습니다.")
    public void disableDeleteRegisteredStudyCafe() {
        //given
        StudyCafeReq studyCafe = StudyCafeReq.builder().build();
        giveStudyCafeExistCondition(false);

        //when
        RuntimeException runtimeException =
            Assertions.assertThrows(StudyCafeNotFoundException.class,
                () -> studyCafeService.deleteStudyCafe(studyCafe));

        //then
        assertThat(runtimeException.getMessage()).isNotNull();
    }
}