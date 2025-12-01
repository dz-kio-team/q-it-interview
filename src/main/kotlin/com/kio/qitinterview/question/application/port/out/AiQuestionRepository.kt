package com.kio.qitinterview.question.application.port.out

import com.kio.qitinterview.question.domain.model.AiQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface AiQuestionRepository: JpaRepository<AiQuestion, Long> {
}