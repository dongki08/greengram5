package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass //나를 상속받으면 그 맵핑자료도 자식들한테 전달
@EntityListeners(AuditingEntityListener.class) //공통적으로 처리
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate //생성시간정보
    private LocalDateTime updatedAt;
}
