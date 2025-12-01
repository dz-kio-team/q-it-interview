package com.kio.qitinterview.question.application.service.prompt

import com.kio.qit.enums.LlmMessageType
import com.kio.qit.enums.ModelType
import com.kio.qitinterview.question.adapter.`in`.web.dto.request.CreateAiQuestionSuggestionRequest
import com.kio.qitllmclient.model.LlmMessage
import com.kio.qitllmclient.model.LlmRequest
import org.springframework.stereotype.Component

@Component
class QuestionPromptBuilder {

    fun buildAiQuestionLlmRequest(request: CreateAiQuestionSuggestionRequest): LlmRequest {
        val systemMessage = LlmMessage(
            type = LlmMessageType.SYSTEM,
            prompt = """
                        당신은 면접 전문가입니다.
                        특정 기술이나 개념에 대해 깊이 있는 이해도를 평가할 수 있는 면접 질문을 생성합니다.

                        반드시 다음 JSON 형식으로만 응답하세요:
                        {
                          "questions": [
                            {
                              "question": "면접 질문 내용",
                              "keyPoint": "이 질문의 의도와 평가하고자 하는 핵심 포인트를 상세히 해설",
                              "interviewType": "Hard Skill"
                            },
                            {
                              "question": "면접 질문 내용",
                              "keyPoint": "이 질문의 의도와 평가하고자 하는 핵심 포인트를 상세히 해설",
                              "interviewType": "Soft Skill"
                            }
                          ]
                        }

                        질문 생성 시 고려사항:
                        1. 이론적 지식과 실무 경험을 모두 평가할 수 있어야 합니다.
                        2. 일반적인 질문보다는 실제 문제 상황을 제시하는 질문을 선호합니다.
                        3. 단순 암기가 아닌 개념 이해도를 측정할 수 있어야 합니다.
                        4. 질문은 한국어로 작성합니다.
                        5. 각 질문마다 예상 답변의 핵심 포인트를 문장 형태로 포함합니다.

                        **keyPoint 작성 지침 (중요):**
                        - 단순 키워드 나열이 아닌, 2-3문장의 상세한 해설을 작성하세요.
                        - 이 질문이 "무엇을" 평가하려는지 명확히 설명하세요.
                        - 어떤 개념, 기술, 경험을 확인하고자 하는지 구체적으로 서술하세요.
                        - 예상 답변에서 나와야 할 핵심 요소들을 포함하세요.
                        
                        **interviewType 작성 지침 (중요):**
                        - 질문이 평가하고자 하는 면접 유형을 HARD_SKILL 또는 SOFT_SKILL 중 하나로 명확히 기재하세요.
                        - ALL 또는 all과 같은 혼합형 표현은 사용하지 마세요.

                        예시:
                        - 나쁜 예: "트랜잭션 관리, 동시성 제어"
                        - 좋은 예: "이 질문은 데이터베이스 트랜잭션의 ACID 특성에 대한 이해도를 평가하며, 특히 동시성 제어 상황에서 발생할 수 있는 문제점(Dirty Read, Non-Repeatable Read 등)과 이를 해결하기 위한 격리 수준(Isolation Level) 설정 경험을 확인합니다. 또한 실무에서 성능과 데이터 정합성 사이의 트레이드오프를 어떻게 고려하는지 평가합니다."

                        주의: 반드시 유효한 JSON 형식으로만 응답하고, 추가 설명은 포함하지 마세요.
                    """.trimIndent()
        )
        val userMessage = LlmMessage(
            type = LlmMessageType.USER,
            prompt =
            """
                다음 조건에 맞는 면접 질문을 생성해주세요:
                요청 면접 유형에 맞는 질문을 생성해주세요. 면접 유형 값이 ALL인 겨우 HardSkill과 SoftSkill을 혼합하여 생성해주세요.
                null 값이 들어오는 필드는 조건에서 제외해주세요.

                - 직군: ${request.jobRole}
                - 연차: ${request.careerYears}
                - 회사명: ${request.companyName}
                - 면접 유형: ${request.interviewType}
                - 질문 개수: 5개
            """.trimIndent()
        )
        return LlmRequest(
            prompt = listOf(systemMessage, userMessage),
            model = ModelType.OLLAMA
        )
    }
}