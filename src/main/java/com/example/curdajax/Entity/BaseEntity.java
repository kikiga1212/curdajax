package com.example.curdajax.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//TODO: 여러 테이블에 공통적으로 사용하는 필드는 분리해서 관리
@MappedSuperclass //개별적으로 사용 불가, 다른테이블과 결합해서만 사용이 가능
@Getter @Setter
//TODO: applicaation에 선언된 감사활성화를 이용해서 자동 생성(날짜,시간)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column(nullable = false, updatable = false)
    @CreatedDate //삽입시 날짜와 시간을 자동생성해서 등록
    private LocalDateTime regDate;
    @LastModifiedDate //수정시 날짜와 시간을 자동생성해서 등록
    private LocalDateTime modDate;
}
