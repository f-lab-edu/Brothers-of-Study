package kr.bos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.bos.exception.DuplicatedException;
import kr.bos.exception.NotFoundException;
import kr.bos.model.dto.request.LoginReq;
import kr.bos.model.dto.request.SignUpReq;
import kr.bos.model.dto.request.UserUpdateReq;
import kr.bos.model.dto.response.UserInfoRes;
import kr.bos.service.LoginService;
import kr.bos.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(UserController.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .apply(sharedHttpSession())
            .build();
    }

    @Test
    @DisplayName("회원가입에 성공합니다.")
    public void signUpTestWhenSuccess() throws Exception {
        SignUpReq requestDto = SignUpReq.builder()
            .email("email@email.com")
            .password("password")
            .name("장진욱")
            .address("주소")
            .build();

        doNothing().when(userService).signUp(requestDto);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("users/sign-up/success", requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("user's email"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("user's password"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("user's name"),
                fieldWithPath("address").type(JsonFieldType.STRING).description("user's address")
            )));
    }

    @Test
    @DisplayName("회원가입에 실패합니다. :중복된 이메일입니다.")
    public void signUpTestWhenFail() throws Exception {
        SignUpReq requestDto = SignUpReq.builder()
            .email("email@email.com")
            .password("password")
            .name("장진욱")
            .address("주소")
            .build();

        doThrow(new DuplicatedException("This email already exists.")).when(userService)
            .signUp(any());

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("users/sign-up/fail", requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("user's email"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("user's password"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("user's name"),
                fieldWithPath("address").type(JsonFieldType.STRING).description("user's address")
            )));
    }

    @Test
    @DisplayName("로그인에 성공합니다.")
    public void loginTestWhenSuccess() throws Exception {
        LoginReq requestDto = LoginReq.builder()
            .email("email@email.com")
            .password("password")
            .build();

        doNothing().when(loginService).login(requestDto);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("users/login/success", requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("user's email"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("user's password")
            )));
    }

    @Test
    @DisplayName("로그인에 실패합니다. :존재하지 않는 이메일입니다.")
    public void loginTestWhenFail() throws Exception {
        LoginReq requestDto = LoginReq.builder()
            .email("email@email.com")
            .password("password")
            .build();

        doThrow(new NotFoundException("Select not found user.")).when(loginService).login(any());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("users/login/fail1", requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("user's email"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("user's password")
            )));
    }

    @Test
    @DisplayName("로그인에 실패합니다. :비밀번호가 일치하지 않습니다.")
    public void loginTestWhenFail2() throws Exception {
        LoginReq requestDto = LoginReq.builder()
            .email("email@email.com")
            .password("password")
            .build();

        doThrow(new IllegalArgumentException("Your password is invalid.")).when(loginService)
            .login(any());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andDo(document("users/login/fail2", requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("user's email"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("user's password")
            )));
    }

    @Test
    @DisplayName("로그아웃에 성공합니다.")
    public void logoutTestWhenSuccess() throws Exception {
        doNothing().when(loginService).logout();

        mockMvc.perform(post("/users/logout"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("users/logout/success"));
    }

    @Test
    @DisplayName("회원탈퇴에 성공합니다.")
    public void deleteUserTestWhenSuccess() throws Exception {
        doNothing().when(userService).deleteUser(any());

        mockMvc.perform(delete("/users"))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("users/delete/success"));
    }

    @Test
    @DisplayName("회원정보 조회에 성공합니다.")
    public void getUserInfoTestWhenSuccess() throws Exception {
        UserInfoRes userInfo = UserInfoRes.builder()
            .email("email@email.com")
            .name("장진욱")
            .address("주소")
            .build();
        given(userService.getUserInfo(any())).willReturn(userInfo);

        mockMvc.perform(get("/users"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("users/info/success", responseFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("user`s email"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("user`s name"),
                fieldWithPath("address").type(JsonFieldType.STRING).description("user`s address")
            )));
    }

    @Test
    @DisplayName("회원정보 조회에 실패합니다. :존재하지 않는 계정")
    public void getUserInfoTestWhenFail() throws Exception {
        doThrow(new NotFoundException("Select not found user")).when(userService)
            .getUserInfo(any());

        mockMvc.perform(get("/users"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("users/info/fail"));
    }

    @Test
    @DisplayName("회원정보 수정에 성공합니다.")
    public void updateUserInfoTestWhenSuccess() throws Exception {
        UserUpdateReq requestDto = UserUpdateReq.builder()
            .name("장진욱")
            .address("주소")
            .build();

        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("users/info-update/success", requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("user's name"),
                fieldWithPath("address").type(JsonFieldType.STRING).description("user's address"),
                fieldWithPath("id").ignored()
            )));
    }
}