package kr.bos.service;

import kr.bos.exception.DuplicatedRoomNumberException;
import kr.bos.exception.ExistsTimeReservationException;
import kr.bos.mapper.ReservationMapper;
import kr.bos.mapper.RoomMapper;
import kr.bos.model.domain.Room;
import kr.bos.model.dto.request.RoomReq;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
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
     * 방 추가 생성하기.
     *
     * @since 1.0.0
     */
    public void createRoom(RoomReq roomReq, Long studyCafeId) {
        Room room = Room.builder()
            .studyCafeId(studyCafeId)
            .number(roomReq.getNumber())
            .capacity(roomReq.getCapacity())
            .build();

        try {
            roomMapper.insertRoom(room);
        } catch (DuplicateKeyException e) {
            throw new DuplicatedRoomNumberException();
        }
    }

    /**
     * 방 수정하기.
     *
     * @since 1.0.0
     */
    public void updateRoom(RoomReq roomReq, Long roomId, Long studyCafeId) {
        Room room = Room.builder()
            .id(roomId)
            .studyCafeId(studyCafeId)
            .number(roomReq.getNumber())
            .capacity(roomReq.getCapacity())
            .build();

        try {
            roomMapper.updateRoom(room);
        } catch (DuplicateKeyException e) {
            throw new DuplicatedRoomNumberException();
        }
    }

    /**
     * 방 삭제하기.
     *
     * @since 1.0.0
     */
    @Transactional
    public void deleteRoom(Long roomId) {
        if (reservationMapper.isExistsNowReservationByRoomId(roomId)) {
            throw new ExistsTimeReservationException();
        }
        roomMapper.deleteRoom(roomId);
    }
}
