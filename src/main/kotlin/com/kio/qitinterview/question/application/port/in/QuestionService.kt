package com.kio.qitinterview.question.application.port.`in`

import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateCustomQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateExistingQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.CreateQuestionSuggestionResponse

interface QuestionService {
    fun createQuestion(request: CreateCustomQuestionSuggestionRequest): CreateQuestionSuggestionResponse
    fun createQuestionFromExisting(request: CreateExistingQuestionSuggestionRequest): CreateQuestionSuggestionResponse
    fun createQuestionUsingAI(request: CreateAiQuestionSuggestionRequest)
}