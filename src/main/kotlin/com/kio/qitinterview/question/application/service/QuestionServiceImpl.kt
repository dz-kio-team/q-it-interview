package com.kio.qitinterview.question.application.service

import com.kio.qit.annotation.UseCase
import com.kio.qit.enums.InterviewType
import com.kio.qit.enums.QuestionGenerationType
import com.kio.qitinterview.company.application.port.out.CompanyRepository
import com.kio.qitinterview.company.domain.model.Company
import com.kio.qitinterview.job.application.port.out.JobRepository
import com.kio.qitinterview.job.domain.model.JobPosition
import com.kio.qitinterview.member.application.port.out.MemberRepository
import com.kio.qitinterview.member.domain.model.Member
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateCustomQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateExistingQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.CreateQuestionSuggestionResponse
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.QuestionSuggestionResultDto
import com.kio.qitinterview.question.application.port.`in`.QuestionService
import com.kio.qitinterview.question.application.port.out.QuestionRepository
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestion
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestionsResponse
import com.kio.qitinterview.question.application.service.prompt.QuestionPromptBuilder
import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.service.QuestionDomainService
import com.kio.qitllmclient.client.ollama.OllamaClient
import org.springframework.transaction.annotation.Transactional

@UseCase
class QuestionServiceImpl(
    private val questionRepository: QuestionRepository,
    private val jobRepository: JobRepository,
    private val memberRepository: MemberRepository,
    private val companyRepository: CompanyRepository,
    private val questionDomainService: QuestionDomainService,
    private val ollamaClient: OllamaClient,
    private val questionPromptBuilder: QuestionPromptBuilder,
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

    @Transactional
    override fun createQuestionUsingAI(request: CreateAiQuestionSuggestionRequest): CreateQuestionSuggestionResponse {
        // 1. JobPosition, Member, Company 조회
        // TODO: 임시 로직, JobPosition이 없을 때 예외를 발생할지 아니면 새로 생성할지 논의 필요
        val jobPosition = jobRepository.findJobPositionByName(request.jobRole)
            ?: run {
                val jobGroup = jobRepository.findJobGroupByName("기타")!!
                val newJobPosition = JobPosition(name = request.jobRole, jobGroup = jobGroup)
                jobRepository.saveJobPosition(newJobPosition)
            }

        // TODO: 임시 회원, 추후 인증 도입 후 수정 필요
        val member = memberRepository.findById(1L)!!

        // 요청에 회사 정보가 있으면 회사 정보 조회
        val company = request.companyName?.let { companyRepository.findByName(it) }

        // 2. AI를 활용한 질문 생성 (동기 처리 - 추후 비동기로 변경 예정)
        val aiQuestionLlmRequest = questionPromptBuilder.buildAiQuestionLlmRequest(request)
        val llmResponse =
            ollamaClient.generate(aiQuestionLlmRequest, InterviewQuestionsResponse::class.java)
        val questions = llmResponse.content.questions

        // 3. AiQuestion 생성 및 저장
        val aiQuestions = questionDomainService.createAiQuestions(questions)
        val savedAiQuestions = questionRepository.saveAllAiQuestions(aiQuestions)

        // 4. QuestionSuggestionRequest, QuestionSuggestionResult 생성 및 저장
        return saveAiQuestionSuggestions(
            aiQuestions = savedAiQuestions,
            interviewQuestions = questions,
            requestInterviewType = request.interviewType,
            jobPosition = jobPosition,
            member = member,
            comapny = company,
            careerYears = request.careerYears
        )
    }

    private fun saveAiQuestionSuggestions(
        aiQuestions: List<AiQuestion>,
        interviewQuestions: List<InterviewQuestion>,
        requestInterviewType: InterviewType,
        jobPosition: JobPosition,
        member: Member,
        comapny: Company? = null,
        careerYears: Int,
    ): CreateQuestionSuggestionResponse {

        // QuestionSuggestionRequest, QuestionSuggestionResult 생성
        val questionSuggestionAggregate =
            questionDomainService.createQuestionSuggestionAggreagte(
                interviewQuestions = interviewQuestions,
                requestInterviewType = requestInterviewType,
                jobPosition = jobPosition,
                member = member,
                company = comapny,
                careerYears = careerYears,
                sourceType = QuestionGenerationType.AI,
                sourceIds = aiQuestions.map { it.id!! },
            )

        // QuestionSuggestionRequest, QuestionSuggestionResult 저장
        val suggestionRequest =
            questionRepository.saveSuggestionRequest(questionSuggestionAggregate.questionSuggestionRequest)
        val suggestionResults =
            questionRepository.saveAllSuggestionResults(questionSuggestionAggregate.questionSuggestionResults)

        // 응답 생성
        return CreateQuestionSuggestionResponse(
            resultQuestions = suggestionResults.map { result ->
                QuestionSuggestionResultDto(
                    suggestionResultId = result.id!!,
                    question = result.questionSnapshot,
                    sourceId = result.sourceId,
                    jobRole = jobPosition.name,
                    careerYears = careerYears,
                    interviewType = suggestionRequest.interviewType,
                    questionGenerationType = QuestionGenerationType.AI,
                    createdAt = result.createdAt!!
                )
            }
        )
    }
}