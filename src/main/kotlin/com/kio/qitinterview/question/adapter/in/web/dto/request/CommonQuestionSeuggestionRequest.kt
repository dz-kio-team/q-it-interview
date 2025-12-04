package com.kio.qitinterview.question.adapter.`in`.web.dto.request

import com.kio.qit.enums.InterviewType

interface CommonQuestionSeuggestionRequest : QuestionSuggestionRequestDto {
    val jobRole: String
    val careerYears: Int
    val interviewType: InterviewType
}