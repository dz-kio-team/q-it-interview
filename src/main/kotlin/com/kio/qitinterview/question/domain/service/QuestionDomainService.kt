package com.kio.qitinterview.question.domain.service

import com.kio.qit.enums.InterviewType
import com.kio.qit.enums.QuestionGenerationType
import com.kio.qitinterview.company.domain.model.Company
import com.kio.qitinterview.job.domain.model.JobPosition
import com.kio.qitinterview.member.domain.model.Member
import com.kio.qitinterview.question.application.service.prompt.InterviewQuestion
import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.model.QuestionSuggestionRequest
import com.kio.qitinterview.question.domain.model.QuestionSuggestionResult
import com.kio.qitinterview.question.domain.service.dto.QuestionSuggestionAggregate
import org.springframework.stereotype.Service

@Service
class QuestionDomainService {

    fun createAiQuestions(inteviewQuestions: List<InterviewQuestion>): List<AiQuestion> {
        return inteviewQuestions.map { interviewQuestion ->
            AiQuestion(
                question = interviewQuestion.question,
                description = interviewQuestion.keyPoint
            )
        }
    }

    /**
     * 질문 생성 요청/결과를 한번에 생성하는 메서드
     */
    fun createQuestionSuggestionAggreagte(
        interviewQuestions: List<InterviewQuestion>,
        requestInterviewType: InterviewType,
        jobPosition: JobPosition,
        member: Member,
        company: Company? = null,
        careerYears: Int,
        sourceIds: List<Long>,
        sourceType: QuestionGenerationType
    ): QuestionSuggestionAggregate {

        // 1. QuestionSuggestionReqeust 생성 (1개)
        val questionSuggestionRequest = QuestionSuggestionRequest(
            jobRole = jobPosition,
            careerYears = careerYears,
            interviewType = requestInterviewType,
            member = member
        )

        // 2. QuestionSuggestionResult 생성 (여러개)
        val questionSuggestionResults = interviewQuestions.mapIndexed { index, interviewQuestion ->
            QuestionSuggestionResult(
                sourceId = sourceIds[index],
                sourceType = sourceType,
                questionSnapshot = interviewQuestion.question,
                descriptionSnapshot = interviewQuestion.keyPoint,
                suggestionRequest = questionSuggestionRequest
            )
        }

        return QuestionSuggestionAggregate(
            questionSuggestionRequest = questionSuggestionRequest,
            questionSuggestionResults = questionSuggestionResults
        )
    }
}