package com.kio.qitinterview.question.adapter.out.persistence

import com.kio.qitinterview.question.domain.model.AiQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface JpaAiQuestionRepository: JpaRepository<AiQuestion, Long> {
}