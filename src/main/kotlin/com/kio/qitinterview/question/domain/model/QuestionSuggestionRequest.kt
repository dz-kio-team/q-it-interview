package com.kio.qitinterview.question.domain.model

import com.kio.qit.enums.InterviewType
import com.kio.qitinterview.company.domain.model.Company
import com.kio.qitinterview.job.domain.model.JobPosition
import com.kio.qitinterview.member.domain.model.Member
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
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
    val company: Company? = null,

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