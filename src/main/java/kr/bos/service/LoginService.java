package kr.bos.service;

import javax.servlet.http.HttpSession;
import kr.bos.exception.InvalidPasswordException;
import kr.bos.model.domain.User;
import kr.bos.model.dto.request.LoginInfoReq;
import kr.bos.utils.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Login Service.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private static final String USER_ID = "USER_ID";
    private final HttpSession session;
    private final UserService userService;

    /**
     * 로그인. 데이터베이스에 저장된 패스워드와 입력한 패스워드 검증 후 세션에 값 저장.
     *
     * @since 1.0.0
     */
    public void login(LoginInfoReq loginInfoReq) {
        User user = userService.selectUserByEmail(loginInfoReq.getEmail());

        if (PasswordEncrypt.isMatch(loginInfoReq.getPassword(), user.getPassword())) {
            session.setAttribute(USER_ID, user.getId());
        } else {
            throw new InvalidPasswordException();
        }
    }

    public void logout() {
        session.removeAttribute(USER_ID);
    }

    public Long getCurrentUser() {
        return (Long) session.getAttribute(USER_ID);
    }
}