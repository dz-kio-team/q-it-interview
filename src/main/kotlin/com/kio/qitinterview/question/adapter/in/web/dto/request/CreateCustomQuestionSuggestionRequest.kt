package com.kio.qitinterview.question.adapter.`in`.web.dto.request

import com.kio.qitinterview.common.enum.InterviewType

data class CreateCustomQuestionSuggestionRequest(
    val jobRole: String,
    val careerYears: Int,
    val customQuestion: String,
    val interviewType: InterviewType
)
