package kr.bos.controller;

import java.util.List;
import kr.bos.dto.StudyCafeDto;
import kr.bos.service.StudyCafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * StudyCafe Controller.
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/studyCafes")
@RequiredArgsConstructor
public class StudyCafeController {
    private final StudyCafeService studyCafeService;


    /**
     * Search.
     *
     * @since 1.0.0
     */
    @GetMapping("/search")
    public List<StudyCafeDto> search(@RequestParam(required = false) String keyword) {
        return studyCafeService.findStudyCafesByKeyword(keyword);
    }
}
