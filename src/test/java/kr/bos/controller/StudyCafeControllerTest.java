package kr.bos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.bos.exception.DuplicatedException;
import kr.bos.exception.NotFoundException;
import kr.bos.model.dto.request.ReservationReq;
import kr.bos.model.dto.request.ReviewReq;
import kr.bos.model.dto.request.RoomReq;
import kr.bos.model.dto.response.PageInfo;
import kr.bos.model.dto.response.ReviewRes;
import kr.bos.model.dto.response.RoomUseInfoRes;
import kr.bos.model.dto.response.StudyCafeDetailRes;
import kr.bos.model.dto.response.StudyCafeRes;
import kr.bos.service.LoginService;
import kr.bos.service.ReservationService;
import kr.bos.service.ReviewService;
import kr.bos.service.RoomService;
import kr.bos.service.StudyCafeService;
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
@WebMvcTest(StudyCafeController.class)
class StudyCafeControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private StudyCafeService studyCafeService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private ReservationService reservationService;

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
    @DisplayName("스터디카페 목록 조회에 성공합니다.")
    public void getStudyCafesTestWhenSuccess() throws Exception {
        List<StudyCafeRes> list = new ArrayList<>();
        StudyCafeRes studyCafe1 = StudyCafeRes.builder()
            .id(1L)
            .title("스터디카페 1")
            .address("주소1")
            .reviewAverage(3.0F)
            .build();

        StudyCafeRes studyCafe2 = StudyCafeRes.builder()
            .id(2L)
            .title("스터디카페 3")
            .address("주소3")
            .reviewAverage(1.0F)
            .build();

        StudyCafeRes studyCafe3 = StudyCafeRes.builder()
            .id(3L)
            .title("스터디카페 2")
            .address("주소2")
            .reviewAverage(5.0F)
            .build();

        list.add(studyCafe1);
        list.add(studyCafe2);
        list.add(studyCafe3);
        PageInfo<StudyCafeRes> studyCafes = new PageInfo<>(3L, list);
        when(studyCafeService.getStudyCafes(any())).thenReturn(studyCafes);

        mockMvc.perform(get(
            "/study_cafes?page=1&isBookmark=true&isMyStudyCafe=true&order=NAME&keyword=검색어"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("study_cafes/get-study-cafes/success", requestParameters(
                parameterWithName("keyword").description("Search Word"),
                parameterWithName("isBookmark").description("isBookmark Filter"),
                parameterWithName("isMyStudyCafe").description("isMyStudyCafe Filter"),
                parameterWithName("order").description("Order Option"),
                parameterWithName("page").description("Page Number")), responseFields(
                fieldWithPath("totalCount").type(JsonFieldType.NUMBER)
                    .description("studyCafes Count"),
                fieldWithPath("list.[].id").type(JsonFieldType.NUMBER)
                    .description("studyCafe`s id"),
                fieldWithPath("list.[].title").type(JsonFieldType.STRING)
                    .description("studyCafe`s title"),
                fieldWithPath("list.[].address").type(JsonFieldType.STRING)
                    .description("studyCafe`s address"),
                fieldWithPath("list.[].reviewAverage").type(JsonFieldType.NUMBER)
                    .description("studyCafe`s reviewAverage")
            )));
    }

    @Test
    @DisplayName("스터디카페 상세 조회에 성공합니다.")
    public void getStudyCafeTestWhenSuccess() throws Exception {
        List<RoomUseInfoRes> rooms = new ArrayList<>();
        RoomUseInfoRes room1 = new RoomUseInfoRes(1L, 1, 1, false);
        RoomUseInfoRes room2 = new RoomUseInfoRes(2L, 2, 4, true);
        rooms.add(room1);
        rooms.add(room2);
        StudyCafeDetailRes studyCafe = StudyCafeDetailRes.builder()
            .id(1L)
            .title("스터디카페 1")
            .address("주소1")
            .thumbnail("썸네일")
            .useCount(1000L)
            .bookmarkCount(100L)
            .reviewAverage(3.0F)
            .rooms(rooms)
            .build();

        when(studyCafeService.getStudyCafe(any(), any())).thenReturn(studyCafe);
        mockMvc.perform(get("/study_cafes/1/").accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("study_cafes/get-study-cafe/success", responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER)
                    .description("studyCafe`s id"),
                fieldWithPath("title").type(JsonFieldType.STRING)
                    .description("studyCafe`s title"),
                fieldWithPath("address").type(JsonFieldType.STRING)
                    .description("studyCafe`s address"),
                fieldWithPath("thumbnail").type(JsonFieldType.STRING)
                    .description("studyCafe`s thumbnail"),
                fieldWithPath("useCount").type(JsonFieldType.NUMBER)
                    .description("studyCafe`s useCount"),
                fieldWithPath("bookmarkCount").type(JsonFieldType.NUMBER)
                    .description("studyCafe`s bookmarkCount"),
                fieldWithPath("reviewAverage").type(JsonFieldType.NUMBER)
                    .description("studyCafe`s reviewAverage"),
                fieldWithPath("rooms.[].id").type(JsonFieldType.NUMBER)
                    .description("room`s id"),
                fieldWithPath("rooms.[].number").type(JsonFieldType.NUMBER)
                    .description("room`s number"),
                fieldWithPath("rooms.[].capacity").type(JsonFieldType.NUMBER)
                    .description("room`s capacity"),
                fieldWithPath("rooms.[].currentUse").type(JsonFieldType.BOOLEAN)
                    .description("room`s currentUse")
            )));
    }

    @Test
    @DisplayName("스터디카페 상세 조회에 실패합니다. :존재하지 않는 스터디카페입니다.")
    public void getStudyCafeTestWhenFail() throws Exception {
        doThrow(new NotFoundException("Select not found study cafe.")).when(studyCafeService)
            .getStudyCafe(any(), any());

        mockMvc.perform(get("/study_cafes/1/").accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("study_cafes/get-study-cafe/fail"));
    }

    @Test
    @DisplayName("북마크 등록에 성공합니다.")
    public void registerBookmarkTestWhenSuccess() throws Exception {
        mockMvc.perform(post("/study_cafes/1/bookmarks")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("study_cafes/register-bookmarks/success"));
    }

    @Test
    @DisplayName("북마크 등록에 실패합니다. :이미 북마크가 등록되어 있습니다.")
    public void registerBookmarkTestWhenFail() throws Exception {
        doThrow(new DuplicatedException("Bookmark is already set.")).when(studyCafeService)
            .registerBookmark(any(), any());

        mockMvc.perform(post("/study_cafes/1/bookmarks")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("study_cafes/register-bookmarks/fail"));
    }

    @Test
    @DisplayName("북마크 취소에 성공합니다.")
    public void deleteBookmarkTestWhenSuccess() throws Exception {
        mockMvc.perform(delete("/study_cafes/1/bookmarks")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("study_cafes/cancel-bookmarks/success"));
    }

    @Test
    @DisplayName("북마크 취소에 실패합니다. :북마크가 등록되어 있지 않습니다.")
    public void deleteBookmarkTestWhenFail() throws Exception {
        Long studyCafeId = 1L;
        doThrow(new NotFoundException("This bookmark is not exists.")).when(studyCafeService)
            .cancelBookmark(any(), eq(studyCafeId));

        mockMvc.perform(delete("/study_cafes/{studyCafeId}/bookmarks", studyCafeId)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("study_cafes/cancel-bookmarks/fail"));
    }

    @Test
    @DisplayName("방 추가에 성공합니다.")
    public void createRoomTestWhenSuccess() throws Exception {
        RoomReq requestDto = RoomReq.builder()
            .studyCafeId(1L)
            .number(11)
            .capacity(40)
            .build();

        mockMvc.perform(post("/study_cafes/1/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("study_cafes/create-rooms/success"));
    }

    @Test
    @DisplayName("방 추가에 실패합니다. :중복된 방 번호가 존재합니다.")
    public void createRoomTestWhenFail() throws Exception {
        RoomReq requestDto = RoomReq.builder()
            .number(1)
            .capacity(4)
            .build();

        Long studyCafeId = 1L;
        doThrow(new DuplicatedException("This room number already exists.")).when(roomService)
            .createRoom(requestDto, studyCafeId);

        mockMvc.perform(post("/study_cafes/1/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("study_cafes/create-rooms/fail"));
    }

    @Test
    @DisplayName("방 수정에 성공합니다.")
    public void updateRoomTestWhenSuccess() throws Exception {
        RoomReq requestDto = RoomReq.builder()
            .studyCafeId(1L)
            .number(11)
            .capacity(40)
            .build();

        mockMvc.perform(put("/study_cafes/1/rooms/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("study_cafes/update-rooms/success"));
    }

    @Test
    @DisplayName("방 수정에 실패합니다. :중복된 방 번호가 존재합니다.")
    public void updateRoomTestWhenFail() throws Exception {
        RoomReq requestDto = RoomReq.builder()
            .number(1)
            .capacity(4)
            .build();

        doThrow(new DuplicatedException("This room number already exists.")).when(roomService)
            .updateRoom(eq(requestDto), any(), any());

        mockMvc.perform(put("/study_cafes/1/rooms/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("study_cafes/update-rooms/fail"));
    }

    @Test
    @DisplayName("방 삭제에 성공합니다.")
    public void deleteRoomTestWhenSuccess() throws Exception {
        mockMvc.perform(delete("/study_cafes/1/rooms/2"))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("study_cafes/delete-rooms/success"));
    }

    @Test
    @DisplayName("방 삭제에 실패합니다. :삭제하려는 방에 등록된 예약이 존재합니다.")
    public void deleteRoomTestWhenFail() throws Exception {
        doThrow(new DuplicatedException("A reservation exists at that time.")).when(roomService)
            .deleteRoom(2L);

        mockMvc.perform(delete("/study_cafes/1/rooms/2"))
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("study_cafes/delete-rooms/fail"));
    }

    @Test
    @DisplayName("예약하기에 성공합니다.")
    public void createReservationTestWhenSuccess() throws Exception {
        ReservationReq requestDto = ReservationReq.builder()
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now().plusMinutes(30))
            .build();

        mockMvc.perform(post("/study_cafes/1/rooms/2/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("study_cafes/create-reservations/success"));
    }

    @Test
    @DisplayName("예약하기에 실패합니다. :잘못된 이용시간을 입력했습니다.")
    public void createReservationTestWhenFail1() throws Exception {
        ReservationReq requestDto = ReservationReq.builder()
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now().plusMinutes(30))
            .build();

        doThrow(new IllegalArgumentException("Please check your reservation time again."))
            .when(reservationService).createReservation(any(), any(), any());

        mockMvc.perform(post("/study_cafes/1/rooms/2/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andDo(document("study_cafes/create-reservations/fail"));
    }

    @Test
    @DisplayName("예약하기에 실패합니다. :방 잠금 데이터가 존재하지 않습니다.")
    public void createReservationTestWhenFail2() throws Exception {
        ReservationReq requestDto = ReservationReq.builder()
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now().plusMinutes(30))
            .build();

        doThrow(new NotFoundException("Select not found room_lock"))
            .when(reservationService).createReservation(any(), any(), any());

        mockMvc.perform(post("/study_cafes/1/rooms/2/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("study_cafes/create-reservations/fail2"));
    }

    @Test
    @DisplayName("예약하기에 실패합니다. :이미 예약이 존재합니다.")
    public void createReservationTestWhenFail3() throws Exception {
        ReservationReq requestDto = ReservationReq.builder()
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now().plusMinutes(30))
            .build();

        doThrow(new DuplicatedException("This Reservation Time already exists."))
            .when(reservationService).createReservation(any(), any(), any());

        mockMvc.perform(post("/study_cafes/1/rooms/2/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("study_cafes/create-reservations/fail3"));
    }

    @Test
    @DisplayName("예약취소에 성공합니다.")
    public void cancelReservationTestWhenSuccess() throws Exception {
        mockMvc.perform(delete("/study_cafes/1/rooms/2/reservations/3"))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("study_cafes/cancel-reservations/success"));
    }

    @Test
    @DisplayName("예약취소에 실패합니다. :존재하지 않는 예약입니다.")
    public void cancelReservationTestWhenFail1() throws Exception {
        doThrow(new NotFoundException("Select not found reservation"))
            .when(reservationService).cancelReservation(any(), any());

        mockMvc.perform(delete("/study_cafes/1/rooms/2/reservations/3"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("study_cafes/cancel-reservations/fail"));
    }

    @Test
    @DisplayName("예약취소에 실패합니다. :시작 10분전에 예약을 취소할 수 없습니다.")
    public void cancelReservationTestWhenFail2() throws Exception {
        doThrow(new IllegalArgumentException("Reservations can not be canceled 10 minutes."))
            .when(reservationService).cancelReservation(any(), any());

        mockMvc.perform(delete("/study_cafes/1/rooms/2/reservations/3"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andDo(document("study_cafes/cancel-reservations/fail2"));
    }

    @Test
    @DisplayName("리뷰 목록 조회에 성공합니다.")
    public void getReviewsTestWhenSuccess() throws Exception {
        List<ReviewRes> list = new ArrayList<>();
        ReviewRes review1 = ReviewRes.builder()
            .id(1L)
            .userName("유저 1")
            .description("설명 1")
            .score(1.0F)
            .createdAt(LocalDateTime.now())
            .build();

        ReviewRes review2 = ReviewRes.builder()
            .id(2L)
            .userName("유저 2")
            .description("설명 2")
            .score(2.0F)
            .createdAt(LocalDateTime.now())
            .build();

        ReviewRes review3 = ReviewRes.builder()
            .id(3L)
            .userName("유저 3")
            .description("설명 3")
            .score(3.0F)
            .createdAt(LocalDateTime.now())
            .build();

        list.add(review1);
        list.add(review2);
        list.add(review3);
        PageInfo<ReviewRes> reviews = new PageInfo<>(3L, list);
        when(reviewService.getReviews(any(), any())).thenReturn(reviews);

        mockMvc.perform(
                get("/study_cafes/1/reviews?page=1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("study_cafes/get-reviews/success", requestParameters(
                parameterWithName("page").description("Page Number")), responseFields(
                fieldWithPath("totalCount").type(JsonFieldType.NUMBER)
                    .description("studyCafes Count"),
                fieldWithPath("list.[].id").type(JsonFieldType.NUMBER)
                    .description("reviews`s id"),
                fieldWithPath("list.[].userName").type(JsonFieldType.STRING)
                    .description("reviews`s userName"),
                fieldWithPath("list.[].description").type(JsonFieldType.STRING)
                    .description("reviews`s description"),
                fieldWithPath("list.[].score").type(JsonFieldType.NUMBER)
                    .description("reviews`s score"),
                fieldWithPath("list.[].createdAt").type(JsonFieldType.STRING)
                    .description("reviews`s created_at")
            )));
    }

    @Test
    @DisplayName("리뷰 등록에 성공합니다.")
    public void createReviewTestWhenSuccess() throws Exception {
        ReviewReq requestDto = ReviewReq.builder()
            .description("설명 1")
            .score(1.0F)
            .build();

        mockMvc.perform(post("/study_cafes/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("study_cafes/create-reviews/success"));
    }

    @Test
    @DisplayName("리뷰 수정에 성공합니다.")
    public void updateReviewTestWhenSuccess() throws Exception {
        ReviewReq requestDto = ReviewReq.builder()
            .description("설명 1")
            .score(1.0F)
            .build();

        mockMvc.perform(put("/study_cafes/1/reviews/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("study_cafes/update-reviews/success"));
    }

    @Test
    @DisplayName("리뷰 수정에 실패합니다. :존재하지 않는 리뷰입니다.")
    public void updateReviewTestWhenFail() throws Exception {
        ReviewReq requestDto = ReviewReq.builder()
            .description("설명 1")
            .score(1.0F)
            .build();

        doThrow(new NotFoundException("Select not found review"))
            .when(reviewService).updateReview(any(), any(), any());

        mockMvc.perform(put("/study_cafes/1/reviews/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("study_cafes/update-reviews/fail"));
    }

    @Test
    @DisplayName("리뷰 삭제에 성공합니다.")
    public void deleteReviewTestWhenSuccess() throws Exception {
        mockMvc.perform(delete("/study_cafes/1/reviews/2"))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("study_cafes/delete-reviews/success"));
    }

    @Test
    @DisplayName("리뷰 삭제에 실패합니다. :존재하지 않는 리뷰입니다.")
    public void deleteReviewTestWhenFail() throws Exception {
        doThrow(new NotFoundException("Select not found review"))
            .when(reviewService).deleteReview(any(), any());

        mockMvc.perform(delete("/study_cafes/1/reviews/2"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("study_cafes/delete-reviews/fail"));
    }
}