package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_feed_comment")
public class FeedCommentEntity extends BaseEntity{
    @Id
    @Column(name = "ifeed_comment", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeedComment;

    @JoinColumn(name = "iuser", nullable = false)
    @ManyToOne()
    private UserEntity userEntity;

    @JoinColumn(name = "ifeed",nullable = false)
    @ManyToOne()
    private FeedEntity feedEntity;

    @Column(length = 500, nullable = false)
    private String comment;
}
