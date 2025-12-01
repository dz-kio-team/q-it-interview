package com.kio.qitinterview.question.application.service

import com.kio.qit.annotation.UseCase
import com.kio.qit.enums.QuestionGenerationType
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateCustomQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateExistingQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.CreateQuestionSuggestionResponse
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.QuestionSuggestionResult
import com.kio.qitinterview.question.application.port.`in`.QuestionService
import com.kio.qitinterview.question.application.port.out.AiQuestionRepository
import com.kio.qitinterview.question.application.port.out.QuestionRepository
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestionsResponse
import com.kio.qitinterview.question.application.service.prompt.QuestionPromptBuilder
import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.service.QuestionDomainService
import com.kio.qitllmclient.client.ollama.OllamaClient
import jakarta.transaction.Transactional
import java.time.LocalDateTime

@UseCase
@Transactional
class QuestionServiceImpl(
    private val questionRepository: QuestionRepository,
    private val questionDomainService: QuestionDomainService,
    private val ollamaClient: OllamaClient,
    private val questionPromptBuilder: QuestionPromptBuilder,
    private val aiQuestionRepository: AiQuestionRepository
) : QuestionService {
    override fun createQuestion(request: CreateCustomQuestionSuggestionRequest): CreateQuestionSuggestionResponse {
        // 1. 사용자 검증

        // 2. CustomQuestion 생성

        // 3. QuestionSuggestionResult 생성

        // 4. 저장

        return null!!
    }

    override fun createQuestionFromExisting(request: CreateExistingQuestionSuggestionRequest): CreateQuestionSuggestionResponse {
        // 1. 사용자 검증

        // 2. ReviewQuestion 목록 조회

        // 3. QuestionSuggestionResult 생성

        // 4. 저장

        return null!!
    }

    override fun createQuestionUsingAI(request: CreateAiQuestionSuggestionRequest): CreateQuestionSuggestionResponse {
        // TODO: 비동기 처리로 개선 필요
        //  현재 문제: LLM 응답이 느려서 HTTP 타임아웃 발생 가능
        //  개선 방안: 비동기 + Polling 방식

        // 1. 사용자 검증

        // 2. AI를 활용한 질문 생성 (동기 처리 - 추후 비동기로 변경 예정)
        val aiQuestionLlmRequest = questionPromptBuilder.buildAiQuestionLlmRequest(request)
        val llmResponse =
            ollamaClient.generate(aiQuestionLlmRequest, InterviewQuestionsResponse::class.java)
        val questions = llmResponse.content.questions

        // 3. QuestionSuggestionResult 생성 및 AiQuestion 저장
        val questionSuggestionResults = questions.map { interviewQuestion ->
            // AiQuestion 저장
            val savedAiQuestion = aiQuestionRepository.save(
                AiQuestion(
                    question = interviewQuestion.question,
                    description = interviewQuestion.keyPoint
                )
            )

            // QuestionSuggestionResult 생성
            QuestionSuggestionResult(
                suggestionResultId = savedAiQuestion.id ?: 0L,
                question = interviewQuestion.question,
                jobRole = request.jobRole,
                careerYears = request.careerYears,
                interviewType = interviewQuestion.interviewType,
                questionGenerationType = QuestionGenerationType.AI,
                createdAt = LocalDateTime.now()
            )
        }

        // TODO: QuestionSuggestionRequest, QuestionSuggestionResult 저장 로직 추가

        // 4. 응답 반환
        return CreateQuestionSuggestionResponse(
            resultQuestions = questionSuggestionResults
        )
    }
}