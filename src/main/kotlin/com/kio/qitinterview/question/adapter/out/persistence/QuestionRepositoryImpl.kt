package com.kio.qitinterview.question.adapter.out.persistence

import com.kio.qitinterview.common.annotation.PersistenceAdapter

@PersistenceAdapter
class QuestionRepositoryImpl(
    private val jpaCustomQuestionRepository: JpaCustomQuestionRepository
) : com.kio.qitinterview.question.application.port.out.QuestionRepository {
}