package com.kio.qitinterview.question.adapter.`in`.web.dto.response

import com.kio.qitinterview.common.enum.InterviewType
import com.kio.qitinterview.common.enum.QuestionGenerationType
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
