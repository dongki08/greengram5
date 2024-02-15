package com.green.greengram4.entity;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.common.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint( columnNames = { "provider_type", "uid"} )
})
public class UserEntity extends BaseEntity{
    @Id //pk값 줄때
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토인크리먼트 IDENTITY(MY SQL 전용)
    private Long iuser;

    @Column(length = 10, name = "provider_type", nullable = false )
    @Enumerated(value = EnumType.STRING) //enum타입으로 바꾼다
    @ColumnDefault("'LOCAL'") //기본값 로컬
    private ProviderTypeEnum providerType;
    //columnDefinition 디비에 코멘트 남기는 어노테이션
    //name DB와 이름이 다를때 사용 (언더바)
    //length 글자 길이설정
    //nullable false 시 notnull true 시 null

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 300, nullable = false)
    private String upw;

    @Column(length = 25, nullable = false)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Column(length = 2100, name = "firebase_token")
    private String firebaseToken;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    private RoleEnum role;
}
