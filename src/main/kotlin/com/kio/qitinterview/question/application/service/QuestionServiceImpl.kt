package com.kio.qitinterview.question.application.service

import com.kio.qitinterview.common.annotation.UseCase
import com.kio.qitinterview.question.domain.service.QuestionDomainService
import jakarta.transaction.Transactional

@UseCase
@Transactional
class QuestionServiceImpl(
    private val questionRepository: com.kio.qitinterview.question.application.port.out.QuestionRepository,
    private val questionDomainService: QuestionDomainService
) : com.kio.qitinterview.question.application.port.`in`.QuestionService {
}