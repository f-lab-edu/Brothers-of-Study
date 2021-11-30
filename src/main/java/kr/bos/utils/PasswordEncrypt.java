package kr.bos.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 패스워드 암호화 처리 클래스.
 *
 * @since 1.0.0
 */
public class PasswordEncrypt {

    private PasswordEncrypt() {
    }

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}