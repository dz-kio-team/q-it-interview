package com.kio.qitinterview.question.application.service

import com.kio.qit.enums.InterviewType
import com.kio.qit.enums.ModelType
import com.kio.qit.enums.QuestionGenerationType
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.application.port.out.AiQuestionRepository
import com.kio.qitinterview.question.application.port.out.QuestionRepository
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestion
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestionsResponse
import com.kio.qitinterview.question.application.service.prompt.QuestionPromptBuilder
import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.service.QuestionDomainService
import com.kio.qitllmclient.client.ollama.OllamaClient
import com.kio.qitllmclient.model.LlmRequest
import com.kio.qitllmclient.model.LlmResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("QuestionServiceImpl 단위 테스트")
class QuestionServiceImplTest {

    private val questionRepository: QuestionRepository = mockk()
    private val questionDomainService: QuestionDomainService = mockk()
    private val ollamaClient: OllamaClient = mockk()
    private val questionPromptBuilder: QuestionPromptBuilder = mockk()
    private val aiQuestionRepository: AiQuestionRepository = mockk()
    private val questionService = QuestionServiceImpl(
        questionRepository = questionRepository,
        questionDomainService = questionDomainService,
        ollamaClient = ollamaClient,
        questionPromptBuilder = questionPromptBuilder,
        aiQuestionRepository = aiQuestionRepository
    )

    @Test
    fun `AI를 활용한 질문 생성 - 성공`() {
        // given
        val request = CreateAiQuestionSuggestionRequest(
            jobRole = "백엔드 개발자",
            careerYears = 3,
            companyName = "더즌",
            interviewType = InterviewType.HARD_SKILL
        )
        val llmRequest = mockk<LlmRequest>()
        val interviewQuestion = InterviewQuestion(
            question = "Spring Boot의 Auto Configuration 동작 원리를 설명해주세요.",
            keyPoint = "Spring Boot의 자동 설정 메커니즘 이해도 평가",
            interviewType = InterviewType.HARD_SKILL
        )
        val llmResponse = LlmResponse(
            content = InterviewQuestionsResponse(questions = listOf(interviewQuestion)),
            model = ModelType.OLLAMA
        )

        val savedAiQuestion = AiQuestion(
            id = 1L,
            question = interviewQuestion.question,
            description = interviewQuestion.keyPoint
        )

        every { questionPromptBuilder.buildAiQuestionLlmRequest(any(CreateAiQuestionSuggestionRequest::class)) } returns llmRequest
        every { ollamaClient.generate(any(LlmRequest::class), InterviewQuestionsResponse::class.java) } returns llmResponse
        every { aiQuestionRepository.save(any(AiQuestion::class)) } returns savedAiQuestion

        // When
        val result = questionService.createQuestionUsingAI(request)

        // Then
        val resultQuestion = result.resultQuestions[0]

        assertAll(
            { assertNotNull(result) },
            { assertEquals(1, result.resultQuestions.size) },
            { assertEquals(1L, resultQuestion.suggestionResultId) },
            { assertEquals(interviewQuestion.question, resultQuestion.question) },
            { assertEquals(request.jobRole, resultQuestion.jobRole) },
            { assertEquals(request.careerYears, resultQuestion.careerYears) },
            { assertEquals(InterviewType.HARD_SKILL, resultQuestion.interviewType) },
            { assertEquals(QuestionGenerationType.AI, resultQuestion.questionGenerationType) },
            { verify(exactly = 1) { questionPromptBuilder.buildAiQuestionLlmRequest(any()) } },
            { verify(exactly = 1) { ollamaClient.generate(any(), InterviewQuestionsResponse::class.java) } },
            { verify(exactly = 1) { aiQuestionRepository.save(any()) } }
        )
    }
}