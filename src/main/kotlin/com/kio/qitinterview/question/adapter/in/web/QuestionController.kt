package com.kio.qitinterview.question.adapter.`in`.web

//import org.springframework.security.core.annotation.AuthenticationPrincipal
import com.kio.qit.annotation.WebAdapter
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateCustomQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateExistingQuestionSuggestionRequest
import com.kio.qitinterview.question.adapter.`in`.web.dto.response.CreateQuestionSuggestionResponse
import com.kio.qitinterview.question.application.port.`in`.QuestionService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
@RequestMapping("/api/v1/questions-suggestions")
class QuestionController(
    private val questionService: QuestionService
) {
    /**
     * 사용자가 직접 입력한 질문을 생성합니다.
     */
    @PostMapping("/custom")
    fun createCustomQuestion(
//        @AuthenticationPrincipal(expression = "username") userId : String,    // Security 적용 후 주석 해제
        @Validated @RequestBody request: CreateCustomQuestionSuggestionRequest
    ): ResponseEntity<CreateQuestionSuggestionResponse> {
        return ResponseEntity.ok(questionService.createQuestion(request))
    }

    /**
     * 기존 질문을 바탕으로 질문을 생성합니다.
     */
    @PostMapping("/from-existing")
    fun createQuestionFromExisting(
//        @AuthenticationPrincipal(expression = "username") userId : String,
        @Validated @RequestBody request: CreateExistingQuestionSuggestionRequest
    ): ResponseEntity<CreateQuestionSuggestionResponse> {
        return ResponseEntity.ok(questionService.createQuestionFromExisting(request))
    }

    /**
     * AI를 활용하여 주제에 맞는 질문을 생성합니다.
     */
    @PostMapping("/ai")
    fun createQuestionUsingAI(
//        @AuthenticationPrincipal(expression = "username") userId : String,
        @Validated @RequestBody request: CreateAiQuestionSuggestionRequest
    ) {
        questionService.createQuestionUsingAI(request)
    }
}