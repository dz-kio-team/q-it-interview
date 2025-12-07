package com.kio.qitinterview.question.adapter.out.persistence

import com.kio.qitinterview.question.domain.model.QuestionSuggestionResult
import org.springframework.data.jpa.repository.JpaRepository

interface JpaQuestionSuggestionResultRepository : JpaRepository<QuestionSuggestionResult, Long> {
}