package com.kio.qitinterview.question.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "custom_questions")
class CustomQuestion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val question: String,
) {
}