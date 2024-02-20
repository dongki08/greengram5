package com.green.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.MockMvcConfig;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcConfig //import같은기능 한글이 깨지지 않게 함
@WebMvcTest(FeedController.class)//빈등록된 컨트롤러를 객체화시킴
class FeedControllerTest {

    @Autowired
    private MockMvc mvc; //delete할지 post할지 쏴주는역할..?

    @Autowired
    private ObjectMapper mapper; //객체를 제이슨 형태로 바꿔주는 역할

    @MockBean//아무것도 값을 넣지않고 리턴하면 리턴해주는 타입의 디폴드값을 리턴함
    private FeedService service;//실제 컨트롤러에서 service를 사용하고 있어서

    @Test
    void postFeed() throws Exception {
        ResVo result = new ResVo(2);
        //when(service.postFeed(any())).thenReturn(result);
        //given(service.postFeed(any())).willReturn(result);

        FeedInsDto dto = new FeedInsDto();


        mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/feed") //방식 post, get 등등
                        .contentType(MediaType.APPLICATION_JSON) // 데이터를 제이슨으로 날리면 필수
                        .content(mapper.writeValueAsString(dto)) // 바디 부분 dto객체를 제이슨으로 변환하여 날림
        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)))
                .andDo(print());

        verify(service).postFeed(any());
    }

//    @Test
//    void getFeedAll() throws Exception {
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap();
//        params.add("page", "2");
//        params.add("안녕", "2");
//
//        List<FeedSelVo> list = new ArrayList<>();
//        FeedSelVo vo = new FeedSelVo();
//        vo.setIfeed(1);
//        vo.setContents("안녕");
//        list.add(vo);
//
//        FeedSelVo vo1 = new FeedSelVo();
//        vo1.setIfeed(2);
//        vo1.setContents("바이");
//        list.add(vo1);
//
//
//        when(service.getFeedAll(any())).thenReturn(list);
//
//        mvc.perform(
//                MockMvcRequestBuilders
//                        .get("/api/feed")
//                        .params(params)
//        ).andDo(print())
//         .andExpect(content().string(mapper.writeValueAsString(list)));
//
//        verify(service).getFeedAll(any());
//    }

    @Test
    void delFeed() throws Exception {

        ResVo result = new ResVo(2);

        when(service.delFeed(any())).thenReturn(result);

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/feed")


        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)))
                .andDo(print());
    }

    @Test
    void toggleFeedFav() {
    }
}