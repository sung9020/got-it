package com.snow.gotit.base.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity (
    @field:UpdateTimestamp
    @LastModifiedDate
    @Comment("수정일자")
    var modifiedAt: LocalDateTime = LocalDateTime.now(),

    @Column(updatable = false)
    @CreatedDate
    @Comment("생성일자")
    var createdAt: LocalDateTime = LocalDateTime.now()
)
