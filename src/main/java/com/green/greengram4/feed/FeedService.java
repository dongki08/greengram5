package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.MyFileUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.entity.*;
import com.green.greengram4.exception.FeedErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;
    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils myFileUtils;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FeedCommentRepository commentRepository;
    private final FeedFavRepository feedFavRepository;

    @Transactional
    public FeedPicsInsDto postFeed(FeedInsDto dto) {

        if(dto.getPics() == null) {
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }

        UserEntity entity = userRepository.getReferenceById((long)authenticationFacade.getLoginUserPk());

        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setContents(dto.getContents());
        feedEntity.setLocation(dto.getLocation());
        feedEntity.setUserEntity(entity);
        feedRepository.save(feedEntity);

        String target = "/feed/" + feedEntity.getIfeed();

        FeedPicsInsDto pdto = new FeedPicsInsDto();
        pdto.setIfeed(feedEntity.getIfeed().intValue());
        for(MultipartFile file : dto.getPics()) {
            String saveFileNm = myFileUtils.transferTo(file, target);
            pdto.getPics().add(saveFileNm);
        }
        List<FeedPicEntity> feedPicEntityList = pdto.getPics()
                .stream()
                .map(item -> FeedPicEntity.builder()
                        .ifeed(feedEntity)
                        .pic(item)
                        .build()
                ).collect(Collectors.toList());
        feedEntity.getFeedPicsEntityList().addAll(feedPicEntityList);

        return pdto;
    }


//    public FeedPicsInsDto postFeed(FeedInsDto dto) {
//        if(dto.getPics() == null) {
//            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
//        }
//        dto.setIuser(authenticationFacade.getLoginUserPk());
//        log.info("dto : {}", dto);
//        int feedAffectedRows = mapper.insFeed(dto);
//        String target = "/feed/" + dto.getIfeed();
//        FeedPicsInsDto fp = new FeedPicsInsDto();
//        fp.setIfeed(dto.getIfeed());
//        for(MultipartFile file : dto.getPics()) {
//            String saveFileNm = myFileUtils.transferTo(file, target);
//            fp.getPics().add(saveFileNm);
//        }
//        int feedPicsAffectedRows = picsMapper.insFeedPics(fp);
//        return fp;
//    }

    @Transactional
    public List<FeedSelVo> getFeedAll(FeedSelDto dto, Pageable pageable) {
        long loginIuser = authenticationFacade.getLoginUserPk();
        dto.setLoginedIuser(loginIuser);
        final List<FeedEntity> list = feedRepository.selFeedAll(dto, pageable);

        final List<FeedPicEntity> picList = feedRepository.selFeedPicsAll(list);
        final List<FeedFavEntity> favList = dto.getIsFavList() == 1 ? null : feedRepository.selFeedFavAllByMe(list, loginIuser);
        final List<FeedCommentSelVo> cmtList = commentMapper.selFeedCommentEachTop4(list);

        return list.stream().map(item -> {
            List<FeedCommentSelVo> eachCommentList = cmtList
                            .stream()
                            .filter(cmt -> cmt.getIfeed() == item.getIfeed())
                            .collect(Collectors.toList());

            int isMoreComment = 0;
            if(eachCommentList.size() == 4) {
                isMoreComment = 1;
                eachCommentList.remove(eachCommentList.size() - 1);
            }

            return FeedSelVo.builder()
                    .ifeed(item.getIfeed().intValue())
                    .contents(item.getContents())
                    .location(item.getLocation())
                    .createdAt(item.getCreatedAt().toString())
                    .writerIuser(item.getUserEntity().getIuser().intValue())
                    .writerNm(item.getUserEntity().getNm())
                    .writerPic(item.getUserEntity().getPic())
                    .pics(picList
                            .stream()
                            .filter(pic -> pic.getIfeed().getIfeed() == item.getIfeed())
                            .map(pic -> pic.getPic())
                            .collect(Collectors.toList()))
                    .isFav(favList == null ? 1 : favList.stream().anyMatch(fav ->
                            fav.getIfeed().getIfeed() == item.getIfeed()) ? 1 : 0)
                    .comments(eachCommentList)
                    .isMoreComment(isMoreComment)
                    .build();
        }
        ).collect(Collectors.toList());
    }

//    @Transactional
//    public List<FeedSelVo> getFeedAll(FeedSelDto dto, Pageable pageable) {
//        List<FeedEntity> feedEntityList = null;
//        AtomicInteger isMoreComment = new AtomicInteger(0);
//        if (dto.getIsFavList() == 0 && dto.getTargetIuser() > 0) {
//            UserEntity userEntity = new UserEntity();
//            userEntity.setIuser((long)dto.getTargetIuser());
//            feedEntityList = feedRepository.findAllByUserEntityOrderByIfeedDesc(userEntity, pageable);
//
//        }
//        return feedEntityList == null
//                ? new ArrayList() : feedEntityList.stream().map(item -> {
//
//            FeedFavIds feedFavIds = new FeedFavIds();
//            feedFavIds.setIuser((long)authenticationFacade.getLoginUserPk());
//            feedFavIds.setIfeed(item.getIfeed());
//                    int isFav = feedFavRepository.findById(feedFavIds).isPresent() ? 1 : 0;
//
//                    List<String> picList = item.getFeedPicsEntityList()
//                            .stream()
//                            .map(pics -> pics.getPic())
//                            .collect(Collectors.toList());
//
//            List<FeedCommentSelVo> cmtList = commentRepository.findAllTop4ByFeedEntity(item)
//                    .stream()
//                    .map(cmt ->
//                        FeedCommentSelVo.builder()
//                                .ifeedComment(cmt.getIfeedComment().intValue())
//                                .comment(cmt.getComment())
//                                .createdAt(cmt.getCreatedAt().toString())
//                                .writerIuser(cmt.getUserEntity().getIuser().intValue())
//                                .writerNm(cmt.getUserEntity().getNm())
//                                .writerPic(cmt.getUserEntity().getPic())
//                                .build()
//
//            ).collect(Collectors.toList());
//            if(cmtList.size() > 3) {
//                isMoreComment.set(1);
//                cmtList.remove(cmtList.size() - 1);
//            }else {
//                isMoreComment.set(0);
//            }
//            //cmtList가 4개이면 > isMoreComment = 1, cmtList에 마지막 하나는 제거
//            //else > isMoreComment = 0, cmtList는 변화가 없다.
//
//                    return FeedSelVo.builder()
//                            .ifeed(item.getIfeed().intValue())
//                            .contents(item.getContents())
//                            .location(item.getLocation())
//                            .createdAt(item.getCreatedAt().toString())
//                            .writerIuser(item.getUserEntity().getIuser().intValue())
//                            .writerNm(item.getUserEntity().getNm())
//                            .writerPic(item.getUserEntity().getPic())
//                            .isMoreComment(isMoreComment.intValue())
//                            .pics(picList)
//                            .comments(cmtList)
//                            .isFav(isFav)
//                            .build();
//
//
//                }).collect(Collectors.toList());
//    }

//    public List<FeedSelVo> getFeedAll(FeedSelDto dto) {
//        List<FeedSelVo> list = mapper.selFeedAll(dto);
//        //feedselvo 타입 리스트안에 selfeedall 쿼리문을 넣는다
//
//        FeedCommentSelDto fcDto = new FeedCommentSelDto();
//        fcDto.setStartIdx(0);
//        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
//
//        for(FeedSelVo vo : list) {
//            List<String> pics = picsMapper.selFeedPicsAll(vo.getIfeed());
//            vo.setPics(pics);
//
//            fcDto.setIfeed(vo.getIfeed());
//            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);
//            vo.setComments(comments);
//
//            if(comments.size() == Const.FEED_COMMENT_FIRST_CNT) {
//                vo.setIsMoreComment(1);
//                comments.remove(comments.size() - 1);
//            }
//        }
//        return list;
//    }

    public ResVo delFeed(FeedDelDto dto) {
        //1 이미지
        int picsAffectedRows = picsMapper.delFeedPicsAll(dto);
        if(picsAffectedRows == 0) {
            return new ResVo(Const.FAIL);
        }

        //2 좋아요
        int favAffectedRows = favMapper.delFeedFavAll(dto);

        //3 댓글
        int commentAffectedRows = commentMapper.delFeedCommentAll(dto);

        //4 피드
        int feedAffectedRows = mapper.delFeed(dto);
        return new ResVo(Const.SUCCESS);
    }


    //--------------- t_feed_fav
    public ResVo toggleFeedFav(FeedFavDto dto) {
        //ResVo - result값은 삭제했을 시 (좋아요 취소) 0, 등록했을 시 (좋아요 처리) 1
        int delAffectedRows = favMapper.delFeedFav(dto);
        if(delAffectedRows == 1) {
            return new ResVo(Const.FEED_FAV_DEL);
        }
        int insAffectedRows = favMapper.insFeedFav(dto);
        return new ResVo(Const.FEED_FAV_ADD);
    }
}
