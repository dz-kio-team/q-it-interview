package com.kio.qitinterview.question.adapter.out.persistence

import com.kio.qitinterview.question.domain.model.QuestionSuggestionRequest
import org.springframework.data.jpa.repository.JpaRepository

interface JpaQuestionSuggestionRequestRepository : JpaRepository<QuestionSuggestionRequest, Long> {
}