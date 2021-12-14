package kr.bos.model.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Reservation Model.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
