package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedFavDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedFavMapperTest {

    @Autowired
    private FeedFavMapper mapper;

    @Test
    public void insFeedFav() {
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(3);
        dto.setIuser(1);
//      신호가 올 때 저장공간이 4개가 생깁니다.
//      page Request session - 개인 공간
//      application - 전용 공간
//      page - 안에있는 공간이어서 우리는 못봅니다. 웹 하면을 만들 때 쓰는 변수
//      Request - 브라우저에서 쓰는 공간, 요청 들어올 때마다 새거
//      브라우저가 꺼지지 않는 이상 같은 공간, 새션에 유니크한 공간을 할당해 줍니다. (요청을 보내고 15분정도 안쓰면 소멸)
        int affectedRows1 = mapper.insFeedFav(dto);
        assertEquals(1, affectedRows1, "첫번재 insert");

        List<FeedFavDto> result = mapper.selFeedFavForTest(dto);
        assertEquals(1, result.size(), "첫번째 insert 확인");

        dto.setIfeed(6);
        dto.setIuser(1);

        int affectedRows2 = mapper.insFeedFav(dto);
        assertEquals(1, affectedRows2, "두번째 insert");

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        assertEquals(1, result2.size(), "두번째 insert 확인");

    }

    @Test
    public void delFeedFavTest() {
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(6);
        dto.setIuser(2);

        int affectedRows1 = mapper.delFeedFav(dto);
        assertEquals(1, affectedRows1);

        int affectedRows2 = mapper.delFeedFav(dto);
        assertEquals(0, affectedRows2);

        List<FeedFavDto> result3 = mapper.selFeedFavForTest(dto);
        assertEquals(0, result3.size());
    }

    @Test
    public void delFeedFavAllTest() {
        final int ifeed = 3;

        FeedFavDto seldto = new FeedFavDto();
        seldto.setIfeed(ifeed);
        List<FeedFavDto> selList = mapper.selFeedFavForTest(seldto);

        FeedDelDto dto = new FeedDelDto();
        dto.setIfeed(ifeed);
        int delaffectedRows = mapper.delFeedFavAll(dto);
        assertEquals(selList.size(), delaffectedRows);

        List<FeedFavDto> selList2 = mapper.selFeedFavForTest(seldto);
        assertEquals(0, selList2.size());
    }
}