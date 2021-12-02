package kr.bos.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LoginInfo Dto.
 * 로그인 입력 정보.
 *
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
public class LoginInfoDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
