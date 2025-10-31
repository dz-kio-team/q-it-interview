package com.kio.qitinterview.question.adapter.`in`.web.dto.request

import com.kio.qit.enum.InterviewType

data class CreateAiQuestionSuggestionRequest(
    override val jobRole: String,
    override val careerYears: Int,
    val companyName: String,
    override val interviewType: InterviewType
) : CommonQuestionSeuggestionRequest
