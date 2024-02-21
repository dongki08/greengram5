package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.feed.model.FeedSelVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;

@Slf4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<FeedSelVo> selFeedAll(long loginIuser, long targetIuser, Pageable pageable) {
        List<FeedEntity> feedlist = jpaQueryFactory
                .select(feedEntity)
                .from(feedEntity)
                .where(whereTargetUser(targetIuser))
                .orderBy(feedEntity.ifeed.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<FeedSelVo> list = feedlist.stream().map(item -> FeedSelVo.builder()
                .ifeed(item.getIfeed().intValue())
                .contents(item.getContents())
                .location(item.getLocation())
                .createdAt(item.getCreatedAt().toString())
                .writerIuser(item.getUserEntity().getIuser().intValue())
                .writerNm(item.getUserEntity().getNm())
                .writerPic(item.getUserEntity().getPic())
                        .pics(item.getFeedPicsEntityList().stream().map(pic ->
                                pic.getPic()).collect(Collectors.toList()))
                        .isFav(item.getFeedFavList().stream().anyMatch(fav ->
                                fav.getIuser().getIuser() > loginIuser) ? 1 : 0)
                .build())
                .collect(Collectors.toList());
        return list;
    }

    private BooleanExpression whereTargetUser(long targetIuser) {
        return targetIuser == 0 ? null : feedEntity.userEntity.iuser.eq(targetIuser);
    }


}
