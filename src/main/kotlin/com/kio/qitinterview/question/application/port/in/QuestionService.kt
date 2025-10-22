package com.kio.qitinterview.question.application.port.`in`

import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateCustomQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateExistingQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.CreateCustomQuestionSuggestionResponse
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.CreateExistingQuestionSuggestionResponse

interface QuestionService {
    fun createQuestion(request: CreateCustomQuestionSuggestionRequest): CreateCustomQuestionSuggestionResponse
    fun createQuestionFromExisting(request: CreateExistingQuestionSuggestionRequest): CreateExistingQuestionSuggestionResponse
    fun createQuestionUsingAI(request: CreateAiQuestionSuggestionRequest)
}