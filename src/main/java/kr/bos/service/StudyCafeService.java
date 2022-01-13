package kr.bos.service;

import java.util.ArrayList;
import java.util.List;
import kr.bos.exception.DuplicatedException;
import kr.bos.exception.NotFoundException;
import kr.bos.mapper.RoomMapper;
import kr.bos.mapper.StudyCafeMapper;
import kr.bos.model.domain.Room;
import kr.bos.model.domain.StudyCafe;
import kr.bos.model.dto.request.RoomReq;
import kr.bos.model.dto.request.SearchOption;
import kr.bos.model.dto.request.SearchTimeReq;
import kr.bos.model.dto.request.StudyCafeReq;
import kr.bos.model.dto.response.PageInfo;
import kr.bos.model.dto.response.RoomUseInfoRes;
import kr.bos.model.dto.response.StudyCafeDetailRes;
import kr.bos.model.dto.response.StudyCafeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
     * 스터디 카페 목록 조회하기.
     *
     * @param searchOption 검색 옵션 DTO.
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "studyCafes", condition = "#searchOption.cacheKey == null",
        key = "#searchOption.cacheKey")
    public PageInfo<StudyCafeRes> getStudyCafes(SearchOption searchOption) {
        List<StudyCafeRes> studyCafes = studyCafeMapper.selectStudyCafesBySearchOption(
            searchOption);
        Long totalCount = studyCafeMapper.selectStudyCafesCountsBySearchOption(searchOption);
        return new PageInfo<>(totalCount, studyCafes);
    }

    /**
     * 스터디 카페 등록하기.
     * <br>
     * 입력 받은 스터디 카페 정보로 스터디 카페 생성. 생성한 스터디 카페의 id를 Room에 지정한 후 Room 생성.
     *
     * @param userId       유저 ID
     * @param studyCafeReq 스터디카페 DTO
     * @since 1.0.0
     */
    @CacheEvict(value = "studyCafes", allEntries = true)
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

        List<Long> roomIds = new ArrayList<>();
        for (Room room : rooms) {
            roomIds.add(room.getId());
        }

        roomMapper.insertRoomLocks(roomIds);
    }

    /**
     * 스터디 카페 디테일 조회.
     *
     * @param studyCafeId   스터디카페 ID
     * @param searchTimeReq 검색 시간 DTO
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public StudyCafeDetailRes getStudyCafe(Long studyCafeId, SearchTimeReq searchTimeReq) {
        StudyCafeDetailRes studyCafeRes = studyCafeMapper.selectStudyCafeDetailById(studyCafeId)
            .orElseThrow(() -> new NotFoundException("Select not found study cafe."));

        List<RoomUseInfoRes> rooms = roomMapper.selectRoomUseInfo(studyCafeId,
            searchTimeReq.getSearchTime());

        studyCafeRes.setRooms(rooms);
        return studyCafeRes;
    }

    /**
     * 북 마크 등록하기.
     * <br>
     *
     * @param userId      유저 ID
     * @param studyCafeId 스터디카페 ID
     * @since 1.0.0
     */
    public void registerBookmark(Long userId, Long studyCafeId) {
        if (studyCafeMapper.isExistsBookmark(userId, studyCafeId)) {
            throw new DuplicatedException("Bookmark is already set.");
        }

        studyCafeMapper.insertBookmark(userId, studyCafeId);
    }

    /**
     * 북 마크 취소하기.
     * <br>
     *
     * @param userId      유저 ID
     * @param studyCafeId 스터디카페 ID
     * @since 1.0.0
     */
    public void cancelBookmark(Long userId, Long studyCafeId) {
        int deleteCount = studyCafeMapper.deleteBookmark(userId, studyCafeId);
        if (deleteCount == 0) {
            throw new NotFoundException("This bookmark is not exists.");
        }
    }
}
