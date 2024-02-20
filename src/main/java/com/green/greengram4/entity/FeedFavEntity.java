package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_feed_fav")
public class FeedFavEntity extends CreatedAtEntity{

    @EmbeddedId
    private FeedFavIds feedFavIds;

    @ManyToOne
    @MapsId("ifeed")
    @JoinColumn(name = "ifeed", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private FeedEntity ifeed;

    @ManyToOne
    @MapsId("iuser")
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private UserEntity iuser;
}
