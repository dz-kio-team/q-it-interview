package com.kio.qitinterview.question.adapter.`in`.web.dto.request

import com.kio.qit.enums.InterviewType

data class CreateAiQuestionSuggestionRequest(
    override val jobRole: String,
    override val careerYears: Int,
    val companyName: String? = null,
    override val interviewType: InterviewType
) : CommonQuestionSeuggestionRequest
