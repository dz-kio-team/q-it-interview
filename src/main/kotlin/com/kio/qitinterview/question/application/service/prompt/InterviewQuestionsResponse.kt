package com.kio.qitinterview.question.application.service.prompt

import com.kio.qit.enums.InterviewType

/**
 * 면접 질문 응답 객체
 */
data class InterviewQuestionsResponse(
    val questions: List<InterviewQuestion>
)

/**
 * 면접 질문
 */
data class InterviewQuestion(
    val question: String,
    val keyPoint: String,
    val interviewType: InterviewType
)