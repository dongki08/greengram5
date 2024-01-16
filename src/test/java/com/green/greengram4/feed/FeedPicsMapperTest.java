package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
// Assertions. 생략하기위해

@MybatisTest //매퍼들 객체화(서비스, 컨트롤러는 x)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//기존데이트 쓰겠다 데이터베이스 바꾸지 않겠다 (이걸안주면 h2데이터방식)
class FeedPicsMapperTest {
//슬라이스 테스트
    private FeedInsDto dto;

    public FeedPicsMapperTest() {
        this.dto = new FeedInsDto();
        this.dto.setIfeed(4);

        List<String> pics = new ArrayList<>();
        pics.add("a.jpg");
        pics.add("b.jpg");
        //this.dto.setPics(pics);
    }

    @Autowired
    private FeedPicsMapper picsMapper;

    @BeforeEach
    public void beforeEach() {
        FeedDelDto delDto = new FeedDelDto();
        delDto.setIfeed(this.dto.getIfeed());
        delDto.setIuser(3);
        int affectedRows = picsMapper.delFeedPicsAll(delDto);
    }

//    @Test
//    void insFeedPics() {
//        List<String> preList = picsMapper.selFeedPicsAll(dto.getIfeed());
//        assertEquals(0, preList.size());
//
//        int insAffectedRows = picsMapper.insFeedPics(dto);
//        assertEquals(dto.getPics().size(), insAffectedRows);
//
//        List<String> afterList = picsMapper.selFeedPicsAll(dto.getIfeed());
//        assertEquals(dto.getPics().size(), afterList.size());
//
//        assertEquals(dto.getPics().get(0), afterList.get(0));
//        assertEquals(dto.getPics().get(1), afterList.get(1));
//    }

    @Test
    void selFeedPicsAll() {

    }

    @Test
    void delFeedPicsAll() {
    }
}