package com.kio.qitinterview.question.application.port.out

import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.model.CustomQuestion
import com.kio.qitinterview.question.domain.model.QuestionSuggestionRequest
import com.kio.qitinterview.question.domain.model.QuestionSuggestionResult

interface QuestionRepository {
    fun saveAllAiQuestions(aiQuestions: List<AiQuestion>) : List<AiQuestion>
    fun saveAllCustomQuestions(customQuestions: List<CustomQuestion>) : List<CustomQuestion>
    fun saveAllSuggestionResults(suggestionResults: List<QuestionSuggestionResult>) : List<QuestionSuggestionResult>
    fun saveSuggestionRequest(suggestionRequest: QuestionSuggestionRequest) : QuestionSuggestionRequest
}