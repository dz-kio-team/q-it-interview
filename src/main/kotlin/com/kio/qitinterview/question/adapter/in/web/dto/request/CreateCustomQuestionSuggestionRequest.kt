package com.kio.qitinterview.question.adapter.`in`.web.dto.request

import com.kio.qit.enum.InterviewType

data class CreateCustomQuestionSuggestionRequest(
    val jobRole: String,
    val careerYears: Int,
    val customQuestion: String,
    val interviewType: InterviewType
) : QuestionSuggestionRequest
