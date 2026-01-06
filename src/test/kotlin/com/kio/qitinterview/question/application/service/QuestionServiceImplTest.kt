package com.kio.qitinterview.question.application.service

import com.kio.qit.enums.InterviewType
import com.kio.qit.enums.ModelType
import com.kio.qit.enums.QuestionGenerationType
import com.kio.qitinterview.company.application.port.out.CompanyRepository
import com.kio.qitinterview.company.domain.model.Company
import com.kio.qitinterview.job.application.port.out.JobRepository
import com.kio.qitinterview.job.domain.model.JobGroup
import com.kio.qitinterview.job.domain.model.JobPosition
import com.kio.qitinterview.member.application.port.out.MemberRepository
import com.kio.qitinterview.member.domain.model.Member
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.application.port.out.QuestionRepository
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestion
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestionsResponse
import com.kio.qitinterview.question.application.service.prompt.QuestionPromptBuilder
import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.model.QuestionSuggestionRequest
import com.kio.qitinterview.question.domain.model.QuestionSuggestionResult
import com.kio.qitinterview.question.domain.service.QuestionDomainService
import com.kio.qitinterview.question.domain.service.dto.QuestionSuggestionAggregate
import com.kio.qitllmclient.client.ollama.OllamaClient
import com.kio.qitllmclient.model.LlmRequest
import com.kio.qitllmclient.model.LlmResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

@DisplayName("QuestionServiceImpl 단위 테스트")
class QuestionServiceImplTest {

    private val questionRepository: QuestionRepository = mockk(relaxed = true)
    private val questionDomainService: QuestionDomainService = mockk()
    private val ollamaClient: OllamaClient = mockk()
    private val questionPromptBuilder: QuestionPromptBuilder = mockk()
    private val jobRepository: JobRepository = mockk()
    private val companyRepository: CompanyRepository = mockk()
    private val memberRepository: MemberRepository = mockk()

    private lateinit var questionService: QuestionServiceImpl

    @BeforeEach
    fun setUp() {
        questionService = QuestionServiceImpl(
            questionRepository = questionRepository,
            jobRepository = jobRepository,
            memberRepository = memberRepository,
            companyRepository = companyRepository,
            questionDomainService = questionDomainService,
            ollamaClient = ollamaClient,
            questionPromptBuilder = questionPromptBuilder
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["더즌"])
    @NullSource
    fun `AI를 활용한 질문 생성 - 회사명 null 여부와 상관없이 질문 생성 성공`(companyName: String?) {
        // given
        val request = CreateAiQuestionSuggestionRequest(
            jobRole = "백엔드 개발자",
            careerYears = 3,
            companyName = companyName,
            interviewType = InterviewType.HARD_SKILL
        )

        val jobGroup = mockk<JobGroup>()
        val jobPosition = JobPosition(id = 1L, name = request.jobRole, jobGroup = jobGroup)
        val member = mockk<Member>()
        val company = companyName?.let { mockk<Company>() }

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

        val aiQuestion = AiQuestion(
            question = interviewQuestion.question,
            description = interviewQuestion.keyPoint,
            interviewType = InterviewType.HARD_SKILL
        )
        val savedAiQuestion = AiQuestion(
            id = 1L,
            question = interviewQuestion.question,
            description = interviewQuestion.keyPoint,
            interviewType = InterviewType.HARD_SKILL
        )
        val suggestionRequest = QuestionSuggestionRequest(
            id = 1L,
            jobRole = jobPosition,
            careerYears = 3,
            interviewType = InterviewType.HARD_SKILL,
            member = member
        ).apply { this.createdAt = LocalDateTime.now() }
        val suggestionResult = QuestionSuggestionResult(
            id = 1L,
            sourceId = 1L,
            sourceType = QuestionGenerationType.AI,
            questionSnapshot = interviewQuestion.question,
            descriptionSnapshot = interviewQuestion.keyPoint,
            interviewType = InterviewType.HARD_SKILL,
            suggestionRequest = suggestionRequest
        ).apply { this.createdAt = LocalDateTime.now() }
        val aggregate = QuestionSuggestionAggregate(
            questionSuggestionRequest = suggestionRequest,
            questionSuggestionResults = listOf(suggestionResult)
        )

        // Mock 설정
        every { jobRepository.findJobPositionByName(request.jobRole) } returns jobPosition
        every { memberRepository.findById(1L) } returns member
        every { companyRepository.findByName(any()) } returns company
        every { questionPromptBuilder.buildAiQuestionLlmRequest(any()) } returns llmRequest
        every { ollamaClient.generate(any(), InterviewQuestionsResponse::class.java) } returns llmResponse
        every { questionDomainService.createAiQuestions(any()) } returns listOf(aiQuestion)
        every { questionRepository.saveAllAiQuestions(any()) } returns listOf(savedAiQuestion)
        every {
            questionDomainService.createQuestionSuggestionAggreagte(
                interviewQuestions = listOf(interviewQuestion),
                interviewTypes = listOf(InterviewType.HARD_SKILL),
                requestInterviewType = InterviewType.HARD_SKILL,
                jobPosition = jobPosition,
                member = member,
                company = company,
                careerYears = 3,
                sourceIds = listOf(1L),
                sourceType = QuestionGenerationType.AI
            )
        } returns aggregate
        every { questionRepository.saveSuggestionRequest(suggestionRequest) } returns suggestionRequest
        every { questionRepository.saveAllSuggestionResults(listOf(suggestionResult)) } returns listOf(suggestionResult)

        // when
        val result = questionService.createQuestionUsingAI(request)

        // then
        val resultQuestion = result.resultQuestions[0]

        assertAll(
            // value checks
            { assertNotNull(result) },
            { assertEquals(1, result.resultQuestions.size) },
            { assertEquals(1L, resultQuestion.suggestionResultId) },
            { assertEquals(interviewQuestion.question, resultQuestion.question) },
            { assertEquals(request.jobRole, resultQuestion.jobRole) },
            { assertEquals(request.careerYears, resultQuestion.careerYears) },
            { assertEquals(InterviewType.HARD_SKILL, resultQuestion.interviewType) },
            { assertEquals(QuestionGenerationType.AI, resultQuestion.questionGenerationType) },

            // verify
            { verify(exactly = 1) { jobRepository.findJobPositionByName("백엔드 개발자") } },
            { verify(exactly = 1) { memberRepository.findById(1L) } },
            { verify(exactly = if (companyName != null) 1 else 0) { companyRepository.findByName(any()) } },
            { verify(exactly = 1) { questionPromptBuilder.buildAiQuestionLlmRequest(request) } },
            { verify(exactly = 1) { ollamaClient.generate(any(), InterviewQuestionsResponse::class.java) } },
            { verify(exactly = 1) { questionDomainService.createAiQuestions(any()) } },
            { verify(exactly = 1) { questionRepository.saveAllAiQuestions(any()) } },
            { verify(exactly = 1) { questionDomainService.createQuestionSuggestionAggreagte(any(), any(), any(), any(), any(), any(), any(), any(), any()) } },
            { verify(exactly = 1) { questionRepository.saveSuggestionRequest(any()) } },
            { verify(exactly = 1) { questionRepository.saveAllSuggestionResults(any()) } }
        )
    }

    @Test
    fun `JobPosition이 없을 경우 기타 직군으로 생성`() {
        // given
        val request = CreateAiQuestionSuggestionRequest(
            jobRole = "데이터 사이언티스트",
            careerYears = 5,
            companyName = null,
            interviewType = InterviewType.ALL
        )

        val jobGroup = JobGroup(id = 1L, name = "기타")
        val newJobPosition = JobPosition(id = 2L, name = "데이터 사이언티스트", jobGroup = jobGroup)
        val member = mockk<Member>()

        val llmRequest = mockk<LlmRequest>()
        val interviewQuestions = listOf(
            InterviewQuestion(
                question = "데이터 파이프라인 구축 경험을 설명해주세요.",
                keyPoint = "데이터 엔지니어링 역량 평가",
                interviewType = InterviewType.HARD_SKILL
            ),
            InterviewQuestion(
                question = "팀과의 협업 경험을 공유해주세요.",
                keyPoint = "팀워크 및 커뮤니케이션 능력 평가",
                interviewType = InterviewType.SOFT_SKILL
            )
        )
        val llmResponse = LlmResponse(
            content = InterviewQuestionsResponse(questions = interviewQuestions),
            model = ModelType.OLLAMA
        )

        val aiQuestions = interviewQuestions.map { q ->
            AiQuestion(question = q.question, description = q.keyPoint, interviewType = q.interviewType)
        }
        val savedAiQuestions = aiQuestions.mapIndexed { index, q ->
            AiQuestion(id = (index + 1).toLong(), question = q.question, description = q.description, interviewType = q.interviewType)
        }

        val suggestionRequest = QuestionSuggestionRequest(
            id = 1L,
            jobRole = newJobPosition,
            careerYears = 5,
            interviewType = InterviewType.ALL,
            member = member
        ).apply { this.createdAt = LocalDateTime.now() }
        val suggestionResults = savedAiQuestions.map { q ->
            QuestionSuggestionResult(
                id = q.id,
                sourceId = q.id!!,
                sourceType = QuestionGenerationType.AI,
                questionSnapshot = q.question,
                descriptionSnapshot = q.description,
                interviewType = q.interviewType,
                suggestionRequest = suggestionRequest,
            ).apply { this.createdAt = LocalDateTime.now()}
        }
        val aggregate = QuestionSuggestionAggregate(
            questionSuggestionRequest = suggestionRequest,
            questionSuggestionResults = suggestionResults
        )

        // Mock 설정
        every { jobRepository.findJobPositionByName(any()) } returns null
        every { jobRepository.findJobGroupByName(any()) } returns jobGroup
        every { jobRepository.saveJobPosition(any()) } returns newJobPosition
        every { memberRepository.findById(any()) } returns member
        every { questionPromptBuilder.buildAiQuestionLlmRequest(any()) } returns llmRequest
        every { ollamaClient.generate(any(), InterviewQuestionsResponse::class.java) } returns llmResponse
        every { questionDomainService.createAiQuestions(any()) } returns aiQuestions
        every { questionRepository.saveAllAiQuestions(any()) } returns savedAiQuestions
        every {
            questionDomainService.createQuestionSuggestionAggreagte(any(), any(), any(), any(), any(), any(), any(), any(), any())
        } returns aggregate
        every { questionRepository.saveSuggestionRequest(any()) } returns suggestionRequest
        every { questionRepository.saveAllSuggestionResults(any()) } returns suggestionResults

        // when
        val result = questionService.createQuestionUsingAI(request)

        // then
        assertAll(
            // value checks
            { assertNotNull(result) },
            { assertEquals(2, result.resultQuestions.size) },
            { assertEquals(newJobPosition.name, result.resultQuestions[0].jobRole) },
            { assertEquals(InterviewType.HARD_SKILL, result.resultQuestions[0].interviewType) },
            { assertEquals(InterviewType.SOFT_SKILL, result.resultQuestions[1].interviewType) },

            // verfiy
            { verify(exactly = 1) { jobRepository.findJobPositionByName("데이터 사이언티스트") } },
            { verify(exactly = 1) { jobRepository.findJobGroupByName("기타") } },
            { verify(exactly = 1) { jobRepository.saveJobPosition(any(JobPosition::class)) } }
        )
    }
}