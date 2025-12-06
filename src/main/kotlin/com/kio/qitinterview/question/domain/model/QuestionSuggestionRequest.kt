package com.kio.qitinterview.question.domain.model

import com.kio.qit.enums.InterviewType
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "question_suggestion_request")
class QuestionSuggestionRequest(
    @Id
    @GeneratedValue
    val id : Long? = null,

    @Column(nullable = false)
    val careerYears : Int,

    @Column(nullable = false)
    val interviewType : InterviewType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member : Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company: Company,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_position_id", nullable = false)
    val jobRole : JobPosition,

    @OneToMany(mappedBy = "suggestionRequest")
    val suggestionResults : List<QuestionSuggestionResult> = emptyList()

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