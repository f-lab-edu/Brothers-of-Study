package kr.bos.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserRes.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserInfoRes {

    private String email;
    private String name;
    private String address;
}
