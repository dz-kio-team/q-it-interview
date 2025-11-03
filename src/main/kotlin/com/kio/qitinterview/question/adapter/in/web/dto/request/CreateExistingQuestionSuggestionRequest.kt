package com.kio.qitinterview.question.adapter.`in`.web.dto.request

data class CreateExistingQuestionSuggestionRequest(
    val reviewQuestionIds: List<Long>
) : QuestionSuggestionRequest
