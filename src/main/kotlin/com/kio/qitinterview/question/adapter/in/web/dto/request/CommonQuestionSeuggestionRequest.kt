package com.kio.qitinterview.question.adapter.`in`.web.dto.request

import com.kio.qit.enum.InterviewType

interface CommonQuestionSeuggestionRequest : QuestionSuggestionRequest {
    val jobRole: String
    val careerYears: Int
    val interviewType: InterviewType
}