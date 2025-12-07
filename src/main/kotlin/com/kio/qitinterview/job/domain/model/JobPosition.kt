package com.kio.qitinterview.job.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "job_position")
class JobPosition(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("직무명")
    @Column(nullable = false, unique = true)
    val name: String
    ) {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
        set(value) {
            if (field != null) {
                throw IllegalStateException("createdAt은 최초 생성 시에만 설정할 수 있습니다.")
            }
            field = value
        }
}