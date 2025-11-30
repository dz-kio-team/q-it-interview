package com.kio.qitinterview.question.adapter.`in`.web.dto.request

import com.kio.qit.enums.InterviewType

data class CreateCustomQuestionSuggestionRequest(
    override val jobRole: String,
    override val careerYears: Int,
    val customQuestion: String,
    override val interviewType: InterviewType
) : CommonQuestionSeuggestionRequest
