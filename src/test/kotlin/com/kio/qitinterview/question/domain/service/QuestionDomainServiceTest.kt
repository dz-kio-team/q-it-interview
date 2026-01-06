package com.kio.qitinterview.question.domain.service

import com.kio.qit.enums.InterviewType
import com.kio.qit.enums.QuestionGenerationType
import com.kio.qitinterview.company.domain.model.Company
import com.kio.qitinterview.job.domain.model.JobGroup
import com.kio.qitinterview.job.domain.model.JobPosition
import com.kio.qitinterview.member.domain.model.Member
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestion
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("QuestionDomainService 단위 테스트")
class QuestionDomainServiceTest {

    private lateinit var questionDomainService: QuestionDomainService

    @BeforeEach
    fun setUp() {
        questionDomainService = QuestionDomainService()
    }

    @Test
    fun `InterviewQuestion을 AiQuestion으로 변환`() {
        // given
        val interviewQuestions = listOf(
            InterviewQuestion(
                question = "Spring Boot의 Auto Configuration 동작 원리를 설명해주세요.",
                keyPoint = "Spring Boot의 자동 설정 메커니즘 이해도 평가",
                interviewType = InterviewType.HARD_SKILL
            ),
            InterviewQuestion(
                question = "팀과의 협업 경험을 공유해주세요.",
                keyPoint = "팀워크 및 커뮤니케이션 능력 평가",
                interviewType = InterviewType.SOFT_SKILL
            )
        )

        // when
        val aiQuestions = questionDomainService.createAiQuestions(interviewQuestions)

        // then
        assertAll(
            { assertEquals(2, aiQuestions.size) },
            { assertEquals("Spring Boot의 Auto Configuration 동작 원리를 설명해주세요.", aiQuestions[0].question) },
            { assertEquals("Spring Boot의 자동 설정 메커니즘 이해도 평가", aiQuestions[0].description) },
            { assertEquals(QuestionGenerationType.AI, aiQuestions[0].sourceType) },
            { assertEquals(InterviewType.HARD_SKILL, aiQuestions[0].interviewType) },
            { assertEquals("팀과의 협업 경험을 공유해주세요.", aiQuestions[1].question) },
            { assertEquals("팀워크 및 커뮤니케이션 능력 평가", aiQuestions[1].description) },
            { assertEquals(InterviewType.SOFT_SKILL, aiQuestions[1].interviewType) },
            { assertEquals(QuestionGenerationType.AI, aiQuestions[1].sourceType) },
        )
    }

    @Test
    @DisplayName("QuestionSuggestionAggregate 생성")
    fun `QuestionSuggestionAggregate 생성 - 회사 정보 없음`() {
        // given
        val jobGroup = mockk<JobGroup>()
        val jobPosition = JobPosition(id = 1L, name = "백엔드 개발자", jobGroup = jobGroup)
        val member = mockk<Member>()

        val interviewQuestions = listOf(
            InterviewQuestion(
                question = "데이터베이스 설계 경험을 설명해주세요.",
                keyPoint = "데이터베이스 설계 능력 평가",
                interviewType = InterviewType.HARD_SKILL
            )
        )
        val interviewTypes = listOf(InterviewType.HARD_SKILL)
        val expectedCareerYears = 3
        val expectedRequestInterviewType = InterviewType.ALL
        val expectedSourceIds = listOf(1L)

        // when
        val aggregate = questionDomainService.createQuestionSuggestionAggreagte(
            interviewQuestions = interviewQuestions,
            interviewTypes = interviewTypes,
            requestInterviewType = expectedRequestInterviewType,
            jobPosition = jobPosition,
            member = member,
            company = null,
            careerYears = expectedCareerYears,
            sourceIds = expectedSourceIds,
            sourceType = QuestionGenerationType.AI
        )

        // then
        val result = aggregate.questionSuggestionResults[0]
        assertAll(
            { assertNotNull(aggregate) },

            // Request 검증
            { assertNotNull(aggregate.questionSuggestionRequest) },
            { assertEquals(interviewQuestions.size, aggregate.questionSuggestionResults.size) },
            { assertEquals(jobPosition, aggregate.questionSuggestionRequest.jobRole) },
            { assertEquals(expectedCareerYears, aggregate.questionSuggestionRequest.careerYears) },
            { assertEquals(expectedRequestInterviewType, aggregate.questionSuggestionRequest.interviewType) },
            { assertEquals(member, aggregate.questionSuggestionRequest.member) },

            // Result 검증
            { assertEquals(expectedSourceIds[0], result.sourceId) },
            { assertEquals(QuestionGenerationType.AI, result.sourceType) },
            { assertEquals(interviewQuestions[0].question, result.questionSnapshot) },
            { assertEquals(interviewQuestions[0].keyPoint, result.descriptionSnapshot) },
            { assertEquals(interviewQuestions[0].interviewType, result.interviewType) },
            { assertEquals(aggregate.questionSuggestionRequest, result.suggestionRequest) }
        )
    }

    @Test
    @DisplayName("QuestionSuggestionAggregate 생성 - 여러 질문 타입 혼합")
    fun `QuestionSuggestionAggregate 생성 - 여러 질문 타입 혼합`() {
        // given
        val jobGroup = mockk<JobGroup>()
        val jobPosition = JobPosition(id = 1L, name = "프로덕트 매니저", jobGroup = jobGroup)
        val member = mockk<Member>()

        val interviewQuestions = listOf(
            InterviewQuestion(
                question = "제품 로드맵 수립 경험을 설명해주세요.",
                keyPoint = "제품 전략 수립 능력 평가",
                interviewType = InterviewType.HARD_SKILL
            ),
            InterviewQuestion(
                question = "팀 간 갈등 상황을 어떻게 해결하셨나요?",
                keyPoint = "갈등 해결 및 조율 능력 평가",
                interviewType = InterviewType.SOFT_SKILL
            ),
            InterviewQuestion(
                question = "데이터 기반 의사결정 사례를 공유해주세요.",
                keyPoint = "데이터 분석 및 활용 능력 평가",
                interviewType = InterviewType.HARD_SKILL
            )
        )
        val interviewTypes = listOf(
            InterviewType.HARD_SKILL,
            InterviewType.SOFT_SKILL,
            InterviewType.HARD_SKILL
        )

        // when
        val aggregate = questionDomainService.createQuestionSuggestionAggreagte(
            interviewQuestions = interviewQuestions,
            interviewTypes = interviewTypes,
            requestInterviewType = InterviewType.ALL,
            jobPosition = jobPosition,
            member = member,
            company = null,
            careerYears = 7,
            sourceIds = listOf(1L, 2L, 3L),
            sourceType = QuestionGenerationType.AI
        )

        // then
        val results = aggregate.questionSuggestionResults

        assertAll(
            { assertNotNull(aggregate) },
            { assertEquals(interviewQuestions.size, aggregate.questionSuggestionResults.size) },
            { assertEquals(interviewQuestions[0].interviewType, results[0].interviewType) },
            { assertEquals(interviewQuestions[1].interviewType, results[1].interviewType) },
            { assertEquals(interviewQuestions[2].interviewType, results[2].interviewType) },
            { assertEquals(interviewQuestions[0].question, results[0].questionSnapshot) },
            { assertEquals(interviewQuestions[1].question, results[1].questionSnapshot) },
            { assertEquals(interviewQuestions[2].question, results[2].questionSnapshot) },
            { assertEquals(interviewQuestions[0].keyPoint, results[0].descriptionSnapshot) },
            { assertEquals(interviewQuestions[1].keyPoint, results[1].descriptionSnapshot) },
            { assertEquals(interviewQuestions[2].keyPoint, results[2].descriptionSnapshot) }
        )
    }
}