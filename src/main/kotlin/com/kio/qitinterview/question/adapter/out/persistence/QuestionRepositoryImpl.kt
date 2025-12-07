package com.kio.qitinterview.question.adapter.out.persistence

import com.kio.qit.annotation.PersistenceAdapter
import com.kio.qitinterview.question.application.port.out.QuestionRepository
import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.model.CustomQuestion
import com.kio.qitinterview.question.domain.model.QuestionSuggestionRequest
import com.kio.qitinterview.question.domain.model.QuestionSuggestionResult

@PersistenceAdapter
class QuestionRepositoryImpl(
    private val jpaCustomQuestionRepository: JpaCustomQuestionRepository,
    private val jpaAiQuestionRepository: JpaAiQuestionRepository,
    private val jpaQuestionSuggestionRequestRepository: JpaQuestionSuggestionRequestRepository,
    private val jpaQuestionSuggestionResultRepository: JpaQuestionSuggestionResultRepository
) : QuestionRepository {

    override fun saveAllAiQuestions(aiQuestions: List<AiQuestion>) : List<AiQuestion> {
        return jpaAiQuestionRepository.saveAll(aiQuestions)
    }

    override fun saveAllCustomQuestions(customQuestions: List<CustomQuestion>): List<CustomQuestion> {
        return jpaCustomQuestionRepository.saveAll(customQuestions)
    }

    override fun saveAllSuggestionResults(suggestionResults: List<QuestionSuggestionResult>): List<QuestionSuggestionResult> {
        return jpaQuestionSuggestionResultRepository.saveAll(suggestionResults)
    }

    override fun saveSuggestionRequest(suggestionRequest: QuestionSuggestionRequest): QuestionSuggestionRequest {
        return jpaQuestionSuggestionRequestRepository.save(suggestionRequest)
    }

}