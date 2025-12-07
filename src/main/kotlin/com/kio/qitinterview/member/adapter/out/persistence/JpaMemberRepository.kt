package com.kio.qitinterview.member.adapter.out.persistence

import com.kio.qitinterview.member.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface JpaMemberRepository: JpaRepository<Member, Long> {
}