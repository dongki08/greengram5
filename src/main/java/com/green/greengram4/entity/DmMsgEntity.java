package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_dm_msg")
public class DmMsgEntity extends CreatedAtEntity{

    @EmbeddedId
    private DmMsgIds msgIds;

    @JoinColumn(name = "idm", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne
    @MapsId("idm")
    private DmEntity idm;

    @JoinColumn(name = "iuser", nullable = false)
    @ManyToOne()
    private UserEntity entity;

    @Column(length = 2000)
    private String msg;
}
