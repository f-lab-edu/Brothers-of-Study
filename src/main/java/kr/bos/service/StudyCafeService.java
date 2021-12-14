package kr.bos.service;

import java.util.ArrayList;
import java.util.List;
import kr.bos.dto.StudyCafeDto;
import kr.bos.exception.BookmarkNotFoundException;
import kr.bos.exception.DuplicatedBookmarkException;
import kr.bos.exception.StudyCafeNotFoundException;
import kr.bos.mapper.RoomMapper;
import kr.bos.mapper.StudyCafeMapper;
import kr.bos.model.domain.Room;
import kr.bos.model.domain.StudyCafe;
import kr.bos.model.dto.request.RoomReq;
import kr.bos.model.dto.request.StudyCafeReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StudyCafe Service.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class StudyCafeService {

    private final StudyCafeMapper studyCafeMapper;
    private final RoomMapper roomMapper;

    /**
     * 스터디 카페 등록하기.
     * <br>
     * 입력 받은 스터디 카페 정보로 스터디 카페 생성. 생성한 스터디 카페의 id를 Room에 지정한 후 Room 생성.
     *
     * @since 1.0.0
     */
    @Transactional
    public void registerStudyCafe(Long userId, StudyCafeReq studyCafeReq) {

        StudyCafe studyCafe = StudyCafe.builder()
            .userId(userId)
            .title(studyCafeReq.getTitle())
            .address(studyCafeReq.getAddress())
            .thumbnail(studyCafeReq.getThumbnail())
            .build();

        studyCafeMapper.insertStudyCafe(studyCafe);

        List<Room> rooms = new ArrayList<>();
        for (RoomReq roomReq : studyCafeReq.getRooms()) {
            Room room = Room.builder()
                .studyCafeId(studyCafe.getId())
                .number(roomReq.getNumber())
                .capacity(roomReq.getCapacity())
                .build();

            rooms.add(room);
        }

        roomMapper.insertRooms(rooms);
    }

    /**
     * 북 마크 등록하기.
     * <br>
     * 북 마크가 이미 등록되어 있다면 DuplicatedBookmarkException 예외 발생.
     *
     * @since 1.0.0
     */
    public void registerBookmark(Long userId, Long studyCafeId) {
        int insertCount = studyCafeMapper.insertBookmark(userId, studyCafeId);
        if (insertCount == 0) {
            throw new DuplicatedBookmarkException();
        }
    }

    /**
     * 북 마크 취소하기.
     * <br>
     * 북 마크가 등록되지 않았다면 BookmarkNotFoundException 예외 발생.
     *
     * @since 1.0.0
     */
    public void cancelBookmark(Long userId, Long studyCafeId) {
        int deleteCount = studyCafeMapper.deleteBookmark(userId, studyCafeId);
        if (deleteCount == 0) {
            throw new BookmarkNotFoundException();
        }
    }

    /**
     * 키워드로 스터디 카페를 검색.
     *
     * @since 1.0.0
     */
    public List<StudyCafeDto> findStudyCafesByKeyword(String name) {
        return StudyCafeDto.toStudyCafeDtos(studyCafeMapper.getStudyCafesByKeyword(name));
    }

    /**
     * 스터디 카페의 id로, 해당 스터디 카페를 조회.
     *
     * @since 1.0.0
     */
    public StudyCafeDto findStudyCafeById(Long id) {
        return StudyCafeDto.toStudyCafeDto(
            studyCafeMapper.getStudyCafeById(id)
                .orElseThrow(() -> new StudyCafeNotFoundException(id)));
    }

    /**
     * 스터디 카페의 고유한 이름을 통해, 해당 스터디 카페의 id를 조회.
     *
     * @since 1.0.0
     */
    public Long findStudyCafeIdByName(String name) {
        return studyCafeMapper.getStudyCafeIdByName(name)
            .orElseThrow(() -> new StudyCafeNotFoundException(name));
    }

    /**
     * 해당 스터디 카페가 존재하는지 체크. 스터디 카페의 이름 & id가 조회될 시 존재하는 것으로 판단.
     *
     * @since 1.0.0
     */
    public boolean isStudyCafeExists(StudyCafe studyCafe) {
        return (
            studyCafeMapper.getStudyCafeById(studyCafe.getId()).isPresent()
                && studyCafeMapper.getStudyCafeIdByName(studyCafe.getTitle()).isPresent());
    }

    /**
     * 해당 스터디 카페를 수정/갱신.
     *
     * @since 1.0.0
     */
    public void updateStudyCafe(StudyCafe studyCafe) {
        if (!isStudyCafeExists(studyCafe)) {
            throw new StudyCafeNotFoundException(studyCafe.getTitle());
        }

        studyCafeMapper.updateStudyCafe(studyCafe);
    }

    /**
     * 해당 스터디 카페를 삭제.
     *
     * @since 1.0.0
     */
    public void deleteStudyCafe(StudyCafe studyCafe) {
        if (!isStudyCafeExists(studyCafe)) {
            throw new StudyCafeNotFoundException(studyCafe.getTitle());
        }

        studyCafeMapper.deleteStudyCafe(studyCafe.getId());
    }
}
