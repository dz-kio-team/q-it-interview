package com.kio.qitinterview.question.domain.service.dto

import com.kio.qitinterview.question.domain.model.QuestionSuggestionRequest
import com.kio.qitinterview.question.domain.model.QuestionSuggestionResult

/**
 * 질문 생성 결과를 한 번에 담는 Aggregate
 */
data class QuestionSuggestionAggregate(
    val questionSuggestionRequest: QuestionSuggestionRequest,
    val questionSuggestionResults: List<QuestionSuggestionResult>
)
