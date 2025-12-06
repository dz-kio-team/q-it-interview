package com.kio.qitinterview.question.adapter.out.persistence

import com.kio.qit.annotation.PersistenceAdapter
import com.kio.qitinterview.question.application.port.out.QuestionRepository
import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.model.CustomQuestion

@PersistenceAdapter
class QuestionRepositoryImpl(
    private val jpaCustomQuestionRepository: JpaCustomQuestionRepository,
    private val jpaAiQuestionRepository: JpaAiQuestionRepository
) : QuestionRepository {

    override fun save(aiQuestion: AiQuestion) : AiQuestion {
        return jpaAiQuestionRepository.save(aiQuestion)
    }

    override fun save(customQuestion: CustomQuestion): CustomQuestion {
        return jpaCustomQuestionRepository.save(customQuestion)
    }

}