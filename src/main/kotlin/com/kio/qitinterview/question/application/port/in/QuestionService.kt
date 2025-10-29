package com.kio.qitinterview.question.application.port.`in`

import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateExistingQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateCustomQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.CreateQuestionSuggestionResponse
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.ResultCustomQuestionSuggestion
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.ResultExistingQuestionSuggestion

interface QuestionService {
    fun createQuestion(request: CreateCustomQuestionSuggestionRequest): CreateQuestionSuggestionResponse<ResultCustomQuestionSuggestion>
    fun createQuestionFromExisting(request: CreateExistingQuestionSuggestionRequest): CreateQuestionSuggestionResponse<ResultExistingQuestionSuggestion>
    fun createQuestionUsingAI(request: CreateAiQuestionSuggestionRequest)
}