package com.kio.qitinterview.question.adapter.`in`.web.dto.response

import com.kio.qit.enum.InterviewType
import com.kio.qit.enum.QuestionGenerationType
import java.time.LocalDateTime

sealed interface QuestionSuggestionResult {
    val question: String
    val jobRole: String
    val careerYears: Int
    val interviewType: InterviewType
    val questionGenerationType: QuestionGenerationType
    val createdAt: LocalDateTime
}