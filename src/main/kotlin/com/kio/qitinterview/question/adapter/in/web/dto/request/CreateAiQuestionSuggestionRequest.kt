package com.kio.qitinterview.question.adapter.`in`.web.dto.request

import com.kio.qit.enum.InterviewType

data class CreateAiQuestionSuggestionRequest(
    val jobRole: String,
    val careerYears: Int,
    val companyName: String,
    val interviewType: InterviewType
) : QuestionSuggestionRequest
