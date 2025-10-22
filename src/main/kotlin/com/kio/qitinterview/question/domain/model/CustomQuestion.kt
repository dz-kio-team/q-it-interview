package com.kio.qitinterview.question.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "custom_question")
class CustomQuestion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val question: String,


) {
}