package com.kio.qitinterview.question.adapter.out.persistence

import com.kio.qit.annotation.PersistenceAdapter

@PersistenceAdapter
class QuestionRepositoryImpl(
    private val jpaCustomQuestionRepository: JpaCustomQuestionRepository
) : com.kio.qitinterview.question.application.port.out.QuestionRepository {
}