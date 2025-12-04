package com.kio.qitinterview.question.domain.model

import com.kio.qit.enums.QuestionGenerationType
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "question_suggestion_result")
class QuestionSuggestionResult(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Comment("질문 아이디")
    @Column(nullable = false)
    val sourceId: Long,

    @Comment("질문 출처 타입")
    @Column(nullable = false)
    val sourceType: QuestionGenerationType,

    @Comment("면접 질문 복사본")
    @Column(nullable = false)
    val questionSnapshot: String,

    @Comment("질문 설명 복사본")
    @Column(nullable = false)
    val descriptionSnapshot: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggestion_request_id", nullable = false)
    val suggestionRequest: QuestionSuggestionRequest

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