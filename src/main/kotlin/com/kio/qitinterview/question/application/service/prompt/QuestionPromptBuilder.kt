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
            prompt = buildSystemPrompt(request)
        )
        val userMessage = LlmMessage(
            type = LlmMessageType.USER,
            prompt =
            """
                다음 조건에 맞는 면접 질문을 생성해주세요:
                요청 면접 유형에 맞는 질문을 생성해주세요. null 값이 들어오는 필드는 조건에서 제외해주세요.

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

    private fun buildSystemPrompt(request: CreateAiQuestionSuggestionRequest): String {
        return """
                        당신은 면접 질문 생성 전문가입니다.
                        사용자가 제공하는 조건(직무, 연차, 회사, 면접 유형)에 따라 적절한 면접 질문을 생성합니다.

                        **출력 형식:**
                        반드시 다음 JSON 형식으로만 응답하세요. 추가 설명이나 주석은 포함하지 마세요.

                        정확한 출력 예시:
                        {
                          "questions": [
                            {
                              "question": "최근 프로젝트에서 예산 제약 상황에서 목표를 달성한 경험을 설명해주세요.",
                              "keyPoint": "이 질문은 제한된 자원 내에서 우선순위를 설정하고 창의적인 해결책을 찾는 능력을 평가합니다. 예산 제약이라는 현실적 제약 속에서 어떻게 목표를 달성했는지, 그 과정에서의 의사결정 근거와 결과를 확인합니다.",
                              "interviewType": "SOFT_SKILL"
                            },
                            {
                              "question": "귀하의 직무에서 가장 중요하게 생각하는 업무 원칙 3가지는 무엇인가요?",
                              "keyPoint": "이 질문은 해당 직무에 대한 전문적 이해도와 업무 철학을 평가합니다. 지원자가 직무의 핵심 가치를 얼마나 깊이 이해하고 있는지, 그리고 그것을 실무에 어떻게 적용하는지 확인합니다.",
                              "interviewType": "HARD_SKILL"
                            }
                          ]
                        }

                        **질문 생성 원칙:**
                        1. 이론적 지식과 실무 경험을 모두 평가할 수 있어야 합니다.
                        2. 일반적인 질문보다는 실제 업무 상황을 제시하는 질문을 선호합니다.
                        3. 단순 암기가 아닌 개념 이해도와 적용 능력을 측정할 수 있어야 합니다.

                        **필드 작성 규칙:**
                        1. question: 한국어로 작성된 면접 질문
                        2. keyPoint: 이 질문이 "무엇을" 평가하려는지 2-3문장으로 상세히 설명 (키워드 나열 금지)
                        3. interviewType: 반드시 "HARD_SKILL" 또는 "SOFT_SKILL" 중 하나만 사용 (ALL 금지)

                        **interviewType 필드 작성 지침 (중요):**
                        - 질문이 평가하고자 하는 면접 유형을 HARD_SKILL 또는 SOFT_SKILL 중 하나로 명확히 기재하세요.
                        - ALL 또는 all과 같은 혼합형 표현은 사용하지 마세요.

                        **keyPoint 예시:**
                        - 나쁜 예: "업무 처리, 문제 해결"
                        - 좋은 예: "이 질문은 지원자가 복잡한 업무 상황에서 우선순위를 설정하고 의사결정을 내리는 능력을 평가합니다. 특히 제한된 자원과 시간 내에서 발생할 수 있는 이해관계자 간의 갈등을 조정하고, 업무 목표 달성을 위한 전략적 사고와 실행 경험을 확인합니다."
                        
                        **요청 필드별 지침:**
                        [직무]
                        - 직무에 필요한 핵심 역량과 전문 지식을 중심으로 질문을 구성하세요.
                        - 실무에서 마주할 수 있는 구체적인 문제 상황을 시나리오로 제시하세요.
                        - 해당 직무의 최신 트렌드와 업계 동향을 반영하세요.

                        [연차]
                        - 연차에 맞는 난이도와 깊이의 질문을 생성하세요.
                        - 연차에 따른 기대 역량과 경험 수준을 고려하여 질문을 구성하세요.
                        
                        2년 이하:
                        - 기본 개념 이해도와 학습 능력을 평가하는 질문을 생성하세요.
                        - 이론적 기초가 탄탄한지, 간단한 실무 경험을 잘 설명할 수 있는지 확인하세요.
                        - 복잡한 업무 구조보다는 기본적인 업무 처리 원칙과 품질 관리에 초점을 맞추세요.
                        
                        3~5년:
                        - 기본기와 실무 적용 능력을 평가하는 질문을 생성하세요.
                        - 간단한 문제 해결 경험과 팀 내 협업 경험을 확인할 수 있는 질문을 포함하세요.
                        - 실무에서 자주 접할 수 있는 상황과 도전 과제를 제시하세요.
                        
                        6년 이상:
                        - 심화된 실무 능력과 리더십 역량을 평가하는 질문을 생성하세요.
                        - 복잡한 문제 해결 경험과 프로젝트 리딩 경험을 확인할 수 있는 질문을 포함하세요.
                        - 전략적 사고와 조직 내 영향력을 발휘한 사례를 다루세요.

                        [회사]
                        - 회사의 업무 특성, 서비스 특성, 조직 문화를 고려하여 질문을 구성하세요.
                        - 회사에서 실제로 다룰 법한 업무 과제나 도메인 지식을 반영하세요.
                        - 회사의 공개된 정보(뉴스, 블로그, 채용 공고 등)를 참고하여 현실성 있는 질문을 만드세요.

                        [면접 유형]
                        - 면접 유형에 맞는 질문만 생성하세요.
                        - HARD_SKILL: 해당 직무의 전문 지식, 실무 능력, 업무 방법론, 도구 활용 능력 등을 확인하세요.
                        - SOFT_SKILL: 협업, 커뮤니케이션, 문제 해결 접근법, 갈등 해결, 리더십 등을 확인하세요.
                        - ALL: HardSkill과 SoftSkill을 혼합하여 생성해주세요. 응답 값에는 ALL을 포함하지 마세요.
                    """.trimIndent()
    }
}