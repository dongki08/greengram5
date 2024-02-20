package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class) //스프링 컨테어너를 사용할 수 있도록
@Import({FeedService.class}) //특정한 ()의 객체를 임폴트함 빈등록 {}중괄호가 있으면 여러개 적을 수 있다
class FeedServiceTest {

    @MockBean
    private FeedMapper mapper;
    @MockBean
    private FeedPicsMapper picsMapper;
    @MockBean
    private FeedFavMapper favMapper;
    @MockBean
    private FeedCommentMapper commentMapper;

    @Autowired
    private FeedService service;

//    @Test
//    void postFeed() {
//        when(mapper.insFeed(any())).thenReturn(2);
//        when(picsMapper.insFeedPics(any())).thenReturn(3);
//
//        FeedInsDto dto = new FeedInsDto();
//        dto.setIfeed(100);
//        ResVo vo = service.postFeed(dto);
//        assertEquals(dto.getIfeed(), vo.getResult());
//
//        // 진짜로 이 메소드를 호출했는지
//        verify(mapper).insFeed(any());
//        verify(picsMapper).insFeedPics(any());
//
//        FeedInsDto dto2 = new FeedInsDto();
//        dto.setIfeed(200);
//        ResVo vo2 = service.postFeed(dto2);
//        assertEquals(dto2.getIfeed(), vo2.getResult());
//    }

//    @Test
//    public void getFeedAll() {
//        FeedSelVo feedSelVo1 = new FeedSelVo();
//        feedSelVo1.setIfeed(1);
//        feedSelVo1.setContents("일번 feedSelVo");
//
//        FeedSelVo feedSelVo2 = new FeedSelVo();
//        feedSelVo2.setIfeed(2);
//        feedSelVo2.setContents("이번 feedSelVo");
//        //서로 다른 주소값 둘은 서로 다르다
//        //feedselvo타입의 객체를 각각 생성
//
//        List<FeedSelVo> list = new ArrayList<>();
//        list.add(feedSelVo1);
//        list.add(feedSelVo2);
//        //1번방, 2번방 값이 담긴 방들을 리스트에 담는다
//
//        when(  mapper.selFeedAll(any())).thenReturn(list);
//        //매퍼 selfeedall 아무값(any)이 들어오면 list로 리턴
//
//
//        List<String> feed1Pics = Arrays.stream( new String[]{ "a.jpg", "b.jpg"} ).toList();
//        //배열로 생성
//
//        List<String> feed2Pics = new ArrayList<>();
//        feed2Pics.add("가.jpg");
//        feed2Pics.add("나.jpg");
//        //add로 생성
//
//        List<List<String>> picsList = new ArrayList();
//        picsList.add(feed1Pics);
//        picsList.add(feed2Pics);
//
//        //List<String>[] picsArr2 = (List<String>[])picsList
//
//        List<String>[] picsArr = new List[2];
//        picsArr[0] = feed1Pics;
//        picsArr[1] = feed2Pics;
//
//        when(  picsMapper.selFeedPicsAll(1)).thenReturn(feed1Pics);
//        when(  picsMapper.selFeedPicsAll(2)).thenReturn(feed2Pics);
//
//        // ifeed(1) 댓글
//        List<FeedCommentSelVo> cmtsFeed1 = new ArrayList<>();
//
//        FeedCommentSelVo cmtVo1_1 = new FeedCommentSelVo();
//        cmtVo1_1.setIfeedComment(1);
//        cmtVo1_1.setComment("1-cmtVo1_1");
//
//        FeedCommentSelVo cmtVo1_2 = new FeedCommentSelVo();
//        cmtVo1_2.setIfeedComment(2);
//        cmtVo1_2.setComment("2-cmtVo1_2");
//
//        cmtsFeed1.add(cmtVo1_1);
//        cmtsFeed1.add(cmtVo1_2);
//
//        FeedCommentSelDto fcDto1 = new FeedCommentSelDto();
//        fcDto1.setStartIdx(0);
//        fcDto1.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
//        fcDto1.setIfeed(1);
//
//        when( commentMapper.selFeedCommentAll(fcDto1) ).thenReturn(cmtsFeed1);
//
//        // ifeed(2) 댓글
//        List<FeedCommentSelVo> cmtsFeed2 = new ArrayList<>();
//
//        FeedCommentSelVo cmtVo2_1 = new FeedCommentSelVo();
//        cmtVo2_1.setIfeedComment(3);
//        cmtVo2_1.setComment("3-cmtVo2_1");
//
//        FeedCommentSelVo cmtVo2_2 = new FeedCommentSelVo();
//        cmtVo2_2.setIfeedComment(4);
//        cmtVo2_2.setComment("4-cmtVo2_2");
//
//        FeedCommentSelVo cmtVo2_3 = new FeedCommentSelVo();
//        cmtVo2_3.setIfeedComment(5);
//        cmtVo2_3.setComment("5-cmtVo2_3");
//
//        FeedCommentSelVo cmtVo2_4 = new FeedCommentSelVo();
//        cmtVo2_4.setIfeedComment(6);
//        cmtVo2_4.setComment("6-cmtVo2_4");
//
//        cmtsFeed2.add(cmtVo2_1);
//        cmtsFeed2.add(cmtVo2_2);
//        cmtsFeed2.add(cmtVo2_3);
//        cmtsFeed2.add(cmtVo2_4);
//
//        FeedCommentSelDto fcDto2 = new FeedCommentSelDto();
//        fcDto2.setStartIdx(0);
//        fcDto2.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
//        fcDto2.setIfeed(2);
//
//        when( commentMapper.selFeedCommentAll(fcDto2) ).thenReturn(cmtsFeed2);
//
//        FeedSelDto dto = new FeedSelDto();
//        List<FeedSelVo> result = service.getFeedAll(dto);
//
//        assertEquals(list, result);
//
//        for (int i = 0; i < list.size(); i++) {
//            //list 사이즈 만큼 for문을 돌린다 i만큼 방 생성 (picsList)와도 같음
//            FeedSelVo vo = list.get(i);
//            // feedselvo 타입 list에 생성된 방을 vo에 담는다
//            assertNotNull(vo.getPics());
//            // null값이 아닌것은 true null값이면 false
//            // vo안에 담긴 pics 주소값이 있으면 true 주소값이 없으면 null false
//
//            List<String> pics = picsList.get(i);
//            //리스트 스트링타입 picsList것 들을 가져와서 방에 넣는다
//            // i방 만큼 생성 pics안에 넣는다
//            assertEquals(vo.getPics(), pics);
//            // 서로 값이 같으면 true 틀리면 false
//            // 진짜인 picsList(위 객체참조) 각각 add해서 담은 pics1, pics2의 값이랑
//            // 가짜인 피드서비스에 vo안에 담긴 pics값을 가져와 비교한다
//
//            List<String> pics2 = picsList.get(i);
//            assertEquals(vo.getPics(), pics2);
//            //위 방법과 동일
//        }
//
//        List<FeedCommentSelVo> commentResult1 = list.get(0).getComments();
//        assertEquals(cmtsFeed1, commentResult1, "ifeed(1) 댓글");
//        assertEquals(0, list.get(0). getIsMoreComment(), "ifeed(1) isMoreComment ");
//        assertEquals(2, list.get(0).getComments().size());
//
//        List<FeedCommentSelVo> commentResult2 = list.get(1).getComments();
//        assertEquals(cmtsFeed2, commentResult2, "ifeed(2) 댓글");
//        assertEquals(1, "ifeed(2) isMoreComment ");
//        assertEquals(3, cmtsFeed2.size());
//    }
}