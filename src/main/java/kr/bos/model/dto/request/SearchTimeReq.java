package kr.bos.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * SearchTime Request Dto.
 *
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchTimeReq {

    @JsonProperty("search_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
        timezone = "Asia/Seoul")
    private LocalDateTime searchTime;

}
