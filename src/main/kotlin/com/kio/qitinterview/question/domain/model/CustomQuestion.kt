package com.kio.qitinterview.question.domain.model

import com.kio.qit.domain.model.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "custom_question")
class CustomQuestion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val question: String,

    ) : BaseEntity() {
}