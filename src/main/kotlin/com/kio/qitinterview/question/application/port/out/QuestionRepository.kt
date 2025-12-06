package com.kio.qitinterview.question.application.port.out

import com.kio.qitinterview.question.domain.model.AiQuestion
import com.kio.qitinterview.question.domain.model.CustomQuestion

interface QuestionRepository {
    fun save(aiQuestion: AiQuestion) : AiQuestion
    fun save(customQuestion: CustomQuestion) : CustomQuestion
}