package com.kio.qitinterview.question.adapter.`in`.web.dto.response

import com.kio.qit.enums.InterviewType
import com.kio.qit.enums.QuestionGenerationType
import java.time.LocalDateTime

data class ResultQuestionSuggestion(
    val suggestionResultId: Long,
    val sourceId: Long?, // AI, Custom에서는 null
    val question: String,
    val jobRole: String,
    val careerYears: Int,
    val interviewType: InterviewType,
    val questionGenerationType: QuestionGenerationType,
    val createdAt: LocalDateTime
) {
    init {
        if (QuestionGenerationType.EXISTING == questionGenerationType) {
            requireNotNull(sourceId) { "QuestionGenerationType이 EXISTING인 경우 sourceId는 null일 수 없습니다." }
        }
    }
}