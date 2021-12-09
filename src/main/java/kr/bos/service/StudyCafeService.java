package kr.bos.service;

import java.util.ArrayList;
import java.util.List;
import kr.bos.model.Room;
import kr.bos.model.StudyCafe;
import kr.bos.dto.RoomDto;
import kr.bos.dto.StudyCafeDto;
import kr.bos.mapper.RoomMapper;
import kr.bos.mapper.StudyCafeMapper;
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
    public void registerStudyCafe(Long userId, StudyCafeDto studyCafeDto) {

        StudyCafe studyCafe = StudyCafe.builder()
            .userId(userId)
            .title(studyCafeDto.getTitle())
            .address(studyCafeDto.getAddress())
            .thumbnail(studyCafeDto.getThumbnail())
            .build();

        studyCafeMapper.insertStudyCafe(studyCafe);

        List<Room> rooms = new ArrayList<>();
        for (RoomDto roomDto : studyCafeDto.getRooms()) {
            Room room = Room.builder()
                .studyCafeId(studyCafe.getId())
                .number(roomDto.getNumber())
                .capacity(roomDto.getCapacity())
                .build();

            rooms.add(room);
        }

        roomMapper.insertRooms(rooms);
    }
}
