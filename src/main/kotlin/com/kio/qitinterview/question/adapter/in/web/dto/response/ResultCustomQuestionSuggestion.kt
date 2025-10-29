package com.kio.qitinterview.question.adapter.`in`.web.dto.response

import com.kio.qit.enum.InterviewType
import com.kio.qit.enum.QuestionGenerationType
import java.time.LocalDateTime

data class ResultCustomQuestionSuggestion(
    val resultId: Long,
    override val question: String,
    override val jobRole: String,
    override val careerYears: Int,
    override val interviewType: InterviewType,
    override val questionGenerationType: QuestionGenerationType,
    override val createdAt: LocalDateTime
) : QuestionSuggestionResult
