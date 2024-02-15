package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_dm_user")
public class DmUserEntity {

    @EmbeddedId
    private DmUserIds dmUserIds;

    @JoinColumn(name = "idm", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne
    @MapsId("idm")
    private DmEntity idm;

    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne
    @MapsId("iuser")
    private UserEntity iuser;
}
