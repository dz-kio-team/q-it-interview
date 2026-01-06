package com.kio.qitinterview

import com.kio.qit.annotation.EnableCoreLibrary
import com.kio.qitllmclient.annotation.EnableLlmLibrary
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing  // JPA Auditing 활성화
@EnableLlmLibrary  // LLM 라이브러리 스프링 빈 등록
@EnableCoreLibrary  // core library 스프링 빈 등록
@SpringBootApplication
class QItInterviewApplication

fun main(args: Array<String>) {
    runApplication<QItInterviewApplication>(*args)
}
