package com.kio.qitinterview.question.adapter.`in`.web.dto.response

data class CreateQuestionSuggestionResponse<T : QuestionSuggestionResult>(
    val resultQuestions: List<T>
)
