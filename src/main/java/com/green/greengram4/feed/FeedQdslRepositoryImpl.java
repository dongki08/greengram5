package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.feed.model.FeedSelVo;
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
    public List<FeedSelVo> selFeedAll(Long loginIuser, UserEntity userEntity, Pageable pageable) {
        List<FeedEntity> list = jpaQueryFactory
                .select(feedEntity)
                .from(feedEntity)
                .orderBy(feedEntity.ifeed.desc())
                .fetch();

        return list.stream().map(item -> FeedSelVo.builder()
                .ifeed(item.getIfeed().intValue())
                .contents(item.getContents())
                .location(item.getLocation())
                .createdAt(item.getCreatedAt().toString())
                .writerIuser(item.getUserEntity().getIuser().intValue())
                .writerNm(item.getUserEntity().getNm())
                .writerPic(item.getUserEntity().getPic())
                .build())
                .collect(Collectors.toList());
    }
}
