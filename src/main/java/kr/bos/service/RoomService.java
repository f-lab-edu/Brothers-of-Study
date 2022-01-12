package kr.bos.service;

import kr.bos.exception.DuplicatedException;
import kr.bos.mapper.ReservationMapper;
import kr.bos.mapper.RoomMapper;
import kr.bos.model.domain.Room;
import kr.bos.model.dto.request.RoomReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Room Service.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomMapper roomMapper;
    private final ReservationMapper reservationMapper;

    /**
     * 스터디 카페에 해당 방 번호가 존재하는지 확인.
     *
     * @param roomNumber  방 번호.
     * @param studyCafeId 스터디카페 ID.
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public boolean isExistsRoomNumber(Integer roomNumber, Long studyCafeId) {
        return roomMapper.isExistsRoomNumber(roomNumber, studyCafeId);
    }

    /**
     * 방 추가 생성하기.
     *
     * @param roomReq     방 생성 DTO.
     * @param studyCafeId 스터디카페 ID.
     * @since 1.0.0
     */
    public void createRoom(RoomReq roomReq, Long studyCafeId) {
        if (isExistsRoomNumber(roomReq.getNumber(), studyCafeId)) {
            throw new DuplicatedException("This room number already exists.");
        }

        Room room = Room.builder()
            .studyCafeId(studyCafeId)
            .number(roomReq.getNumber())
            .capacity(roomReq.getCapacity())
            .build();

        roomMapper.insertRoom(room);
    }

    /**
     * 방 수정하기.
     *
     * @param roomReq     방 생성 DTO.
     * @param roomId      방 ID.
     * @param studyCafeId 스터디카페 ID.
     * @since 1.0.0
     */
    public void updateRoom(RoomReq roomReq, Long roomId, Long studyCafeId) {
        if (isExistsRoomNumber(roomReq.getNumber(), studyCafeId)) {
            throw new DuplicatedException("This room number already exists.");
        }

        Room room = Room.builder()
            .id(roomId)
            .studyCafeId(studyCafeId)
            .number(roomReq.getNumber())
            .capacity(roomReq.getCapacity())
            .build();

        roomMapper.updateRoom(room);
    }

    /**
     * 방 삭제하기.
     *
     * @param roomId 방 ID.
     * @since 1.0.0
     */
    public void deleteRoom(Long roomId) {
        if (reservationMapper.isExistsNowReservationByRoomId(roomId)) {
            throw new DuplicatedException("A reservation exists at that time.");
        }
        roomMapper.deleteRoom(roomId);
    }
}
