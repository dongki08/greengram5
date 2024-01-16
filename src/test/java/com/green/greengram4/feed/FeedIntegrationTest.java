package com.green.greengram4.feed;

import com.green.greengram4.BaseIntegrationTest;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedIntegrationTest extends BaseIntegrationTest {

    @Test
    @Rollback(false) //true db에 저장, false db에 저장x
    public void postFeed() throws Exception {
        List<String> pics = new ArrayList<>();
        pics.add("https://i.namu.wiki/i/tcjtuVfCgbGkYdb_z1r03tQwDNPQwliB_4zOb3Z8P2cSmWx7u8i5euwz9aEMX-MxEHugOlgWkrIwJLTNAv5i_w.webp");

        FeedInsDto dto = new FeedInsDto();
        dto.setIuser(1);
        dto.setContents("통합 테스트 작업 중");
        dto.setLocation("그린컴퓨터학원");
        //dto.setPics(pics);

        String json = om.writeValueAsString(dto);
        System.out.println("json : " + json);

        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/feed")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

                String content = mr.getResponse().getContentAsString(); // { result = 1 }
                ResVo vo = om.readValue(content, ResVo.class);
                assertEquals(true, vo.getResult() > 0);
    }

    @Test
    @Rollback(false)
    public void delFeed() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed", "10");
        params.add("iuser", "1");

        MvcResult mr = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/feed")
                        .params(params)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        ResVo vo = om.readValue(content, ResVo.class);
        assertEquals(1, vo.getResult());
    }
}
