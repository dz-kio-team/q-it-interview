package com.kio.qitinterview

import com.kio.qit.annotation.EnableCoreLibrary
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableCoreLibrary  // core library 스프링 빈 등록
@SpringBootApplication
class QItInterviewApplication

fun main(args: Array<String>) {
    runApplication<QItInterviewApplication>(*args)
}
