package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedPicEntity;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.feed.model.FeedSelDto;
import com.green.greengram4.feed.model.FeedSelVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;
import static com.green.greengram4.entity.QFeedFavEntity.feedFavEntity;
import static com.green.greengram4.entity.QFeedPicEntity.feedPicEntity;

@Slf4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<FeedEntity> selFeedAll(FeedSelDto dto, Pageable pageable) {


        JPAQuery<FeedEntity> jpaQuery = jpaQueryFactory
                .select(feedEntity)
                .from(feedEntity)
                .join(feedEntity.userEntity).fetchJoin()
                .orderBy(feedEntity.ifeed.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if(dto.getIsFavList() == 1) {
            jpaQuery.join(feedFavEntity)
                    .on(feedEntity.ifeed.eq(feedFavEntity.ifeed.ifeed)
                    , feedFavEntity.iuser.iuser.eq(dto.getLoginedIuser()));
        } else {
            jpaQuery.where(whereTargetUser(dto.getTargetIuser()));
        }
        return jpaQuery.fetch();

//        List<FeedSelVo> list = feedlist.stream().map(item -> FeedSelVo.builder()
//                .ifeed(item.getIfeed().intValue())
//                .contents(item.getContents())
//                .location(item.getLocation())
//                .createdAt(item.getCreatedAt().toString())
//                .writerIuser(item.getUserEntity().getIuser().intValue())
//                .writerNm(item.getUserEntity().getNm())
//                .writerPic(item.getUserEntity().getPic())
//                        .pics(item.getFeedPicsEntityList().stream().map(pic ->
//                                pic.getPic()).collect(Collectors.toList()))
//                        .isFav(item.getFeedFavList().stream().anyMatch(fav ->
//                                fav.getIuser().getIuser() > dto.getLoginedIuser()) ? 1 : 0)
//                .build())
//                .collect(Collectors.toList());
//        return list;
    }

    @Override
    public List<FeedPicEntity> selFeedPicsAll(List<FeedEntity> feedEntityList) {
        return jpaQueryFactory.select(Projections.fields(FeedPicEntity.class
                , feedPicEntity.ifeed, feedPicEntity.pic))
                .from(feedPicEntity)
                .where(feedPicEntity.ifeed.in(feedEntityList))
                .fetch();
    }

    @Override
    public List<FeedFavEntity> selFeedFavAllByMe(List<FeedEntity> feedEntityList, long loginIuser) {
        return jpaQueryFactory.select(Projections.fields(FeedFavEntity.class
                , feedFavEntity.ifeed))
                .from(feedFavEntity)
                .where(feedFavEntity.ifeed.in(feedEntityList).and(feedFavEntity.iuser.iuser.eq(loginIuser)))
                .fetch();
    }

    private BooleanExpression whereTargetUser(long targetIuser) {
        return targetIuser == 0 ? null : feedEntity.userEntity.iuser.eq(targetIuser);
    }


}
