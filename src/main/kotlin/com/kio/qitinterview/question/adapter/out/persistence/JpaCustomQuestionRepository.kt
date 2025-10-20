package com.kio.qitinterview.question.adapter.out.persistence

import com.kio.qitinterview.question.domain.model.CustomQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface JpaCustomQuestionRepository : JpaRepository<CustomQuestion, Long> {
}