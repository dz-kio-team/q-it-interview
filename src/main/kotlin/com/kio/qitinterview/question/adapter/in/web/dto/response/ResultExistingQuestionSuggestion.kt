package com.kio.qitinterview.question.adapter.`in`.web.dto.response

import com.kio.qit.enum.InterviewType
import com.kio.qit.enum.QuestionGenerationType
import java.time.LocalDateTime

data class ResultExistingQuestionSuggestion(
    val suggestionResultId: Long,
    val question: String,
    val jobRole: String,
    val careerYears: Int,
    val interviewType: InterviewType,
    val sourceId: Long,
    val questionGenerationType: QuestionGenerationType,
    val createdAt: LocalDateTime
)
