package com.kio.qitinterview.question.adapter.`in`.web

import com.kio.qitinterview.common.annotation.WebAdapter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
@RequestMapping("/questions")
class QuestionController(
    private val questionService: com.kio.qitinterview.question.application.port.`in`.QuestionService
) {
}