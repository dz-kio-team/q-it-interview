package com.kio.qitinterview.question.application.service

import com.kio.qit.annotation.UseCase
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateExistingQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateCustomQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.CreateQuestionSuggestionResponse
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.ResultCustomQuestionSuggestion
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.ResultExistingQuestionSuggestion
import com.kio.qitinterview.question.application.port.`in`.QuestionService
import com.kio.qitinterview.question.application.port.out.QuestionRepository
import com.kio.qitinterview.question.domain.service.QuestionDomainService
import jakarta.transaction.Transactional

@UseCase
@Transactional
class QuestionServiceImpl(
    private val questionRepository: QuestionRepository,
    private val questionDomainService: QuestionDomainService
) : QuestionService {
    override fun createQuestion(request: CreateCustomQuestionSuggestionRequest): CreateQuestionSuggestionResponse<ResultCustomQuestionSuggestion> {
        // 1. 사용자 검증

        // 2. CustomQuestion 생성

        // 3. QuestionSuggestionResult 생성

        // 4. 저장

        return null!!
    }

    override fun createQuestionFromExisting(request: CreateExistingQuestionSuggestionRequest): CreateQuestionSuggestionResponse<ResultExistingQuestionSuggestion> {
        // 1. 사용자 검증

        // 2. ReviewQuestion 목록 조회

        // 3. QuestionSuggestionResult 생성

        // 4. 저장

        return null!!
    }

    override fun createQuestionUsingAI(request: CreateAiQuestionSuggestionRequest) {
        // 1. 사용자 검증

        // 2. AI를 활용한 질문 생성 (메시지 큐 이용)

    }
}