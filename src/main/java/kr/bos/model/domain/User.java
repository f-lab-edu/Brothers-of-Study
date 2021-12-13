package kr.bos.model.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Reservation Model.
 *
 * @since 1.0.0
 */
@Getter
@Builder
public class User {

    Long id;
    String email;
    String password;
    String name;
    String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
