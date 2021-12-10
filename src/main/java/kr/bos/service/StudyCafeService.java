package kr.bos.service;

import java.util.List;
import kr.bos.domain.StudyCafe;
import kr.bos.dto.StudyCafeDto;
import kr.bos.exception.StudyCafeNotFoundException;
import kr.bos.mapper.StudyCafeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * StudyCafe Service.
 * <br>
 * 키워드로 스터디 카페들을 검색하는 기능, 스터디 카페의 id로 스터디 카페 이름을 가져오는 기능,
 * 스터디 카페 이름으로 해당 스터디의 id를 가져오는 기능, 스터디 카페를 수정/삭제/등록하는 기능을 제공.
 * <br>
 *
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class StudyCafeService {

    private final StudyCafeMapper studyCafeMapper;

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
     * 새로운 스터디 카페 등록.
     *
     * @since 1.0.0
     */
    public void insertStudyCafe(StudyCafe studyCafe) {
        if (isStudyCafeExists(studyCafe)) {
            throw new StudyCafeNotFoundException(studyCafe.getTitle());
        }

        studyCafeMapper.insertStudyCafe(studyCafe);
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
