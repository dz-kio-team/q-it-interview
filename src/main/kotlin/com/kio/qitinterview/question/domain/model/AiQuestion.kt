package com.kio.qitinterview.question.domain.model

import com.kio.qit.domain.model.BaseEntity
import com.kio.qit.enums.InterviewType
import com.kio.qit.enums.QuestionGenerationType
import jakarta.persistence.*

@Entity
@Table(name = "ai_question")
class AiQuestion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val question: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val interviewType: InterviewType,

    @Column(nullable = false)
    val sourceType: QuestionGenerationType = QuestionGenerationType.AI,

    ) : BaseEntity() {
}