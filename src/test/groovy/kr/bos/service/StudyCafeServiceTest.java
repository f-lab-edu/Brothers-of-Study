package kr.bos.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import kr.bos.domain.StudyCafe;
import kr.bos.dto.StudyCafeDto;
import kr.bos.exception.StudyCafeNotFoundException;
import kr.bos.mapper.StudyCafeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyCafeServiceTest {

    @InjectMocks
    private StudyCafeService studyCafeService;

    @Mock
    private StudyCafeMapper studyCafeMapper;

    public void giveStudyCafeExistCondition(boolean isStudyCafeExist) {
        StudyCafe studyCafe = StudyCafe.builder()
                .id(1L)
                .title("열공 스터디카페 분당점")
                .userId(100L)
                .address("성남시 분당구 장안로")
                .thumbnail("Thumbnail")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Long studyCafeId = 1L;

        Optional<StudyCafe> studyCafeOptional =
                isStudyCafeExist ? Optional.of(StudyCafe.builder().build()) : Optional.empty();
        Optional<Long> studyCafeIdOptional =
                isStudyCafeExist ? Optional.of(studyCafeId) : Optional.empty();

        lenient().doReturn(studyCafeOptional).when(studyCafeMapper).getStudyCafeById(any());
        lenient().doReturn(studyCafeIdOptional).when(studyCafeMapper).getStudyCafeIdByName(any());
    }


    @Test
    @DisplayName("스터디 카페 이름 키워드가 주어질 시 해당 키워드를 갖는 스터디 카페들을 DTO 객체 리스트로 돌려줍니다.")
    public void findStudyCafesByKeyword() {
        // given
        final String name = "강남";
        final List<StudyCafe> studyCafes =
                Arrays.asList(StudyCafe.builder().id(1L).title("강남스터디카페").build()
        );
        given(studyCafeMapper.getStudyCafesByKeyword(name)).willReturn(studyCafes);

        // when
        List<StudyCafeDto> studyCafeDtos = studyCafeService.findStudyCafesByKeyword(name);

        // then
        assertThat(studyCafeDtos).hasSize(1);
        assertThat(studyCafeDtos.get(0)).isInstanceOf(StudyCafeDto.class);
    }

    @Test
    @DisplayName("스터디 카페 객체가 주어지면 해당 스터디 카페가 등록되어 있는지 확인할 수 있습니다.")
    public void checkStudyCafeExists() {
        // given
        StudyCafe studyCafe = StudyCafe.builder().id(1L).title("강남").build();

        given(studyCafeMapper.getStudyCafeIdByName(studyCafe.getTitle()))
                .willReturn(Optional.of(1L));
        given(studyCafeMapper.getStudyCafeById(studyCafe.getId()))
                .willReturn(Optional.of(StudyCafe.builder().build()));

        // then
        assertThat(studyCafeService.isStudyCafeExists(studyCafe)).isTrue();
    }

    @Test
    @DisplayName("스터디 카페의 id가 조회되지 않으면 해당 스터디 카페는 등록되지 않은 상태입니다.")
    public void checkStudyCafeExistsWithNoId() {
        // given
        StudyCafe studyCafe = StudyCafe.builder().id(1L).title("강남").build();

        lenient().doReturn(Optional.of(StudyCafe.builder().build()))
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
        StudyCafe studyCafe = StudyCafe.builder().id(1L).title("강남").build();

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
                .willReturn(Optional.of(StudyCafe.builder().id(id).build()));

        // when
        StudyCafeDto studyCafeDto = studyCafeService.findStudyCafeById(id);

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
    @DisplayName("존재하지 않는 스터디 카페를 새로 등록할 수 있습니다.")
    public void insertStudyCafe() {
        //given
        StudyCafe studyCafe = StudyCafe.builder().build();
        given(studyCafeMapper.insertStudyCafe(studyCafe)).willReturn(1);
        giveStudyCafeExistCondition(false);
        //when
        studyCafeService.insertStudyCafe(studyCafe);

        //then
        verify(studyCafeMapper).insertStudyCafe(studyCafe);
    }

    @Test
    @DisplayName("이미 존재하는 스터디 카페는 등록할 수 없습니다.")
    public void insertStudyCafeAlreadyExists() {
        //given
        StudyCafe studyCafe = StudyCafe.builder().build();
        giveStudyCafeExistCondition(true);

        //when
        RuntimeException runtimeException =
                Assertions.assertThrows(StudyCafeNotFoundException.class,
                    () -> studyCafeService.insertStudyCafe(studyCafe));

        //then
        assertThat(runtimeException.getMessage()).isNotNull();
    }

    @Test
    @DisplayName("스터디 카페가 존재할 시, 스터디 카페의 정보를 업데이트합니다.")
    public void updateValidStudyCafe() {
        //given
        StudyCafe studyCafe = StudyCafe.builder().build();
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
        StudyCafe studyCafe = StudyCafe.builder().build();
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
        StudyCafe studyCafe = StudyCafe.builder().build();
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
        StudyCafe studyCafe = StudyCafe.builder().build();
        giveStudyCafeExistCondition(false);

        //when
        RuntimeException runtimeException =
                Assertions.assertThrows(StudyCafeNotFoundException.class,
                    () -> studyCafeService.deleteStudyCafe(studyCafe));

        //then
        assertThat(runtimeException.getMessage()).isNotNull();
    }
}
